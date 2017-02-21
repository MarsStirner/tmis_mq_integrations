package ru.bars_open.medvtr.amqp.biomaterial.util;

import org.springframework.amqp.core.Message;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;

import java.util.Collections;

/**
 * Author: Upatov Egor <br>
 * Date: 21.02.2017, 9:42 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageParser {
    /**
     * [B] Преобразование:
     * -- [B.1] Спарсить байтовый массив в структуры  {@link DeserializationFactory#parse}
     * -- [B.2] Валидация //TODO https://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html/programmaticapi.html#programmaticapi
     *
     * @param message AMQP сообщение для преобразования и проверки
     * @return Преобразованное в структуру и отвалидированное сообщение
     * @throws MessageIsIncorrectException [Z.1] MessageIsIncorrectException Сообщение нельзя преобразовать, или отсутствует часть полей
     */
    public static BiologicalMaterialMessage parse(final Message message) throws MessageIsIncorrectException {
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
