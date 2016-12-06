package ru.bars_open.medvtr.db.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.impl.mapped.AbstractDeletableDaoImpl;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceItemDao;
import ru.bars_open.medvtr.db.entities.Invoice;
import ru.bars_open.medvtr.db.entities.InvoiceItem;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("invoiceItemDao")
public class InvoiceItemDaoImpl extends AbstractDeletableDaoImpl<InvoiceItem> implements InvoiceItemDao {
    @Override
    public Class<InvoiceItem> getEntityClass() {
        return InvoiceItem.class;
    }

    @Override
    public List<InvoiceItem> getByInvoice(final Invoice invoice, final boolean isRefund) {
        final DetachedCriteria criteria = getListCriteria();
        showDeleted(criteria, false);
        if (isRefund) {
            criteria.add(Restrictions.eq("refund.id", invoice.getId()));
        } else {
            criteria.add(Restrictions.eq("invoice.id", invoice.getId()));
        }
        return criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }
}
