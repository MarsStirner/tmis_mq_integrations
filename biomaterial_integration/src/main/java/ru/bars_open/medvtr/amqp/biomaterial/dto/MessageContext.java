package ru.bars_open.medvtr.amqp.biomaterial.dto;

import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Test;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 17.02.2017, 14:37 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageContext {
    private final org.springframework.amqp.core.Message amqpMessage;
    private final BiologicalMaterialMessage parsedMessage;

    private Message message;
    private Biomaterial biomaterial;

    private final Set<ResearchContext> researchs;

    public MessageContext(final org.springframework.amqp.core.Message amqpMessage, final BiologicalMaterialMessage parsedMessage) {
        this.amqpMessage = amqpMessage;
        this.parsedMessage = parsedMessage;
        this.researchs = new LinkedHashSet<>(parsedMessage.getResearch().size());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Кастомные геттеры
    ///////////////////////////////////////////////////////////////////////////////////////////////
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

    public String getBiomaterialType() {
        return biomaterial.getBiomaterialType();
    }

    public String getTestTybeType() {
        return biomaterial.getTestTubeType();
    }

    public BiologicalMaterialMessage getParsed() {
        return parsedMessage;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Свойства
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setBiomaterial(final Biomaterial biomaterial) {
        this.biomaterial = biomaterial;
    }

    public Biomaterial getBiomaterial() {
        return biomaterial;
    }

    public void setMessage(final Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public Set<ResearchContext> getResearchs() {
        return researchs;
    }

    public void addToResearchs(final Analysis analysis, final Research research, final Set<Test> tests) {
        researchs.add(new ResearchContext(analysis, research, tests));
    }



}
