package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 13:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "analysis")
public class Analysis extends IdentifiedEntity implements Nameable{

    @Column(name = "a_name")
    private String name;

    public Analysis() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Analysis[").append(id);
        sb.append("]{ name='").append(convertFromDb(name)).append("'}");
        return sb.toString();
    }
}
