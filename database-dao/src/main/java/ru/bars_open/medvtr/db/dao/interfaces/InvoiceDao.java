package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.entities.Invoice;

public interface InvoiceDao extends AbstractDeletableDao<Invoice>{
    Invoice getByNumber(final String number);
}
