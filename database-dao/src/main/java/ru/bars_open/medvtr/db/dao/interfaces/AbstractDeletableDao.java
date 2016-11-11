package ru.bars_open.medvtr.db.dao.interfaces;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

public interface AbstractDeletableDao<T extends DeletableEntity> extends AbstractDao<T>{
    default DetachedCriteria showDeleted(final DetachedCriteria criteria, final boolean showDeleted){
        return showDeleted ? criteria : criteria.add(Restrictions.eq("deleted", false));
    }
}
