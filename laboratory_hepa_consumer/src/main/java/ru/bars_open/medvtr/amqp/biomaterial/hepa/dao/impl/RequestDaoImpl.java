package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.RequestDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("requestDao")
public class RequestDaoImpl extends AbstractDaoImpl<Request> implements RequestDao {

    private final List<String> SOI_TO_SEND;

    @Autowired
    public RequestDaoImpl(ConfigurationHolder cfg) {
        this.SOI_TO_SEND = cfg.getStringList("polling.soi");
        log.info("<init> with SOI_TO_SEND:\n{}", SOI_TO_SEND.stream().collect(Collectors.joining(",\n")));
    }


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
    public List<Request> getNotSent() {
        return em.createQuery("SELECT a " +
                                      "FROM Request a " +
                                      "INNER JOIN FETCH a.result " +
                                      "INNER JOIN FETCH a.soi "+
                                      "LEFT JOIN FETCH a.completedBy " +
                                      "WHERE " +
                                      "a.sent <> 1 " +
                                      "AND a.feedback IS NOT NULL " +
                                      "AND a.soi.code IN :soi_codes_to_send", Request.class)
                .setParameter("soi_codes_to_send", SOI_TO_SEND)
                .getResultList();
    }

    @Override
    public Request setSent(final Request request) {
        request.setSent(1);
        return em.merge(request);
    }
}
