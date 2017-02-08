package ru.bars_open.medvtr.amqp.biomaterial.dao.util;

import org.apache.commons.lang3.BooleanUtils;
import org.joda.time.LocalDateTime;
import org.springframework.amqp.core.MessageProperties;
import ru.bars_open.medvtr.amqp.biomaterial.entities.*;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.ActionType;

import java.nio.charset.StandardCharsets;

/**
 * Фабрика конструирующая новые сущности БД
 */
public class EntityFactory {

    public static Biomaterial create(
            final ru.bars_open.medvtr.mq.entities.base.Biomaterial source,
            final Measurement amount,
            final RbTestTubeType testTubeType,
            final RbBiomaterialType biomaterialType,
            final Person person
    ) {
        final Biomaterial result = new Biomaterial();
        result.setExternalId(source.getId());
        result.setBarcodePrefix(String.valueOf(source.getBarcode().getPeriod()));
        result.setBarcodeNumber(String.valueOf(source.getBarcode().getCode()));
        result.setPlannedDateTime(source.getDatetimePlanned().toLocalDateTime());
        if (source.getDatetimeTaken() != null) { result.setFacticalDateTime(source.getDatetimeTaken().toLocalDateTime()); }
        //TODO Event
        result.setAmount(amount);
        result.setTestTybeType(testTubeType);
        result.setBiomaterialType(biomaterialType);
        result.setPerson(person);
        result.setNote(source.getNote());
        return result;
    }

    public static Measurement create(final Double value, final RbUnit unit) {
        final Measurement result = new Measurement();
        result.setUnit(unit);
        result.setValue(value);
        return result;
    }

    public static Person create(final ru.bars_open.medvtr.mq.entities.base.Person source, final RbSex sex) {
        final Person result = new Person();
        result.setExternalId(source.getId());
        result.setPatrName(source.getPatrName());
        result.setLastName(source.getLastName());
        result.setFirstName(source.getFirstName());
        result.setSex(sex);
        result.setBirthDate(source.getBirthDate().toLocalDateTime());
        return result;
    }

    public static Message create(final org.springframework.amqp.core.Message message, final Direction direction, final Biomaterial biomaterial) {
        final MessageProperties messageProperties = message.getMessageProperties();
        final Message result = new Message();
        result.setCorrelationId(messageProperties.getCorrelationIdString());
        result.setBody(new String(message.getBody(), StandardCharsets.UTF_8));
        result.setDeliveryTag((int) messageProperties.getDeliveryTag());
        result.setDirection(direction);
        result.setRoutingKey(messageProperties.getReceivedRoutingKey());
        result.setTimestamp(new LocalDateTime(messageProperties.getTimestamp()));
        result.setType(messageProperties.getType());
        result.setBiomaterial(biomaterial);
        return result;
    }

    public static RbTestTubeType create(final ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType source, final Measurement volume) {
        final RbTestTubeType result = new RbTestTubeType();
        result.setAutocreated(true);
        result.setVolume(volume);
        result.setCode(source.getCode());
        result.setName(source.getName());
        return result;
    }

    public static RbBiomaterialType create(
            final ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType source, final RbBiomaterialType parent, final RbSex sex
    ) {
        final RbBiomaterialType result = new RbBiomaterialType();
        result.setAutocreated(true);
        result.setCode(source.getCode());
        result.setName(source.getName());
        result.setSex(sex);
        result.setGroup(parent);
        return result;
    }

    public static Research create(
            final Analysis source, final Biomaterial biomaterial, final Message message, final RbResearchType researchType, final Person assigner
    ) {
        final Research result = new Research();
        result.setExternalId(source.getId());
        result.setBiomaterial(biomaterial);
        result.setMessage(message);
        result.setCancelled(false);
        result.setUrgent(BooleanUtils.toBooleanDefaultIfNull(source.getIsUrgent(), false));
        if (assigner != null) { result.setAssigner(assigner); }
        if (source.getBegDate() != null) { result.setBegDate(source.getBegDate().toLocalDateTime()); }
        if (source.getEndDate() != null) { result.setEndDate(source.getEndDate().toLocalDateTime()); }
        if (researchType != null) {
            result.setResearchType(researchType);
            result.setNote(null);
        } else {
            final ActionType actionType = source.getType();
            result.setNote(String.format(
                    "Тип исследования не определен: ActionType[%d]{code='%s', flatCode='%s', name='%s'}",
                    actionType.getId(),
                    actionType.getCode(),
                    actionType.getFlatCode(),
                    actionType.getName()
            ));
        }
        return result;
    }
}
