package ru.bars_open.medvtr.db.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.bars_open.medvtr.db.PersistenceConfig;
import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractDao<T extends IdentifiedEntity> implements ru.bars_open.medvtr.db.dao.interfaces.AbstractDao<T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(PersistenceConfig.HOSPITAL_SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with [{}] and SessionFactory[@{}]",
                getEntityClass().getSimpleName(),
                Integer.toHexString(sessionFactory.hashCode())
        );
    }


    public abstract Class<T> getEntityClass();


    @Override
    public DetachedCriteria getSimplestCriteria(){
        return DetachedCriteria.forClass(getEntityClass()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    @Override
    public DetachedCriteria getEntityCriteria(){
        return getSimplestCriteria();
    }


    @Override
    public T get(Integer id){
        if(id == null || id <= 0) {
            return null;
        }
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.idEq(id));
        final Session session = sessionFactory.getCurrentSession();
        final List<T> resultList = criteria.getExecutableCriteria(session).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }

    @Override
    public Integer save(T entity) {
        final Serializable assignedID = sessionFactory.getCurrentSession().save(entity);
        log.debug("Save entity[assignedID={}]: {}", assignedID, entity);
        return (Integer) assignedID;
    }

    @Override
    public void update(T entity){
        sessionFactory.getCurrentSession().update(entity);
        log.debug("Update entity: {}", entity);
    }


}
