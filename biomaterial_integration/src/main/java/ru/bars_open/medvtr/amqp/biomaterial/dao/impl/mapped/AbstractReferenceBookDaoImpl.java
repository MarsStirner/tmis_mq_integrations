package ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped;


import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class AbstractReferenceBookDaoImpl<T extends ReferenceBookEntity> extends AbstractDaoImpl<T> implements ReferenceBookDao<T> {

    @Override
    public T getByCode(final String code) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        final List<T> resultList = criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }

    @Override
    public Map<String, T> getMap(final Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new HashMap<>(0);
        }
        final Map<String, T> result = new HashMap<>(codes.size());
        final DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.in("code", codes));
        final List<T> resultList = criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
        for (final T item : resultList) {
            result.put(item.getCode(), item);
        }
        return result;
    }

}
