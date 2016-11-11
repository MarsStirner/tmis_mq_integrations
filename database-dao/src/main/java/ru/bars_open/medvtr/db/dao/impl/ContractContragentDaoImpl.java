package ru.bars_open.medvtr.db.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.PersistenceConfig;
import ru.bars_open.medvtr.db.dao.interfaces.ContractContragentDao;
import ru.bars_open.medvtr.db.entities.ContractContragent;

import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("contractContragentDao")
public class ContractContragentDaoImpl implements ContractContragentDao{
    private static final Logger log = LoggerFactory.getLogger(ContractContragentDao.class);

    @Autowired
    @Qualifier(PersistenceConfig.HOSPITAL_SESSION_FACTORY)
    private SessionFactory sessionFactory;

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
    public ContractContragent get(Integer id) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.idEq(id));
        final Session session = sessionFactory.getCurrentSession();
        final List<ContractContragent> resultList = criteria.getExecutableCriteria(session).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }
}
