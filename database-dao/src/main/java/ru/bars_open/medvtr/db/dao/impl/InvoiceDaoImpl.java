package ru.bars_open.medvtr.db.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.PersistenceConfig;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceDao;
import ru.bars_open.medvtr.db.entities.Invoice;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 20:14 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@SuppressWarnings("unchecked")
@Transactional
@Repository("invoiceDao")
public class InvoiceDaoImpl implements InvoiceDao {
    private static final Logger log = LoggerFactory.getLogger(InvoiceDao.class);

    @Autowired
    @Qualifier(PersistenceConfig.HOSPITAL_SESSION_FACTORY)
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Initialized [@{}] with SessionFactory[@{}]",
                Integer.toHexString(this.hashCode()),
                Integer.toHexString(sessionFactory.hashCode())
        );
    }

    @Override
    public Class<Invoice> getEntityClass() {
        return Invoice.class;
    }

    @Override
    public Invoice getByNumber(final String number) {
        final DetachedCriteria criteria = getEntityCriteria();
        showDeleted(criteria, false);
        criteria.add(Restrictions.eq("number", number));

        final Session session = sessionFactory.getCurrentSession();

        final List<Invoice> resultList = criteria.getExecutableCriteria(session).list();
        switch(resultList.size()){
            case 0: {
                return null;
            }
            case 1: {
                return resultList.iterator().next();
            }
            default: {
                log.warn("By number ['{}'] founded {} rows. Return first", number, resultList.size());
                return resultList.iterator().next();
            }
        }
    }

    @Override
    public DetachedCriteria getEntityCriteria() {
        final DetachedCriteria result = getSimplestCriteria();
        result.createAlias("contract", "contract", JoinType.INNER_JOIN);
        result.createAlias("parent", "parent", JoinType.LEFT_OUTER_JOIN);
        return result;
    }


}
