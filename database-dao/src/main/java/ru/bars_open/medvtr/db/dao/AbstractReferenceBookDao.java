package ru.bars_open.medvtr.db.dao;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.db.dao.interfaces.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractReferenceBookDao extends AbstractDao<ReferenceBookEntity> implements ReferenceBookDao{

    @Override
    public <E> E getByCode(final Class<E> clazz, final String code) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("code", code));
        final List<E> resultList = criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }

}
