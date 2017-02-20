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
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MappingDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MessageDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.StatusDao;
import ru.bars_open.medvtr.amqp.biomaterial.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.dto.ResearchContext;
import ru.bars_open.medvtr.amqp.biomaterial.entities.LaboratoryStatus;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapBiomaterialTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTubeTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.util.MDCHelper;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.ActionType;
import ru.bars_open.medvtr.mq.entities.base.Biomaterial;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTest;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType;
import ru.bars_open.medvtr.mq.entities.base.util.Test;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import java.nio.charset.StandardCharsets;
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

    private final String appId;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MappingDao mappingDao;

    @Autowired
    private StatusDao statusDao;

    private final Config cfg;

    @Autowired
    public LaboratorySender(final ConfigurationHolder cfg) {
        this.appId = cfg.getAppId();
        this.cfg = cfg.getConfig("laboratories");
        log.info("Initialized with CFG = {}", cfg);
    }

    /**
     * [E] Отправка в лаборатории:
     * -- [E.1] Поиск маппингов справочников для конкретной лаборатории
     * -- [E.2] Формирование сообщений с учетом маппингов
     * -- [E.3] Отправка в ЛИС-ы
     * -- [E.4] Сохранить в БД связки Исследований и Лабораторий
     *
     * @param laboratory
     * @param ctx
     * @param researches
     */
    public boolean send(final RbLaboratory laboratory, final MessageContext ctx, final Set<ResearchContext> researches) {
        MDCHelper.push(laboratory.getCode());
        log.info("Start sending [{}] researches", researches.size());
        if (cfg.hasPath(laboratory.getCode())) {
            final Config labConfig = cfg.getConfig(laboratory.getCode());
            final String routingKey = labConfig.getString("routing_key");
            final String exchange = labConfig.getString("exchange");
            // [E.1] Поиск маппингов справочников для конкретной лаборатории
            // [E.2] Формирование сообщений с учетом маппингов
            final BiologicalMaterialMessage result = constructBiologicalMaterialMessage(laboratory, ctx, researches);
            final Message message = constructMessage(routingKey, result, ctx, laboratory.getCode());
            // [E.3] Отправка в ЛИС
            template.send(exchange, routingKey, message);
            log.info("Sent! Exchange='{}', routingKey='{}'", exchange, routingKey);
            // [E.4] Сохранить в БД связки Исследований и Лабораторий
            for (ResearchContext researchContext : researches) {
                statusDao.setLaboratoryStatus(researchContext.getResearch(), laboratory, LaboratoryStatus.WAIT);
            }
        } else {
            log.error("NO CONFIGURATION!!!! SKIP.");
            MDCHelper.pop();
            return false;
        }
        MDCHelper.pop();
        return true;
    }

    private BiologicalMaterialMessage constructBiologicalMaterialMessage(
            final RbLaboratory laboratory, final MessageContext ctx, final Set<ResearchContext> researches
    ) {
        final BiologicalMaterialMessage result = new BiologicalMaterialMessage();
        result.setBiomaterial(constructBiomaterial(laboratory, ctx.getMqBiomaterial()));
        for (ResearchContext researchContext : researches) {
            MDCHelper.push(researchContext.getId());
            result.getResearch().add(constructAnalysis(laboratory, researchContext.getAnalysis()));
            MDCHelper.pop();
        }
        return result;
    }

    private Analysis constructAnalysis(final RbLaboratory laboratory, final Analysis source) {
        final Analysis result = new Analysis();
        result.setId(source.getId());
        result.setStatus(source.getStatus());
        result.setType(constructActionType(laboratory, source.getType()));
        result.setIsUrgent(source.getIsUrgent());
        result.setAssigner(source.getAssigner());
        result.setBegDate(source.getBegDate());
        result.setEndDate(source.getEndDate());
        for (Test test : source.getTests()) {
            MDCHelper.push(test.getId());
            result.getTests().add(constructTest(laboratory, test));
            MDCHelper.pop();
        }
        return result;
    }

    private Test constructTest(final RbLaboratory laboratory, final Test source) {
        final MapTestTypeToLaboratory mapping = mappingDao.getTestType(laboratory, source.getTest().getCode());
        if (mapping != null) {
            log.info("TestType replaced by [{}]['{}', '{}']", mapping.getPk().getCode(), mapping.getReplaceCode(), mapping.getReplaceName());
            final Test result = new Test();
            result.setId(source.getId());
            final RbTest indicator = new RbTest();
            result.setTest(indicator);
            indicator.setId(source.getTest().getId());
            indicator.setCode(StringUtils.isNotEmpty(mapping.getReplaceCode()) ? mapping.getReplaceCode() : source.getTest().getCode());
            indicator.setName(StringUtils.isNotEmpty(mapping.getReplaceName()) ? mapping.getReplaceName() : source.getTest().getName());
            return result;
        } else {
            return source;
        }
    }

    private ActionType constructActionType(final RbLaboratory laboratory, final ActionType source) {
        final MapResearchTypeToLaboratory mapping = mappingDao.getResearchType(laboratory, source.getCode());
        if (mapping != null) {
            log.info("ActionType replaced by [{}]['{}', '{}']", mapping.getPk().getCode(), mapping.getReplaceCode(), mapping.getReplaceName());
            final ActionType result = new ActionType();
            result.setId(source.getId());
            result.setCode(StringUtils.isNotEmpty(mapping.getReplaceCode()) ? mapping.getReplaceCode() : source.getCode());
            result.setName(StringUtils.isNotEmpty(mapping.getReplaceName()) ? mapping.getReplaceName() : source.getName());
            result.setFlatCode(source.getFlatCode());
            result.setMnemonic(source.getMnemonic());
            return result;
        } else {
            return source;
        }
    }

    private Biomaterial constructBiomaterial(final RbLaboratory laboratory, final Biomaterial source) {
        final Biomaterial result = new Biomaterial();
        result.setId(source.getId());
        result.setEvent(source.getEvent());
        result.setBiomaterialType(constructBiomaterialType(laboratory, source.getBiomaterialType()));
        result.setTestTubeType(constructTestTubeType(laboratory, source.getTestTubeType()));
        result.setAmount(source.getAmount());
        result.setDatetimePlanned(source.getDatetimePlanned());
        result.setDatetimeTaken(source.getDatetimeTaken());
        result.setStatus(source.getStatus());
        result.setBarcode(source.getBarcode());
        result.setNote(laboratory.getCode());
        result.setPerson(source.getPerson());
        return result;
    }

    private RbTestTubeType constructTestTubeType(final RbLaboratory laboratory, final RbTestTubeType source) {
        final MapTestTubeTypeToLaboratory mapping = mappingDao.getTestTubeType(laboratory, source.getCode());
        if (mapping != null) {
            log.info("TestTubeType replaced by [{}]['{}', '{}']", mapping.getPk().getCode(), mapping.getReplaceCode(), mapping.getReplaceName());
            final RbTestTubeType result = new RbTestTubeType();
            result.setId(source.getId());
            result.setCode(StringUtils.isNotEmpty(mapping.getReplaceCode()) ? mapping.getReplaceCode() : source.getCode());
            result.setName(StringUtils.isNotEmpty(mapping.getReplaceName()) ? mapping.getReplaceName() : source.getName());
            result.setColor(source.getColor());
            result.setImage(source.getImage());
            result.setVolume(source.getVolume());
            return result;
        } else {
            return source;
        }
    }

    private RbBiomaterialType constructBiomaterialType(final RbLaboratory laboratory, final RbBiomaterialType source) {
        final MapBiomaterialTypeToLaboratory mapping = mappingDao.getBiomaterialType(laboratory, source.getCode());
        if (mapping != null) {
            log.info("BiomaterialType replaced by [{}]['{}', '{}']", mapping.getPk().getCode(), mapping.getReplaceCode(), mapping.getReplaceName());
            final RbBiomaterialType result = new RbBiomaterialType();
            result.setId(source.getId());
            result.setCode(StringUtils.isNotEmpty(mapping.getReplaceCode()) ? mapping.getReplaceCode() : source.getCode());
            result.setName(StringUtils.isNotEmpty(mapping.getReplaceName()) ? mapping.getReplaceName() : source.getName());
            result.setSex(source.getSex());
            return result;
        } else {
            return source;
        }
    }

    private Message constructMessage(
            final String routingKey, final BiologicalMaterialMessage result, final MessageContext ctx, final String replyCode
    ) {
        final byte[] body = DeserializationFactory.serialize(result, DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON, StandardCharsets.UTF_8);
        messageDao.createOutMessage(body, ctx.getUUID(), routingKey, "BiologicalMaterialMessage", ctx.getBiomaterial());
        final MessagePropertiesBuilder propertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertiesBuilder.setAppId(appId);
        propertiesBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        propertiesBuilder.setRedelivered(false);
        propertiesBuilder.setContentLength(body.length);
        propertiesBuilder.setContentType(DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON);
        propertiesBuilder.setCorrelationIdString(ctx.getUUID());
        propertiesBuilder.setTimestamp(new LocalDateTime().toDate());
        propertiesBuilder.setType("BiologicalMaterialMessage");
        propertiesBuilder.setHeader("replyCode", replyCode);
        return new Message(body, propertiesBuilder.build());
    }


}
