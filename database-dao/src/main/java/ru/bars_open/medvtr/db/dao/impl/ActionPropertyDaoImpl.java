package ru.bars_open.medvtr.db.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.db.dao.interfaces.ActionPropertyDao;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.actionProperty.APValue;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 16:40 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("actionPropertyDao")
public class ActionPropertyDaoImpl extends AbstractDaoImpl<ActionProperty> implements ActionPropertyDao{

    @Override
    public APValue<Number> setValue(final ActionProperty ap, final String result) {
        return null;
    }

    @Override
    public Class<ActionProperty> getEntityClass() {
        return ActionProperty.class;
    }
}
