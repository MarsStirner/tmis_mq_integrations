package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.*;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.entities.base.util.Test;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 18.01.2017, 18:58 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component("consumer")
public class Consumer implements ChannelAwareMessageListener {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private final String ROUTING_KEY_SEND;

    private final Set<String> possibleKeys;
    private final Soi soi;
    private final Operator operator;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private TubeDao tubeDao;

    @Autowired
    private MaterialDao materialDao;

    @Autowired
    private AnalysisDao analysisDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ResponseSender responseSender;


    @Autowired
    public Consumer(final ConfigurationHolder cfg, final SoiDao soiDao, final OperatorDao operatorDao) {
        this.possibleKeys = new HashSet<>(1);
        this.ROUTING_KEY_SEND = cfg.getString(ConfigurationKeys.REQUEST_SEND_ROUTING_KEY);
        this.possibleKeys.add(ROUTING_KEY_SEND);
        this.soi = soiDao.get(cfg.getString("soi"));
        this.operator = operatorDao.get(cfg.getString("operator"));
        log.info("<init>: possibleKeys = {}; soi={}; operator={}", possibleKeys, soi, operator);
    }


    @Override
    @Transactional
    public void onMessage(final org.springframework.amqp.core.Message amqpMessage, final Channel channel) throws Exception {
        final MessageProperties props = amqpMessage.getMessageProperties();
        final long tag = props.getDeliveryTag();
        final String routingKey = props.getReceivedRoutingKey();
        final Charset encoding = DeserializationFactory.getEncoding(log, props.getContentEncoding());
        log.info("###{}: Receive new amqpMessage[RK='{}']({}):\n{}", tag, routingKey, encoding, new String(amqpMessage.getBody(), encoding));
        if (log.isDebugEnabled()) { log.debug("#{}: {}", tag, props); }
        if (ROUTING_KEY_SEND.equals(routingKey)) {
            final BiologicalMaterialMessage message = DeserializationFactory.parse(amqpMessage.getBody(),
                                                                                   amqpMessage.getMessageProperties().getContentType(),
                                                                                   encoding,
                                                                                   BiologicalMaterialMessage.class
            );
            if (!validate(message)) {
                throw new MessageIsIncorrectException(null);
            }
            final Person person = message.getBiomaterial().getEvent().getClient();
            final Client client = clientDao.findOrCreate(person.getLastName(),
                                                         person.getFirstName(),
                                                         person.getPatrName(),
                                                         person.getBirthDate(),
                                                         person.getSex()
            );
            log.info("#{}: Client={}", tag, client);
            final Tube tube = tubeDao.findOrCreate(client, message.getBiomaterial().getDatetimeTaken());
            log.info("#{}: Tube={}", tag, tube);
            final Material material = materialDao.get(message.getBiomaterial().getBiomaterialType());
            log.info("#{}: Material={}", tag, material);
            if (material == null) {
                responseSender.noSuchBiomaterialType(tag, props.getCorrelationIdString(), message.getBiomaterial());
                log.info("###{}: End. NO_SUCH_BIOMATERIAL_TYPE", tag);
                return;
            }
            for (Analysis item : message.getResearch()) {
                log.info("#{}-{}: Start process Research[{}-{}]", tag, item.getId(), item.getType().getCode(), item.getType().getName());
                for (Test test : item.getTests()) {
                    log.info("#{}-{}: Test[{}][{}-{}]", tag, item.getId(), test.getId(), test.getTest().getCode(), test.getTest().getName());
                    final ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Analysis analysisType = analysisDao.get(test.getTest().getCode());
                    if (analysisType != null) {
                        log.info("#{}-{}: Analysis={}", tag, item.getId(), analysisType);
                        final Request request = requestDao.createRequest(
                                client,
                                soi,
                                operator,
                                analysisType,
                                material,
                                item.getId() + "-" + test.getId()
                        );
                        log.info("#{}-{}: Request={}", tag, item.getId(), request);
                    } else {
                        log.info("#{}-{}: No such Analysis[{}] found", tag, item.getId(), test.getTest().getCode());
                    }
                }
            }
        } else {
            throw new UnknownRoutingKeyException(routingKey, possibleKeys);
        } log.info("###{}: End. Successfully processed", tag);
    }


    //TODO
    private boolean validate(final BiologicalMaterialMessage parsed) {
        return true;
    }
}

