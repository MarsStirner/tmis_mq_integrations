package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.PersistenceConfig;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.LaboratoryDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 16:46 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("laboratoryDao")
@Transactional
public class LaboratoryDaoImpl extends AbstractDaoImpl<RbLaboratory> implements LaboratoryDao {

    private static final Logger log = LoggerFactory.getLogger(LaboratoryDaoImpl.class);

    @Autowired
    @Qualifier(PersistenceConfig.SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with SessionFactory[@{}]", Integer.toHexString(sessionFactory.hashCode()));
    }

    @Override
    public Class<RbLaboratory> getEntityClass() {
        return RbLaboratory.class;
    }


    @Override
    @SuppressWarnings("unchecked")
    public RbLaboratory get(final String code) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("code", code));
        final Session session = sessionFactory.getCurrentSession();
        final List<RbLaboratory> resultList = criteria.getExecutableCriteria(session).list();
        switch (resultList.size()) {
            case 0: {
                log.debug("Not found by code[{}]", code);
                return null;
            }
            case 1: {
                log.debug("Found by code[{}]", code);
                return resultList.iterator().next();
            }
            default: {
                log.warn("By code [{}] founded {} rows. Return first", code, resultList.size());
                return resultList.iterator().next();
            }
        }
    }
}
