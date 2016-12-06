package ru.bars_open.medvtr.db.dao.impl.mapped;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;


public abstract class AbstractDeletableDaoImpl<T extends DeletableEntity> extends AbstractDaoImpl<T> {

    public DetachedCriteria showDeleted(final DetachedCriteria criteria, final boolean showDeleted) {
        return showDeleted ? criteria : criteria.add(Restrictions.eq("deleted", false));
    }

}
