package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.*;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.LoggingPostProcessor;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.MDCHelper;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.NoSuchBiomaterialTypeException;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.OrgStructure;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.entities.base.util.Test;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

import java.util.Collections;
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
    private final Operator operator;
    private final String defaultSoi;

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
    private SoiDao soiDao;

    @Autowired
    public Consumer(final ConfigurationHolder cfg, final OperatorDao operatorDao) {
        this.possibleKeys = new HashSet<>(1);
        this.ROUTING_KEY_SEND = cfg.getString(ConfigurationKeys.REQUEST_SEND_ROUTING_KEY);
        this.possibleKeys.add(ROUTING_KEY_SEND);
        this.operator = operatorDao.get(cfg.getString("operator"));
        this.defaultSoi = cfg.getString("defaultSoi");
        log.info("<init>: possibleKeys = {}; operator={}", possibleKeys, operator);
    }


    /**
     * Типовой случай обработки сообщения:
     * ********************************************************************************************
     * [A] Логгирование: >> {@link LoggingPostProcessor#postProcessMessage}
     * -- [A.1] выставить недостающие свойства сообщению >> {@link LoggingPostProcessor#enrichMessageProperties}
     * -- [A.2] заллогировать сообщение  >> {@link LoggingPostProcessor#logMessage} }
     * ********************************************************************************************
     * [B] Преобразование: >> {@link Consumer#parse}
     * -- [B.1] Спарсить байтовый массив в структуры  >> {@link DeserializationFactory#parse}
     * -- [B.2] Валидация
     * ********************************************************************************************
     * TODO описание процесса
     * ********************************************************************************************
     * [Z] Обработка ошибок: >> {@link ErrorHandler#handleError}
     * -- [Z.1] MessageIsIncorrectException Сообщение нельзя преобразовать, или отсутствует часть полей
     * -- [Z.2] UnknownRoutingKeyException Неизвестный программе ключ (неизвестный тип события)
     */
    @Override
    @Transactional(value = "hepaTransactionManager")
    public void onMessage(final Message amqpMessage, final Channel channel)
            throws MessageIsIncorrectException, UnknownRoutingKeyException, NoSuchBiomaterialTypeException {
        //[B] Преобразование
        final MessageContext ctx = new MessageContext(amqpMessage, parse(amqpMessage));
        log.debug("Message parsed");
        if (ROUTING_KEY_SEND.equals(ctx.getRoutingKey())) {
            final Person person = ctx.getClient();
            final Client client = clientDao.findOrCreate(
                    person.getId(),
                    person.getLastName(),
                    person.getFirstName(),
                    person.getPatrName(),
                    person.getBirthDate().toLocalDate(),
                    person.getSex()
            );
            log.info("Client={}", client);
            final Tube tube = tubeDao.findOrCreate(client, ctx.getMqBiomaterial().getDatetimeTaken());
            log.info("Tube={}", tube);
            final Material material = materialDao.get(ctx.getBiomaterialType());
            log.info("Material={}", material);
            if (material == null) {
                throw new NoSuchBiomaterialTypeException(ctx.getBiomaterialType().getCode(), ctx.getBiomaterialType().getName());
            }
            final Soi soi = getSoi(ctx.getMqBiomaterial().getEvent().getOrgStructure());
            log.info("Soi={}", soi);
            for (Analysis item : ctx.getMqResearch()) {
                MDCHelper.push(item.getId());
                log.info("Start process Research[{}-{}]", item.getType().getCode(), item.getType().getName());
                for (Test test : item.getTests()) {
                    MDCHelper.push(test.getId());
                    log.info("Test[{}-{}]", test.getTest().getCode(), test.getTest().getName());
                    final ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Analysis analysisType = analysisDao.get(test.getTest().getCode());
                    if (analysisType != null) {
                        log.info("Analysis={}", analysisType);
                        final Request request = requestDao.findOrCreate(client, soi, operator, analysisType, material, String.valueOf(test.getId()));
                        log.info("Request={}", request);
                    } else {
                        log.warn("No such Analysis[{}] found", test.getTest().getCode());
                    }
                    MDCHelper.pop();
                }
                MDCHelper.pop();
            }
            responseSender.sent(amqpMessage);
            log.info("### End. Successfully processed");
            return;
        }
        // [Z.2] UnknownRoutingKeyException Неизвестный программе ключ (неизвестный тип события)
        throw new UnknownRoutingKeyException(ctx.getRoutingKey(), possibleKeys);
    }

    private Soi getSoi(final OrgStructure orgStructure) {
        if (orgStructure != null) {
            if (StringUtils.isNotEmpty(orgStructure.getUuid())) {
                final Soi result = soiDao.get(orgStructure.getUuid());
                if (result != null) {
                    return result;
                }
                log.warn("No Soi found by [{}]. Try default soi [{}]", orgStructure.getUuid(), defaultSoi);
            } else {
                log.warn("biomaterial.event.orgStructure.uuid is NULL or empty. Try default soi [{}]", defaultSoi);
            }
        } else {
            log.warn("biomaterial.event.orgStructure is NULL. Try default soi [{}]", defaultSoi);
        }
        return soiDao.get(defaultSoi);
    }

    /**
     * [B] Преобразование:
     * -- [B.1] Спарсить байтовый массив в структуры  {@link DeserializationFactory#parse}
     * -- [B.2] Валидация //TODO https://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html/programmaticapi.html#programmaticapi
     *
     * @param message AMQP сообщение для преобразования и проверки
     * @return Преобразованное в структуру и отвалидированное сообщение
     * @throws MessageIsIncorrectException [Z.1] MessageIsIncorrectException Сообщение нельзя преобразовать, или отсутствует часть полей
     */
    private BiologicalMaterialMessage parse(final Message message) throws MessageIsIncorrectException {
        //[B.1] Спарсить байтовый массив в структуры
        final BiologicalMaterialMessage result = DeserializationFactory.parse(
                message.getBody(),
                message.getMessageProperties().getContentType(),
                message.getMessageProperties().getContentEncoding(),
                BiologicalMaterialMessage.class
        );
        //[B.2] Валидация
        if (result == null) {
            // -- [Z.1] MessageIsIncorrectException Сообщение нельзя преобразовать, или отсутствует часть полей
            throw new MessageIsIncorrectException(Collections.singleton("Message is not parsed from json String"));
        }
        return result;


    }
}

