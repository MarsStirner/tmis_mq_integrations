package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.PersistenceConfig;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.IdentifiedEntity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Transactional(value = "hepaTransactionManager")
public abstract class AbstractDaoImpl<T extends IdentifiedEntity> implements AbstractDao<T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext(unitName = PersistenceConfig.PERSISTENCE_UNIT_NAME_HEPA)
    protected EntityManager em;

    @PostConstruct
    public void init() {
        log.info("<init> for work with [{}] and EM[@{}]", getEntityClass().getSimpleName(), Integer.toHexString(em.hashCode()));
    }

    public abstract Class<T> getEntityClass();

    @Override
    public T get(Integer id) {
       return em.find(getEntityClass(), id);
    }

    @Override
    public Integer save(T entity) {
        em.persist(entity);
        log.debug("Save entity: {}", entity.toShortString());
        return entity.getId();
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
        log.debug("Update entity: {}", entity);
    }


}
