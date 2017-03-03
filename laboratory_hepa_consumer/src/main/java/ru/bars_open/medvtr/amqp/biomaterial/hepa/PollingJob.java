package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated.ActionDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated.ActionPropertyDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.view.AnalysisStatus;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.view.SendAnalysisResultsToTMIS;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.MDCHelper;
import ru.bars_open.medvtr.db.entities.Action;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.ActionPropertyType;
import ru.bars_open.medvtr.db.entities.util.ActionStatus;
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

    @Autowired
    private ActionDao actionDao;

    @Override
    @Transactional(value = "hepaTransactionManager")
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        log.info("Start Job[{}] by Trigger[{}]", context.getJobDetail().getKey(), context.getTrigger().getKey());
        if (!cfg.getBoolean("polling.enabled")) {
            log.warn("End Job[{}]. Polling is disabled", context.getJobDetail().getKey());
            return;
        }
        final List<SendAnalysisResultsToTMIS> resultsList = requestDao.getReadyForSend();
        log.info("There are {} unsent results for TMIS", resultsList.size());
        for (SendAnalysisResultsToTMIS item : resultsList) {
            MDCHelper.start(item.getId());
            if (processAnalysisResult(item)) {
                requestDao.setSent(item.getRequest());
            }
            log.debug("End. sent = {}", item.getRequest().getSent());
            MDC.clear();
        }
        log.info("End Job[{}] by Trigger[{}]", context.getJobDetail().getKey(), context.getTrigger().getKey());
    }


    @Transactional(value = "hospitalTransactionManager", propagation = Propagation.REQUIRES_NEW)
    private boolean processAnalysisResult(final SendAnalysisResultsToTMIS request) {
        try {
            log.debug("Request is ready. Completed by {} at {}", request.getCompletedBy().toShortString(), request.getCompleteDate());
            final ActionProperty ap = setTestResult(convertFromDb(request.getFeedback()), convertFromDb(request.getResultString()));
            if (ap == null) {
                log.error("AP[{}] not found (by feedback)", convertFromDb(request.getFeedback()));
                return false;
            }
            setLink(ap.getAction(), ap.getType(), convertFromDb(request.getLink()));
            changeAction(ap.getAction(), request.getStatus(), request.getComment());
            return true;
        } catch (Exception e){
            log.error("Unexpected Exception while processing Request[{}]: ", request.getId(), e);
            return false;
        }
    }

    private ActionProperty setTestResult(final String feedback, final String resultString) {
        final int parsedFeedback = NumberUtils.toInt(feedback);
        if (parsedFeedback > 0) {
            final ActionProperty ap = actionPropertyDao.get(parsedFeedback);
            if (ap == null) { return null; }
            log.info("{}. Need to set value='{}'", ap.toLogString(), resultString);
            actionPropertyDao.setValue(ap, resultString);
            return ap;
        } else {
            log.error("Request has invalid feedback[{}]", feedback);
            return null;
        }
    }

    private ActionProperty setLink(final Action action, final ActionPropertyType apt, final String link) {
        if (StringUtils.isEmpty(link)) {
            log.debug("Link is empty or null");
            return null;
        }
        final String fullLink = cfg.getString("polling.uploadPath") + link;
        if (StringUtils.isEmpty(apt.getCode())) {
            log.debug("Link[{}] is present, but source{} has null or empty code", fullLink, apt.toLogString());
            return null;
        }
        final String fullAptCode = apt.getCode() + cfg.getString("polling.actionPropertyTypeLinkSuffix");
        final ActionProperty ap = actionPropertyDao.getByActionAndCode(action, fullAptCode);
        if (ap != null) {
            actionPropertyDao.setValue(ap, fullLink);
            log.debug("Link[{}] is set to {}", fullLink, ap.toLogString());
            return ap;
        } else {
            log.debug("Link[{}] is present, but no AP with APT.code='{}' found", fullLink, fullAptCode);
            return null;
        }
    }

    private void changeAction(final Action action, final AnalysisStatus status, final String comment) {
        if (AnalysisStatus.ERROR.equals(status)) {
            log.warn("AnalysisResult.status is ERROR, so change Action[{}] status to CANCELLED", action.getId());
            actionDao.setStatus(action, ActionStatus.CANCELLED, comment);
        } else {
            if (ActionStatus.STARTED.equals(action.getStatus()) || ActionStatus.WAITING.equals(action.getStatus())) {
                log.warn("Action[{}] status[{}] is for change to FINISHED.", action.getId(), action.getStatus());
                actionDao.setStatus(action, ActionStatus.FINISHED);
            } else {
                log.warn("Action[{}] status[{}] is not for change.", action.getId(), action.getStatus());
            }
        }
    }
}
