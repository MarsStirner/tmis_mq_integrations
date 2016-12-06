package ru.bars_open.medvtr.db.dao.impl.mapped;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.db.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import java.util.*;

@SuppressWarnings("unchecked")
public abstract class AbstractReferenceBookDaoImpl extends AbstractDaoImpl<ReferenceBookEntity> implements ReferenceBookDao {

    @Override
    public <E extends ReferenceBookEntity> E getByCode(final Class<E> clazz, final String code) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("code", code));
        final List<E> resultList = criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }

    @Override
    public <E extends ReferenceBookEntity> Map<String, E> getMap(final Class<E> clazz, final Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new HashMap<>(0);
        }
        final Map<String, E> result = new HashMap<>(codes.size());
        final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.in("code", codes));
        final List<E> resultList = criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
        for (final E item : resultList) {
            result.put(item.getCode(), item);
        }
        return result;
    }
}
