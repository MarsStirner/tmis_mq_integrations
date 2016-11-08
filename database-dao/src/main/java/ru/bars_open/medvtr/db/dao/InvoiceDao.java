package ru.bars_open.medvtr.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.PersistenceConfig;
import ru.bars_open.medvtr.db.entities.Invoice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PostConstruct
    public void init(){
       log.info("INIT!");
    }

    @PersistenceContext(unitName = PersistenceConfig.PERSISTENCE_UNIT_NAME_HOSPITAL)
    private EntityManager em;

    public Invoice getByNumber(final String number) {
        final List<Invoice> resultList = em.createQuery("SELECT a FROM Invoice a WHERE a.number = :number", Invoice.class)
                .setParameter("number", number)
                .getResultList();
        return resultList.iterator().hasNext() ? resultList.iterator().next() : null;
    }
}
