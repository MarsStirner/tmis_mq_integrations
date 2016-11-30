package ru.bars_open.medvtr.db.dao.interfaces;

import org.hibernate.criterion.DetachedCriteria;
import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;

public interface AbstractDao<T extends IdentifiedEntity> {

    Class<T> getEntityClass();

    DetachedCriteria getSimplestCriteria();

    DetachedCriteria getEntityCriteria();

    T get(Integer id);

    Integer save(T entity);

    void update(T entity);
}
