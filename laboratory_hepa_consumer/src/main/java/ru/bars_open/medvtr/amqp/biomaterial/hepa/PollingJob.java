package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import ca.uhn.fhir.context.FhirContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.logging.MDC;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private static final String HEPA_CODE_SYSTEM = "http://hepaDB.blood.local";

    @Autowired
    private ConfigurationHolder cfg;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ActionPropertyDao actionPropertyDao;

    @Autowired
    private ActionDao actionDao;


    @Autowired
    @Qualifier("fhirContext)")
    private FhirContext fhir;

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
//        DiagnosticReport result = new DiagnosticReport();
//        result.setId(String.valueOf(request.getId()));
//        //Локальный идентификатор, назначенный отчету исполнителем заказа, обычно информационной системой провайдера службы диагностики.
//        result.addIdentifier(craeteIdentifier(request.getRequest()));
//        result.setStatus(DiagnosticReport.DiagnosticReportStatus.FINAL);
//        result.setCategory(getCategory(request.getAnalysis()));
//        result.setCode(getCode(request.getAnalysis()));
//        result.setSubjectTarget(new Patient().setId(String.valueOf(request.getClient().getId())));
//        result.setEffective(new DateTimeType(Date.from(request.getCompleteDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
//        result.setIssued(new java.util.Date());
//        result.addPerformer().setDisplay("HEPA");
//        result.addRequest(new Reference(request.getFeedback()));
//        result.addSpecimen(new Reference(request.getMaterial().getName()));
//        result.addImage().setLink( new Reference(request.getLink()));
//
//        log.info(fhir.newJsonParser().setPrettyPrint(true).encodeResourceToString( result));

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

//    /**
//     * Локальный идентификатор, назначенный отчету исполнителем заказа, обычно информационной системой провайдера службы диагностики.
//     * @param request запрос теста к лаборатории
//     * @return идентфикатор результатов анализа
//     */
//    private Identifier craeteIdentifier(final Request request) {
//        final Identifier result =new Identifier();
//        result.setSystem(HEPA_CODE_SYSTEM);
//        result.setIdBase();
//        return result;
//    }
//
//    private CodeableConcept getCode(final Analysis analysis) {
//        new CodeableConcept().addCoding(new Coding(HEPA_CODE_SYSTEM, String.valueOf(request.getAnalysis().getId()), request.getAnalysis().getName()))
//    }
//
//    /**
//     *  Категория услуги
//     *  Diagnostic Service Section Codes   http://hl7.org/fhir/ValueSet/diagnostic-service-sections
//     *  OID:	2.16.840.1.113883.4.642.2.117 http://hl7.org/fhir/ValueSet/v2-0074
//     * @param analysis тип Анализа в Хепе
//     * @return Концепт категории услуг
//     */
//    private CodeableConcept getCategory(final Analysis analysis) {
//
//        final CodeableConcept result = new CodeableConcept();
//        result.addCoding(new Coding("http://hl7.org/fhir/v2/0074", "LAB", "Laboratory"));
//        return result;
//    }

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
