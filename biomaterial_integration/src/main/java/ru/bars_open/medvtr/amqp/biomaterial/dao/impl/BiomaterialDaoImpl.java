package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.*;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("biomaterialDao")
@Transactional
public class BiomaterialDaoImpl extends AbstractDaoImpl<Biomaterial> implements BiomaterialDao {

    @Autowired
    private MeasurementDao measurementDao;
    @Autowired
    private RbTestTubeTypeDao testTubeTypeDao;
    @Autowired
    private RbBiomaterialTypeDao biomaterialTypeDao;
    @Autowired
    private PersonDao personDao;


    @Override
    public Class<Biomaterial> getEntityClass() {
        return Biomaterial.class;
    }

    @Override
    public Biomaterial findOrCreate(final ru.bars_open.medvtr.mq.entities.base.Biomaterial source) {
        if (source == null) { return null; }
        final Biomaterial result = getByExternalId(source.getId());
        return result != null ? result : create(source);
    }


    @Override
    public Biomaterial create(final ru.bars_open.medvtr.mq.entities.base.Biomaterial source) {
        if (source == null) { return null; }
        final Measurement amount = measurementDao.create(source.getAmount());
        final RbTestTubeType testTubeType = testTubeTypeDao.findOrCreate(source.getTestTubeType());
        final RbBiomaterialType biomaterialType = biomaterialTypeDao.findOrCreate(source.getBiomaterialType());
        final Person person = personDao.findOrCreate(source.getPerson());

        final Biomaterial result = EntityFactory.create(source, amount, testTubeType, biomaterialType, person);
        save(result);
        return result;
    }

    @Override
    public Biomaterial getByExternalId(final Integer number) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("externalId", number));
        final Session session = sessionFactory.getCurrentSession();
        final List<Biomaterial> resultList = criteria.getExecutableCriteria(session).list();
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
    public DetachedCriteria getEntityCriteria() {
        final DetachedCriteria result = getSimplestCriteria();
        result.createAlias("amount", "amount", JoinType.INNER_JOIN);
        result.createAlias("testTybeType", "testTybeType", JoinType.INNER_JOIN);
        result.createAlias("biomaterialType", "biomaterialType", JoinType.INNER_JOIN);
        result.createAlias("person", "person", JoinType.INNER_JOIN);
        return result;
    }

}
