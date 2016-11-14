package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Тип оплаты
 */
@Entity
@Table(name="rbPayType")
public class RbPayType extends ReferenceBookEntity{
    //Наличный расчет
    public static final String CODE_CASH = "cash";
    //Безналичный расчет
    public static final String CODE_CASHLESS = "non_cash";

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbPayType[").append(id);
        sb.append("]{ code='").append(code);
        sb.append("', name='").append(name);
        sb.append("'}");
        return sb.toString();
    }
}
