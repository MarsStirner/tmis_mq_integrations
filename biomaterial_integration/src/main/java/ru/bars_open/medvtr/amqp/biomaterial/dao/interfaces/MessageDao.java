package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Direction;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 20:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface MessageDao extends AbstractDao<Message> {
    Message createMessage(byte[] body, String uuid, String routingKey, String type, Direction direction, Biomaterial biomaterial);

    default Message createInMessage(byte[] body, String uuid, String routingKey, String type, Biomaterial biomaterial) {
        return createMessage(body, uuid, routingKey, type, Direction.IN, biomaterial);
    }

    default Message createOutMessage(byte[] body, String uuid, String routingKey, String type, Biomaterial biomaterial) {
        return createMessage(body, uuid, routingKey, type, Direction.OUT, biomaterial);
    }
}
