package ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped;


import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDaoWithExternal;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDaoWithExternalImpl<T extends IdentifiedEntityWithExternal> extends AbstractDaoImpl<T>
        implements AbstractDaoWithExternal<T>
{

    @Override
    public T getByExternalId(final String externalId) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("externalId", externalId));
        final Session session = sessionFactory.getCurrentSession();
        final List<T> resultList = criteria.getExecutableCriteria(session).list();
        switch (resultList.size()) {
            case 0: {
                log.debug("Not found by externalId[{}]", externalId);
                return null;
            }
            case 1: {
                log.debug("Found by externalId[{}]", externalId);
                return resultList.iterator().next();
            }
            default: {
                log.warn("By externalId [{}] founded {} rows. Return first", externalId, resultList.size());
                return resultList.iterator().next();
            }
        }
    }

}
