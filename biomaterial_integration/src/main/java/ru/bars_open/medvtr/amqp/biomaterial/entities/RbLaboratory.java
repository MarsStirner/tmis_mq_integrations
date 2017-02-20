package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.persistence.Column;
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
public class RbLaboratory extends IdentifiedEntity{
    @Column(name="code", nullable = false)
    private String code;

    @Column(name="name", nullable = false)
    private String name;

    public RbLaboratory() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {

        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbLaboratory[").append(id);
        sb.append("][").append(code);
        sb.append("]{ name='").append(name);
        sb.append("'}");
        return sb.toString();
    }
}
