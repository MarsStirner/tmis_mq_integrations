package ru.bars_open.medvtr.db.dao.interfaces;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;

public interface AbstractDao<T extends IdentifiedEntity> {

    Class<T> getEntityClass();

    default DetachedCriteria getSimplestCriteria(){
        return DetachedCriteria.forClass(getEntityClass()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    DetachedCriteria getEntityCriteria();

}
