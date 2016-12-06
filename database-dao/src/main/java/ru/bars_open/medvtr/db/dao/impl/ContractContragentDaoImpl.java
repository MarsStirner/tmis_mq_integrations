package ru.bars_open.medvtr.db.dao.impl;


import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.db.dao.interfaces.ContractContragentDao;
import ru.bars_open.medvtr.db.entities.Client;
import ru.bars_open.medvtr.db.entities.ContractContragent;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository("contractContragentDao")
public class ContractContragentDaoImpl extends AbstractDaoImpl<ContractContragent> implements ContractContragentDao {

    @Override
    public Class<ContractContragent> getEntityClass() {
        return ContractContragent.class;
    }

    @Override
    public DetachedCriteria getEntityCriteria() {
        final DetachedCriteria result = getSimplestCriteria();
        result.createAlias("client", "client", JoinType.LEFT_OUTER_JOIN);
        result.createAlias("organisation", "organisation", JoinType.LEFT_OUTER_JOIN);
        return result;
    }

    @Override
    public ContractContragent getByClient(final Integer clientId) {
        if (clientId == null) { return null; }
        final DetachedCriteria criteria = getEntityCriteria().add(Restrictions.eq("client.id", clientId));
        final Session session = sessionFactory.getCurrentSession();

        final List<ContractContragent> resultList = criteria.getExecutableCriteria(session).list();
        switch (resultList.size()) {
            case 0: { return null; }
            case 1: { return resultList.iterator().next(); }
            default: {
                log.warn("By Client[{}] founded {} rows. Return first", clientId, resultList.size());
                return resultList.iterator().next();
            }
        }
    }

    @Override
    public ContractContragent getByClient(final Client client) {
        return client == null ? null : getByClient(client.getId());
    }
}
