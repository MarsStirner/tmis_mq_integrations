package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:06 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Лабораторные системы, и куда публиковать сообщения для них
 */
@Entity
@Table(name="rbLaboratory")
public class RbLaboratory extends ReferenceBookEntity{
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbLaboratory[").append(id);
        sb.append("][").append(code);
        sb.append("]{ name='").append(name);
        sb.append("'}");
        return sb.toString();
    }
}
