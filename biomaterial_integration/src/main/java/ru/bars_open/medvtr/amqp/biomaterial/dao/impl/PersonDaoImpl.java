package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.PersonDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbSexDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbUnitDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Person;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbSex;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("personDao")
@Transactional
public class PersonDaoImpl extends AbstractDaoImpl<Person> implements PersonDao {

    @Autowired
    private RbUnitDao unitDao;

    @Autowired
    private RbSexDao sexDao;

    @Override
    public Person findOrCreate(final ru.bars_open.medvtr.mq.entities.base.Person source) {
        if (source == null) { return null; }
        final Person result = getByExternalId(source.getId());
        return result != null ? result : create(source);
    }

    @Override
    public Person create(final ru.bars_open.medvtr.mq.entities.base.Person source) {
        if (source == null) { return null; }
        final RbSex sex = sexDao.get(source.getSex());
        final Person result = EntityFactory.create(source, sex);
        save(result);
        return result;
    }

    @Override
    public Person getByExternalId(final Integer number) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("externalId", number));
        final Session session = sessionFactory.getCurrentSession();
        final List<Person> resultList = criteria.getExecutableCriteria(session).list();
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
    public Class<Person> getEntityClass() {
        return Person.class;
    }
}
