package ru.bars_open.medvtr.amqp.biomaterial;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.BiomaterialDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MappingDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MessageDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.ResearchDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.*;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.*;

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

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private BiomaterialDao biomaterialDao;

    @Autowired
    private ResearchDao researchDao;


    @Autowired
    private MappingDao mappingDao;

    @Autowired
    private LaboratorySender laboratorySender;


    @Autowired
    public Consumer(final ConfigurationHolder cfg) {
        this.possibleKeys = new HashSet<>(1);
        this.ROUTING_KEY_SEND = cfg.getString(ConfigurationKeys.REQUEST_SEND_ROUTING_KEY);
        log.info("Constructor called. Possible keys = {}", possibleKeys);
    }


    @Override
    @Transactional
    public void onMessage(final org.springframework.amqp.core.Message amqpMessage, final Channel channel) throws Exception {
        final MessageProperties props = amqpMessage.getMessageProperties();
        final long tag = props.getDeliveryTag();
        final String routingKey = props.getReceivedRoutingKey();
        final Charset encoding = DeserializationFactory.getEncoding(log, tag, props.getContentEncoding());
        log.info("###{}: Receive new amqpMessage[RK='{}']({}):\n{}", tag, routingKey, encoding, new String(amqpMessage.getBody(), encoding));
        if (log.isDebugEnabled()) { log.debug("#{}: {}", tag, props); }
        validateMessageProperties(amqpMessage.getMessageProperties(), tag, routingKey);
        final BiologicalMaterialMessage message = DeserializationFactory.parse(amqpMessage.getBody(),
                                                                               amqpMessage.getMessageProperties().getContentType(),
                                                                               encoding,
                                                                               BiologicalMaterialMessage.class
        );
        if (!validate(message)) {
            throw new MessageIsIncorrectException(null);
        }
        final Biomaterial dbBiomaterial = biomaterialDao.findOrCreate(message.getBiomaterial());
        final Message dbMessage = messageDao.createInMessage(amqpMessage, dbBiomaterial);
        if (ROUTING_KEY_SEND.equals(routingKey)) {
            final List<Analysis> researchList = message.getResearch();
            if (!CollectionUtils.isEmpty(researchList)) {
                final Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> toSend = processResearchList(researchList,
                                                                                                          dbBiomaterial,
                                                                                                          dbMessage,
                                                                                                          tag
                );
                if (!toSend.isEmpty()) {
                    laboratorySender.send(tag, dbMessage.getCorrelationId(), dbBiomaterial, message.getBiomaterial(), toSend);
                } else {
                    //TODO
                    log.warn("#{}: Nothing to send!!!", tag);
                }
            } else {
                //TODO
                log.warn("#{} Research list is empty.", tag);
            }

        } else {
            throw new UnknownRoutingKeyException(routingKey, possibleKeys);
        }
        log.info("###{}: End. Successfully processed", tag);
    }

    private Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> processResearchList(
            final List<Analysis> researchList, final Biomaterial biomaterial, final Message message, final long tag
    ) {
        final Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> result = new HashMap<>(5);
        for (Analysis analysis : researchList) {
            log.warn("#{}-{}: Start", tag, analysis.getId());
            final Research research = researchDao.findOrCreate(analysis, biomaterial, message);
            if (research.getResearchType() != null) {
                final Map<RbLaboratory, MapResearchTypeToLaboratory> mapping = mappingDao.findLaboratoryMapping(research, biomaterial);
                mergeSendStructures(result, mapping, analysis, research);
            } else {
                log.warn("#{}-{}: No such ResearchType. Skip to next.", tag, analysis.getId());
            }
        }
        return result;
    }

    private void mergeSendStructures(
            final Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> result,
            final Map<RbLaboratory, MapResearchTypeToLaboratory> mapping,
            final Analysis analysis,
            final Research research
    ) {
        for (Map.Entry<RbLaboratory, MapResearchTypeToLaboratory> entry : mapping.entrySet()) {
            final Set<SendResearchToLabaratoryStruct> resultSet = result.getOrDefault(entry.getKey(), new HashSet<>());
            resultSet.add(new SendResearchToLabaratoryStruct(analysis, research, entry.getValue()));
            result.put(entry.getKey(), resultSet);
        }
    }

    //TODO Реализовать кастомный конвертер пропертей сообщения org.springframework.amqp.rabbit.support.MessagePropertiesConverter
    private void validateMessageProperties(final MessageProperties messageProperties, final long tag, final String routingKey) {
        if (StringUtils.isEmpty(messageProperties.getCorrelationIdString())) {
            final String value = UUID.randomUUID().toString();
            log.warn("#{}: CorrelationId NOT SET!!!! Assign '{}' by RANDOM", tag, value);
            messageProperties.setCorrelationIdString(value);
        }
        if (StringUtils.isEmpty(messageProperties.getType())) {
            if (ROUTING_KEY_SEND.equalsIgnoreCase(routingKey)) {
                final String value = BiologicalMaterialMessage.class.getSimpleName();
                log.warn("#{}: Type NOT SET!!!! Assign '{}' by RK[{}]", tag, value, routingKey);
                messageProperties.setType(value);
            }
        }
        if (StringUtils.isEmpty(messageProperties.getContentType())) {
            if (ROUTING_KEY_SEND.equalsIgnoreCase(routingKey)) {
                final String value = "application/json";
                log.warn("#{}: ContentType NOT SET!!!! Assign '{}' by RK[{}]", tag, value, routingKey);
                messageProperties.setContentType(value);
            }
        }
    }

    //TODO
    private boolean validate(final BiologicalMaterialMessage parsed) {
        return true;
    }
}

