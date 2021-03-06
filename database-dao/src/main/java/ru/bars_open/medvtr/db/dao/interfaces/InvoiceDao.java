package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.db.entities.Invoice;

public interface InvoiceDao extends AbstractDao<Invoice> {
    Invoice getByNumber(final String number);
}
