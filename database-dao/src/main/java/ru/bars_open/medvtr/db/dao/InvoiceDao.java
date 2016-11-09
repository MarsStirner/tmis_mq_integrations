package ru.bars_open.medvtr.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.PersistenceConfig;
import ru.bars_open.medvtr.db.entities.Invoice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 20:14 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("invoiceDao")
@Transactional
public class InvoiceDao{

    private static final Logger log = LoggerFactory.getLogger(InvoiceDao.class);

   @Autowired
   @Qualifier("hospital")
   private SessionFactory sessionFactory

    public Invoice getByNumber(final String number) {
       DetachedCriteria.forClass(this.getClass()).getExecutableCriteria(em.unwrap(Session.class)).list();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }
}
