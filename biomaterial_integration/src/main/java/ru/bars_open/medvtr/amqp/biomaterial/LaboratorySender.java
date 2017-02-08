package ru.bars_open.medvtr.amqp.biomaterial;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.entities.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Person;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.ActionType;
import ru.bars_open.medvtr.mq.entities.base.Biomaterial;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 08.02.2017, 14:48 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("laboratorySender")
public class LaboratorySender {
    private static final Logger log = LoggerFactory.getLogger(LaboratorySender.class);

    @Autowired
    private RabbitTemplate template;

    public void send(
            final long tag, final String uuid, final Biomaterial biomaterial, final Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> toSend
    ) {
        for (Map.Entry<RbLaboratory, Set<SendResearchToLabaratoryStruct>> entry : toSend.entrySet()) {
            sendToLaboratory(tag, uuid, entry.getKey(), biomaterial, entry.getValue());
        }
        log.info("#{}: SENDED", tag);
    }

    private void sendToLaboratory(
            final long tag,
            final String uuid,
            final RbLaboratory laboratory,
            final Biomaterial biomaterial,
            final Set<SendResearchToLabaratoryStruct> toSend
    ) {
        final String logPrefix = "#" + tag + "-" + laboratory.getCode();
        final BiologicalMaterialMessage result = constructBiologicalMaterialMessage(biomaterial, toSend, logPrefix);
        final Message message = constructMessage(result, uuid);
        template.send(laboratory.getExchange(), laboratory.getRoutingKey(), message);
    }

    private Message constructMessage(final BiologicalMaterialMessage source, final String uuid) {
        final byte[] body = DeserializationFactory.serialize(source, DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON, StandardCharsets.UTF_8);
        final MessagePropertiesBuilder propertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertiesBuilder.setAppId("LABORATORY_INTEGRATION");
        propertiesBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        propertiesBuilder.setRedelivered(false);
        propertiesBuilder.setContentLength(body.length);
        propertiesBuilder.setContentType(DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON);
        propertiesBuilder.setCorrelationIdString(uuid);
        propertiesBuilder.setTimestamp(new LocalDateTime().toDate());
        propertiesBuilder.setType("BiologicalMaterialMessage");
        return new Message(body, propertiesBuilder.build());
    }

    private BiologicalMaterialMessage constructBiologicalMaterialMessage(
            final Biomaterial biomaterial, final Set<SendResearchToLabaratoryStruct> toSend, final String logPrefix
    ) {
        log.info("{}: Start construct BiologicalMaterialMessage with [{}] researches", logPrefix, toSend.size());
        final BiologicalMaterialMessage result = new BiologicalMaterialMessage();
        result.setBiomaterial(biomaterial);
        for (SendResearchToLabaratoryStruct parameters : toSend) {
            log.info("{}-{}: Action[{}]", logPrefix, parameters.getResearch().getExternalId(), parameters.getResearch().getExternalId());
            result.getResearch().add(createAnalysis(logPrefix, parameters, parameters.getResearch()));
        }
        log.info("{}: BiologicalMaterialMessage constructed", logPrefix);
        return result;
    }

    private Analysis createAnalysis(final String tag, final SendResearchToLabaratoryStruct parameters, final Research research) {
        final Analysis analysis = new Analysis();
        analysis.setId(research.getExternalId());
        analysis.setStatus(parameters.getAnalysis().getStatus());
        analysis.setType(createActionType(tag + "-" + research.getExternalId(), parameters.getMapping()));
        analysis.setIsUrgent(research.isUrgent());
        analysis.setAssigner(createPerson(research.getAssigner()));
        analysis.setBegDate(research.getBegDate() != null ? research.getBegDate().toDateTime() : null);
        analysis.setEndDate(research.getEndDate() != null ? research.getEndDate().toDateTime() : null);
        analysis.setTests(parameters.getAnalysis().getTests());
        return analysis;
    }

    private ActionType createActionType(final String tag, final MapResearchTypeToLaboratory source) {
        final ActionType result = new ActionType();
        result.setId(source.getResearchType().getId());
        if (StringUtils.isNotEmpty(source.getOverrideResearchTypeCode())) {
            log.info("{}: Override ActionType.code to [{}]", tag, source.getOverrideResearchTypeCode());
            result.setCode(source.getOverrideResearchTypeCode());
        } else {
            result.setCode(source.getResearchType().getCode());
        }
        if (StringUtils.isNotEmpty(source.getOverrideResearchTypeName())) {
            log.info("{}: Override ActionType.name to [{}]", tag, source.getOverrideResearchTypeName());
            result.setName(source.getOverrideResearchTypeName());
        } else {
            result.setName(source.getResearchType().getCode());
        }
        return result;
    }

    private ru.bars_open.medvtr.mq.entities.base.Person createPerson(final Person source) {
        final ru.bars_open.medvtr.mq.entities.base.Person result = new ru.bars_open.medvtr.mq.entities.base.Person();
        result.setId(source.getExternalId());
        switch (source.getSex().getCode()) {
            case "MALE":
                result.setSex(Sex.MALE);
                break;
            case "FEMALE":
                result.setSex(Sex.FEMALE);
                break;
            default:
                result.setSex(Sex.UNKNOWN);
                break;
        }
        result.setBirthDate(source.getBirthDate() != null ? source.getBirthDate().toDateTime() : null);
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setPatrName(source.getPatrName());
        return result;
    }
}
