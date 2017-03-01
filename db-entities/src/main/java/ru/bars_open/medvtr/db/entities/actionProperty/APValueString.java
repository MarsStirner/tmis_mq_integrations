package ru.bars_open.medvtr.db.entities.actionProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 20:50 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "ActionProperty_String")
public class APValueString extends APValue<String> {

    @Override
    public boolean setValue(final Object value) {
        this.value = value != null ? value.toString() : null;
        return true;
    }
}
