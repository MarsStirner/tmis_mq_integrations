package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.RequestDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated.ActionPropertyDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Request;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.MDCHelper;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.util.TypeName;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.util.List;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 27.02.2017, 13:27 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("pollingJob")
public class PollingJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(PollingJob.class);

    @Autowired
    private ConfigurationHolder cfg;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ActionPropertyDao actionPropertyDao;

    @Override
    @Transactional(value = "hepaTransactionManager")
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        log.info("Start Job[{}] by Trigger[{}]", context.getJobDetail().getKey(), context.getTrigger().getKey());
        if (!cfg.getBoolean("polling.enabled")) {
            log.warn("End Job[{}]. Polling is disabled", context.getJobDetail().getKey());
            return;
        }
        final List<Request> resultsList = requestDao.getNotSent();
        log.info("There are {} unsent results for TMIS", resultsList.size());
        for (Request request : resultsList) {
            if(processAnalysisResult(request)) {
               requestDao.setSent(request);
            }
            log.debug("End. sent = {}", request.getSent());
        }
        MDC.clear();
        log.info("End Job[{}] by Trigger[{}]", context.getJobDetail().getKey(), context.getTrigger().getKey());
    }


    @Transactional(value ="hospitalTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public boolean processAnalysisResult(final Request request) {
        MDCHelper.start(request.getId());
        log.debug("Request is ready. Completed by {} at {}", request.getCompletedBy().toShortString(), request.getCompleteDate());
        final ActionProperty ap;
        try {
            ap = actionPropertyDao.get(Integer.valueOf(request.getFeedback()));
        } catch (NumberFormatException e) {
            log.error("End. Request has non-numeric feedback[{}]", request.getFeedback());
            return false;
        }
        if(ap == null){
            log.error("ActionProperty[{}] not found", request.getFeedback());
            return false;
        }
        final String result = getResultByType(ap.getType().getTypeName(), request);
        log.info("ActionProperty[{}] found, type={}. Set value to '{}'", ap.getId(), ap.getType().getTypeName(), result);
        actionPropertyDao.setValue(ap, result);
        return true;
    }

    private String getResultByType(final TypeName typeName, final Request request) {
        switch (typeName) {
            case String:
                return request.getAmount() != null ? String.valueOf(request.getAmount()) : convertFromDb(request.getResult().getName());
            case Double:
                return request.getAmount() != null ? String.valueOf(request.getAmount()) : convertFromDb(request.getResult().getName());
            case Text:
                final String result = request.getAmount() != null ? String.valueOf(request.getAmount()) : convertFromDb(request.getResult().getName());
                return StringUtils.isEmpty(request.getPhoresis()) ? result : makeLink(request.getPhoresis(), result);
            default:
                return request.getAmount() != null ? String.valueOf(request.getAmount()) : convertFromDb(request.getResult().getName());
        }
    }

    private String makeLink(final String phoresis, final String result) {
        return "<a href=\"" + cfg.getString("polling.uploadPath") + "/" + phoresis + "\">" + result + "</a>";
    }
}
