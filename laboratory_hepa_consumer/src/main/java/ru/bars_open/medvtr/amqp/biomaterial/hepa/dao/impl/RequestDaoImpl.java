package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.RequestDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.view.SendAnalysisResultsToTMIS;

import java.util.Date;
import java.util.List;

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
        result.setCreateDate(new Date());
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
}
