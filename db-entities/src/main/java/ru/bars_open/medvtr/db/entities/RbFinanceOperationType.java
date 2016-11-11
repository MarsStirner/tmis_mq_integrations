package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Тип оплаты
 */
@Entity
@Table(name = "rbFinanceOperationType")
public class RbFinanceOperationType extends ReferenceBookEntity {

    //Поступление денежных средств
    public static final String CODE_PAYER_BALANCE_IN = "payer_balance_in";
    //Возврат денежных средств
    public static final String CODE_PAYER_BALANCE_OUT = "payer_balance_out";
    //Оплата по счёту
    public static final String CODE_INVOICE_PAY = "invoice_pay";
    //Отмена оплаты по счёту
    public static final String CODE_INVOICE_CANCEL = "invoice_cancel";

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbFinanceOperationType[").append(id);
        sb.append("]{ code='").append(code);
        sb.append("', name='").append(name);
        sb.append("'}");
        return sb.toString();
    }
}
