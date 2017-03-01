package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 27.02.2017, 18:38 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="results")
public class Result extends IdentifiedEntity implements Nameable {

    @Column(name="r_name")
    private String name;

    @Column(name="long_name")
    private String longName;

    public Result() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(final String longName) {
        this.longName = longName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result[").append(id);
        sb.append("]{name='").append(convertFromDb(name)).append('\'');
        sb.append(", longName='").append(convertFromDb(longName)).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
