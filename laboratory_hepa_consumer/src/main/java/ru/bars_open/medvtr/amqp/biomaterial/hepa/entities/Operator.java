package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="operator")
public class Operator extends IdentifiedEntity{

    @Column(name= "uname")
    private String code;

    public Operator() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Operator{");
        sb.append("code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
