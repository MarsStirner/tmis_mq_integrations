package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.db.entities.Invoice;
import ru.bars_open.medvtr.db.entities.InvoiceItem;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:24 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface InvoiceItemDao extends AbstractDao<InvoiceItem> {

    default List<InvoiceItem> getByRefund(final Invoice invoice){
        return getByInvoice(invoice, true);
    }

    default List<InvoiceItem> getByInvoice(final Invoice invoice) {
        return getByInvoice(invoice, false);
    }

    List<InvoiceItem> getByInvoice(final Invoice invoice, boolean isRefund);
}
