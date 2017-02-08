package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:31 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:Какое-то измерение или количество чего-либо
 */
@Entity
@Table(name="Measurement")
public class Measurement extends IdentifiedEntity{

    /**
     * Значение
     */
    @Column(name="value", nullable = false)
    private double value;

    /**
     * Единица измерения {rbUnit}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "unit_id", nullable = false)
    private RbUnit unit;

    public Measurement() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(final RbUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{ ");
        sb.append(value);
        sb.append(" | ").append(unit.getCode());
        sb.append(" }");
        return sb.toString();
    }
}
