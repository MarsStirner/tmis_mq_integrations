package ru.bars_open.medvtr.amqp.biomaterial;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.*;
import ru.bars_open.medvtr.amqp.biomaterial.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.dto.ResearchContext;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.util.LaboratoryNotAssignedException;
import ru.bars_open.medvtr.amqp.biomaterial.util.LoggingPostProcessor;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
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

    private final String ROUTING_KEY_RESEND;
    private final String ROUTING_KEY_SEND;

    private final Set<String> possibleKeys;

    @Autowired
    private LaboratoryMapper laboratoryMapper;

    @Autowired
    private LaboratorySender laboratorySender;

    @Autowired
    private ResponseSender responseSender;

    @Autowired
    private MessagePersister messagePersister;


    @Autowired
    public Consumer(final ConfigurationHolder cfg) {
        this.possibleKeys = new HashSet<>(1);
        this.ROUTING_KEY_SEND = cfg.getString(ConfigurationKeys.REQUEST_SEND_ROUTING_KEY);
        this.ROUTING_KEY_RESEND = cfg.getString(ConfigurationKeys.REQUEST_RESEND_ROUTING_KEY);
        possibleKeys.add(ROUTING_KEY_SEND);
        possibleKeys.add(ROUTING_KEY_RESEND);
        log.info("Constructor called. Possible keys = {}", possibleKeys);
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
     * [C] Сохранить данные сообщения в локальную БД (с проверкой уже существующих данных) >> {@link MessagePersister#saveMessageToDb}
     * -- [C.1] Сохранить или найти биоматериал >> {@link BiomaterialDao#findOrCreate}
     * -- [C.2] Сохранить сообщение >> {@link MessageDao#createInMessage}
     * -- [C.3] Сохранить список исследований (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link ResearchDao#findOrCreate}
     * -- [C.4] Сохранить список тестов (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link TestDao#findOrCreate}
     * ********************************************************************************************
     * [D] Деление на лаборатории: >> {@link LaboratoryMapper#map}
     * -- [D.1] Найти лаборатории в которые надо разослать части сообщения >> {@link MappingDao#findLaboratoryMapping}
     * -- [D.2] Поделить данные на части для каждой из лабораторий >> Частично {@link LaboratoryMapper#mergeSendStructures }
     * ********************************************************************************************
     * [E] Отправка в лаборатории:
     * -- [E.1] Поиск маппингов справочников для конкретной лаборатории
     * -- [E.2] Формирование сообщений с учетом маппингов
     * -- [E.3] Отправка в ЛИС-ы
     * -- [E.4] Сохранить в БД связки Исследований и Лабораторий
     * ********************************************************************************************
     * [Z] Обработка ошибок: >> {@link ErrorHandler#handleError}
     * -- [Z.1] MessageIsIncorrectException Сообщение нельзя преобразовать, или отсутствует часть полей
     * -- [Z.2] UnknownRoutingKeyException Неизвестный программе ключ (неизвестный тип события)
     */
    @Override
    @Transactional
    public void onMessage(final Message amqpMessage, final Channel channel)
            throws MessageIsIncorrectException, UnknownRoutingKeyException, LaboratoryNotAssignedException {
        //[B] Преобразование
        final MessageContext ctx = new MessageContext(amqpMessage, parse(amqpMessage));
        log.debug("Message parsed");
        //[C] Сохранить данные сообщения в локальную БД (с проверкой уже существующих данных)
        messagePersister.saveMessageToDb(ctx);
        log.debug("Message persisted");
        if (ROUTING_KEY_SEND.equals(ctx.getRoutingKey()) || ROUTING_KEY_RESEND.equals(ctx.getRoutingKey())) {
            //[D] Деление на лаборатории
            final Map<RbLaboratory, Set<ResearchContext>> laboratoryMapping = laboratoryMapper.map(ctx);
            if(laboratoryMapping.isEmpty()){ throw new LaboratoryNotAssignedException(); }
            //[E] Отправка в лаборатории
            for (Map.Entry<RbLaboratory, Set<ResearchContext>> entry : laboratoryMapping.entrySet()) {
                  laboratorySender.send(entry.getKey(), ctx, entry.getValue());
            }
            responseSender.wait(amqpMessage, laboratoryMapping.keySet());
            log.info("### End. Successfully processed");
            return;
        }
        // [Z.2] UnknownRoutingKeyException Неизвестный программе ключ (неизвестный тип события)
        throw new UnknownRoutingKeyException(ctx.getRoutingKey(), possibleKeys);
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
        final BiologicalMaterialMessage result = DeserializationFactory.parse(message.getBody(),
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

