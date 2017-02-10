package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped;

import org.hibernate.criterion.DetachedCriteria;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.IdentifiedEntity;


public interface AbstractDao<T extends IdentifiedEntity> {

    Class<T> getEntityClass();

    DetachedCriteria getSimplestCriteria();

    DetachedCriteria getEntityCriteria();

    DetachedCriteria getListCriteria();

    T get(Integer id);

    Integer save(T entity);

    void update(T entity);
}
