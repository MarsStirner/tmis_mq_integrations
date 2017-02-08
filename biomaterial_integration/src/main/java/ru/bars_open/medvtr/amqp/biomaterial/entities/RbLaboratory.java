package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

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
public class RbLaboratory extends ReferenceBookEntity{

    /**
     * Имя обменника, куда надо публиковать сообщение
     */
    @Column(name = "exchange", nullable = false)
    private String exchange;

    /**
     * Ключ, с которым нужно публиковать сообщения
     */
    @Column(name = "routingKey", nullable = false)
    private String routingKey;

    public RbLaboratory() {
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(final String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(final String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbLaboratory[").append(id);
        sb.append("][").append(code);
        sb.append("]{ name='").append(name);
        sb.append("', exchange='").append(exchange).append('\'');
        sb.append(", routingKey='").append(routingKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
