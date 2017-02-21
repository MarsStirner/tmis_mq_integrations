package ru.bars_open.medvtr.amqp.biomaterial.hepa.dto;

import org.springframework.amqp.core.Message;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 21.02.2017, 5:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageContext {
    private final Message amqpMessage;
    private final BiologicalMaterialMessage parsedMessage;

    public MessageContext(final Message amqpMessage, final BiologicalMaterialMessage parsedMessage) {
        this.amqpMessage = amqpMessage;
        this.parsedMessage = parsedMessage;
    }

    public Person getClient() {
        return parsedMessage.getBiomaterial().getEvent().getClient();
    }

    public byte[] getBody() {
        return amqpMessage.getBody();
    }

    public String getUUID() {
        return amqpMessage.getMessageProperties().getCorrelationIdString();
    }

    public String getRoutingKey() {
        return amqpMessage.getMessageProperties().getReceivedRoutingKey();
    }


    public ru.bars_open.medvtr.mq.entities.base.Biomaterial getMqBiomaterial() {
        return parsedMessage.getBiomaterial();
    }

    public List<Analysis> getMqResearch() {
        return parsedMessage.getResearch();
    }

    public RbBiomaterialType getBiomaterialType() {
        return getMqBiomaterial().getBiomaterialType();
    }
}
