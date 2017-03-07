package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MessageDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Direction;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("messageDao")
@Transactional
public class MessageDaoImpl extends AbstractDaoImpl<Message> implements MessageDao {
    @Override
    public Class<Message> getEntityClass() {
        return Message.class;
    }

    public Message createMessage(
            final byte[] body,
            final String uuid,
            final String routingKey,
            final String type,
            final Direction direction,
            final Biomaterial biomaterial
    ) {
        final Message result = new Message();
        result.setCorrelationId(uuid);
        result.setBody(new String(body, StandardCharsets.UTF_8));
        result.setDirection(direction);
        result.setRoutingKey(routingKey);
        result.setTimestamp(LocalDateTime.now());
        result.setType(type);
        result.setBiomaterial(biomaterial);
        save(result);
        return result;
    }
}
