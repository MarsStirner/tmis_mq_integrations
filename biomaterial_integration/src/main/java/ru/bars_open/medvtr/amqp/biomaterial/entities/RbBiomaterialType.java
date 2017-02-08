package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 19:44 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name= "rbBiomaterialType")
public class RbBiomaterialType extends ReferenceBookEntity {

    /**
     * Пол пациента
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sex_id", nullable=false)
    private RbSex sex;

    /**
     * Для группировки
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id", nullable=true)
    private RbBiomaterialType group;

    @Column(name= "autocreated", nullable = false)
    private boolean autocreated = false;


    public RbBiomaterialType() {
    }

    public RbSex getSex() {
        return sex;
    }

    public void setSex(final RbSex sex) {
        this.sex = sex;
    }

    public RbBiomaterialType getGroup() {
        return group;
    }

    public void setGroup(final RbBiomaterialType group) {
        this.group = group;
    }

    public boolean isAutocreated() {
        return autocreated;
    }

    public void setAutocreated(final boolean autocreated) {
        this.autocreated = autocreated;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbBiomaterialType[").append(id);
        sb.append("][").append(code);
        sb.append("]{ name='").append(name);
        sb.append("', autocreated=").append(autocreated);
        sb.append('}');
        return sb.toString();
    }
}
