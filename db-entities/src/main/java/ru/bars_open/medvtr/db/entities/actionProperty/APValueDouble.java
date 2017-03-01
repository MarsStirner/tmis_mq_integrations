package ru.bars_open.medvtr.db.entities.actionProperty;

import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 20:50 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "ActionProperty_Double")
public class APValueDouble extends APValue<Double> {

    @Override
    public boolean setValue(final Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof Number) {
            this.value = ((Number) value).doubleValue();
        } else if ("".equals(value)) {
            this.value = 0.0;
        } else {
            try {
                this.value = NumberUtils.createDouble(value.toString());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
