package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.actionProperty.APValue;


/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 16:32 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface ActionPropertyDao extends AbstractDao<ActionProperty> {
    APValue<Number> setValue(ActionProperty ap, String result);
}

