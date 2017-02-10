package ru.bars_open.medvtr.amqp.biomaterial;

import com.typesafe.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MessageDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Person;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.ActionType;
import ru.bars_open.medvtr.mq.entities.base.Biomaterial;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
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
    @Autowired
    private MessageDao messageDao;

    private final Config cfg;

    @Autowired
    public LaboratorySender(final ConfigurationHolder cfg) {
        this.cfg = cfg.getConfig("laboratories");
        log.debug("Initialized with CFG= {}", cfg);
    }


    public void send(
            final long tag,
            final String uuid,
            final ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial dbBiomaterial,
            final Biomaterial biomaterial,
            final Map<RbLaboratory, Set<SendResearchToLabaratoryStruct>> toSend
    ) {
        for (Map.Entry<RbLaboratory, Set<SendResearchToLabaratoryStruct>> entry : toSend.entrySet()) {
            sendToLaboratory(tag, uuid, dbBiomaterial, entry.getKey().getCode(), biomaterial, entry.getValue());
        }
    }

    private void sendToLaboratory(
            final long tag,
            final String uuid,
            final ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial dbBiomaterial,
            final String laboratoryCode,
            final Biomaterial biomaterial,
            final Set<SendResearchToLabaratoryStruct> toSend
    ) {
        final String logPrefix = "#" + tag + "-" + laboratoryCode;
        log.info("{}: Start sending [{}] researches", logPrefix, toSend.size());
        if (cfg.hasPath(laboratoryCode)) {
            final Config labConfig = cfg.getConfig(laboratoryCode);
            final String routingKey = labConfig.getString("routing_key");
            final String exchange = labConfig.getString("exchange");
            final BiologicalMaterialMessage result = constructBiologicalMaterialMessage(biomaterial, toSend, logPrefix);
            final byte[] body = DeserializationFactory.serialize(result, DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON, StandardCharsets.UTF_8);
            final Message message = constructMessage(body, uuid);
            messageDao.createOutMessage(body, uuid, routingKey ,"BiologicalMaterialMessage", dbBiomaterial);
            template.send(exchange, routingKey, message);
        } else {
            log.error("{}: NO CONFIGURATION!!!! SKIP.", logPrefix);
        }
    }

    private Message constructMessage(final byte[] body, final String uuid) {
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
