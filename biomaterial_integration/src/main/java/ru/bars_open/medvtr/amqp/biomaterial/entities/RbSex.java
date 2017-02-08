package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:  Справочник полов
 */
@Entity
@Table(name="rbSex")
public class RbSex extends ReferenceBookEntity {
    @Override
    public String toString() {
        return code;
    }
}
