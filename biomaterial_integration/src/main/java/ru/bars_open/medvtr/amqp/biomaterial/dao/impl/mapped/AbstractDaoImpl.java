package ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.bars_open.medvtr.amqp.biomaterial.PersistenceConfig;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractDaoImpl<T extends IdentifiedEntity> implements AbstractDao<T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(PersistenceConfig.SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with [{}] and SessionFactory[@{}]", getEntityClass().getSimpleName(), Integer.toHexString(sessionFactory.hashCode()));
    }

    public abstract Class<T> getEntityClass();


    @Override
    public DetachedCriteria getSimplestCriteria() {
        return DetachedCriteria.forClass(getEntityClass()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    @Override
    public DetachedCriteria getListCriteria() {
        return getSimplestCriteria();
    }

    @Override
    public DetachedCriteria getEntityCriteria() {
        return getListCriteria();
    }

    @Override
    public T get(Integer id) {
        if (id == null || id <= 0) { return null; }
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.idEq(id));
        final Session session = sessionFactory.getCurrentSession();
        final List<T> resultList = criteria.getExecutableCriteria(session).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }

    @Override
    public Integer save(T entity) {
        final Serializable assignedID = sessionFactory.getCurrentSession().save(entity);
        log.trace("Save entity: {}", entity.toShortString());
        return (Integer) assignedID;
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
        log.trace("Update entity: {}", entity);
    }


}
