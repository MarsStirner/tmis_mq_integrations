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
    Message createMessage(org.springframework.amqp.core.Message message, final Direction direction, final Biomaterial biomaterial);

    default Message createInMessage(org.springframework.amqp.core.Message message, final Biomaterial biomaterial){
        return createMessage(message, Direction.IN,  biomaterial);
    }
    default Message createOutMessage(org.springframework.amqp.core.Message message, final Biomaterial biomaterial){
        return createMessage(message, Direction.OUT,  biomaterial);
    }
}
