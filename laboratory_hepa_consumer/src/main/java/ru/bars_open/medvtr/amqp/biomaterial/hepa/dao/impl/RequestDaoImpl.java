package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.RequestDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.view.SendAnalysisResultsToTMIS;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("requestDao")
public class RequestDaoImpl extends AbstractDaoImpl<Request> implements RequestDao {

    @Override
    public Class<Request> getEntityClass() {
        return Request.class;
    }

    @Override
    public Request createRequest(
            final Client client, final Soi soi, final Operator operator, final Analysis analysisType, final Material material, final String feedback
    ) {
        final Request result = new Request();
        result.setClient(client);
        result.setSoi(soi);
        result.setCreateDate(LocalDate.now());
        result.setCreatedBy(operator);
        result.setAnalysis(analysisType);
        result.setMaterial(material);
        result.setFeedback(convertToDb(feedback));
        save(result);
        return result;
    }

    @Override
    public List<SendAnalysisResultsToTMIS> getReadyForSend() {
        return em.createNamedQuery("SendAnalysisResultsToTMIS.getAll", SendAnalysisResultsToTMIS.class).getResultList();
    }


    @Override
    public Request setSent(final Request request) {
        request.setSent(1);
        return em.merge(request);
    }

    @Override
    public Request findOrCreate(
            final Client client, final Soi soi, final Operator operator, final Analysis analysisType, final Material material, final String feedback
    ) {
        final Request result = getByFeedback(feedback);
        if (result == null) {
            return createRequest(client, soi, operator, analysisType, material, feedback);
        } else {
            log.warn("Request with feedback[{}] already exists. ", feedback, result);
            boolean createNewOne = false;
            if (!Objects.equals(result.getClient(), client)) {
                log.warn(
                        "Existing Request[{}] has different Client[{}], so create a new one Request",
                        result.getId(),
                        result.getClient() != null ? result.getClient().getId() : null
                );
                createNewOne = true;
            } else if (!Objects.equals(result.getAnalysis(), analysisType)) {
                log.warn(
                        "Existing Request[{}] has different Analysis[{}], so create a new one Request",
                        result.getId(),
                        result.getAnalysis() != null ? result.getAnalysis().getId() : null
                );
                createNewOne = true;
            } else if (!Objects.equals(result.getSoi(), soi)) {
                log.warn(
                        "Existing Request[{}] has different Soi[{}], so override to Soi[{}]",
                        result.getId(),
                        result.getSoi() != null ? result.getSoi().getId() : null,
                        soi != null ? soi.getId() : null
                );
                result.setSoi(soi);
            } else if (!Objects.equals(result.getCreatedBy(), operator)) {
                log.warn(
                        "Existing Request[{}] has different Operator(createBy)[{}], so override to Operator[{}]",
                        result.getId(),
                        result.getCreatedBy() != null ? result.getCreatedBy().getId() : null,
                        operator != null ? operator.getId() : null
                );
                result.setCreatedBy(operator);
            } else if (!Objects.equals(result.getMaterial(), material)) {
                log.warn(
                        "Existing Request[{}] has different Material[{}], so override to Material[{}]",
                        result.getId(),
                        result.getMaterial() != null ? result.getMaterial().getId() : null,
                        material != null ? material.getId() : null
                );
                result.setMaterial(material);
            } else {
                log.info("Request[{}} Contents seems to be the same", result.getId());
            }
            return createNewOne ? createRequest(client, soi, operator, analysisType, material, feedback) : em.merge(result);
        }
    }

    private Request getByFeedback(final String feedback) {
        return em.createQuery("SELECT a " +
                                      "FROM Request a " +
                                      "JOIN FETCH a.soi " +
                                      "JOIN FETCH a.client " +
                                      "JOIN FETCH a.material " +
                                      "JOIN FETCH a.analysis " +
                                      "JOIN FETCH a.createdBy "+
                                      "WHERE a.feedback = :feedback", Request.class).setParameter("feedback", feedback).getResultList().stream().findFirst()
                .orElse(null);
    }
}
