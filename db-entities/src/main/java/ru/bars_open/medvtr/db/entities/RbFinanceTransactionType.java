package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Тип оплаты
 */
@Entity
@Table(name = "rbFinanceTransactionType")
public class RbFinanceTransactionType extends ReferenceBookEntity{
    //Движение средств на счёте плательщика
    public static final String CODE_PAYER_BALANCE = "payer_balance";
    //Движение средств по выставленным счетам
    public static final String CODE_INVOICE = "invoice";
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbFinanceTransactionType[").append(id);
        sb.append("]{ code='").append(code);
        sb.append("', name='").append(name);
        sb.append("'}");
        return sb.toString();
    }
}
