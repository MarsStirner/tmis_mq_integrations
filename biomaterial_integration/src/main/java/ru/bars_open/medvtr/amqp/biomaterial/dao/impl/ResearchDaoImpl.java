package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.PersonDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbResearchTypeDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.ResearchDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.*;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("researchDao")
@Transactional
public class ResearchDaoImpl extends AbstractDaoImpl<Research> implements ResearchDao {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private RbResearchTypeDao rbResearchTypeDao;

    @Override
    public Research create(
            final Analysis source, final Biomaterial biomaterial, final Message message
    ) {
        if (source == null) { return null; }
        final Person assigner = personDao.findOrCreate(source.getAssigner());
        final RbResearchType researchType = rbResearchTypeDao.getByCode(source.getType().getCode());
        final Research result = EntityFactory.create(source, biomaterial, message, researchType, assigner);
        save(result);
        return result;
    }

    @Override
    public Research getByExternalId(final Integer number) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("externalId", number));
        final Session session = sessionFactory.getCurrentSession();
        final List<Research> resultList = criteria.getExecutableCriteria(session).list();
        switch (resultList.size()) {
            case 0: {
                log.debug("Not found by externalId[{}]", number);
                return null;
            }
            case 1: {
                return resultList.iterator().next();
            }
            default: {
                log.warn("By externalId [{}] founded {} rows. Return first", number, resultList.size());
                return resultList.iterator().next();
            }
        }
    }

    @Override
    public Research findOrCreate(final Analysis source, final Biomaterial biomaterial, final Message message) {
        if (source == null) { return null; }
        final Research result = getByExternalId(source.getId());
        return result != null ? result : create(source, biomaterial, message);
    }


    @Override
    public Class<Research> getEntityClass() {
        return Research.class;
    }
}
