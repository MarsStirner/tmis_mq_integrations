package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="soi")
public class Soi extends IdentifiedEntity implements Nameable{

    @Column(name= "s_name")
    private String name;

    @Column(name="code")
    private String code;

    public Soi() {
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
        final StringBuilder sb = new StringBuilder("Soi{");
        sb.append("name='").append(convertFromDb(name)).append('\'');
        sb.append("code='").append(convertFromDb(code)).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
