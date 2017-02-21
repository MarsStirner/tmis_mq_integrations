package ru.bars_open.medvtr.amqp.biomaterial;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.BiomaterialDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.LaboratoryDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.ResearchDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.StatusDao;
import ru.bars_open.medvtr.amqp.biomaterial.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.LaboratoryStatus;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.util.MDCHelper;
import ru.bars_open.medvtr.amqp.biomaterial.util.MessageParser;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 21.02.2017, 9:35 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component("laboratoryResponseConsumer")
public class LaboratoryResponseConsumer implements ChannelAwareMessageListener{
    private static final Logger log = LoggerFactory.getLogger(LaboratoryResponseConsumer.class);

    private final String SENT_ROUTING_KEY;

    @Autowired
    private LaboratoryDao laboratoryDao;

    @Autowired
    private ResearchDao researchDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private ResponseSender responseSender;

    @Autowired
    private BiomaterialDao biomaterialDao;

    @Autowired
    public LaboratoryResponseConsumer(final ConfigurationHolder cfg) {
        this. SENT_ROUTING_KEY = cfg.getString(ConfigurationKeys.LABORATORY_RESPONSE_ROUTING_KEY_SENT);
    }

    @Override
    @Transactional
    public void onMessage(final Message amqpMessage, final Channel channel) throws Exception {
        //[B] Преобразование
        final MessageContext ctx = new MessageContext(amqpMessage, MessageParser.parse(amqpMessage));
        log.debug("Message parsed");
        final String replyCode = (String) amqpMessage.getMessageProperties().getHeaders().get("replyCode");
        final RbLaboratory laboratory = laboratoryDao.get(replyCode);
        log.info("RbLaboratory = {}", laboratory);
        for (Analysis analysis : ctx.getMqResearch()) {
            MDCHelper.push(analysis.getId());
            log.debug("Start [{}]-'{}'", analysis.getType().getCode(), analysis.getType().getName());
            final Research research = researchDao.getByExternalId(String.valueOf(analysis.getId()));
            log.debug("Research={}", research);
            if(research == null){
                log.warn("No research found by externalId[{}] !!!", analysis.getId());
            }  else {
                statusDao.setLaboratoryStatus(research, laboratory, SENT_ROUTING_KEY.equals(ctx.getRoutingKey()) ?  LaboratoryStatus.SENT : LaboratoryStatus.ERROR);
            }
            MDCHelper.pop();
        }
        checkBiomaterialComplete(ctx);
        log.info("### End. Successfully processed!");
    }

    private void checkBiomaterialComplete(final MessageContext ctx) {
        log.info("Start check biomaterial status");
        final Biomaterial biomaterial = biomaterialDao.getByExternalId(String.valueOf(ctx.getMqBiomaterial().getId()));
        final List<Research> researchList = researchDao.getByBiomaterial(biomaterial);
        boolean isAllSent = true;
        for (Research research : researchList) {
            MDCHelper.push(research.getExternalId());
            if(statusDao.isSent(research)){
                log.debug("isSent to all laboratories");
            } else {
                isAllSent = false;
                log.info("is NOT Sent to all laboratories!");
                MDCHelper.pop();
                break;
            }
            MDCHelper.pop();
        }
        if(isAllSent){
            log.info("All Researches isSent!");
            responseSender.sent(ctx.getAmqpMessage());
        }
    }
}
