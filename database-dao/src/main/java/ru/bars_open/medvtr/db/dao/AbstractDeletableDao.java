package ru.bars_open.medvtr.db.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;


public abstract class AbstractDeletableDao<T extends DeletableEntity> extends AbstractDao<T> {

    public DetachedCriteria showDeleted(final DetachedCriteria criteria, final boolean showDeleted){
        return showDeleted ? criteria : criteria.add(Restrictions.eq("deleted", false));
    }

}
