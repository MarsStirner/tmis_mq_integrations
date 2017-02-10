package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import org.springframework.amqp.core.MessageProperties;
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
    Message createMessage(
            final byte[] body, final String uuid, final String routingKey, final String type, final Direction direction, final Biomaterial biomaterial
    );

    default Message createInMessage(org.springframework.amqp.core.Message message, final Biomaterial biomaterial) {
        final MessageProperties props = message.getMessageProperties();
        return createMessage(message.getBody(),
                             props.getCorrelationIdString(),
                             props.getReceivedRoutingKey(),
                             props.getType(),
                             Direction.IN,
                             biomaterial
        );
    }

    default Message createOutMessage(
            final byte[] body, final String uuid, final String routingKey, final String type, final Biomaterial biomaterial
    ) {
        return createMessage(body, uuid, routingKey, type, Direction.OUT, biomaterial);
    }
}
