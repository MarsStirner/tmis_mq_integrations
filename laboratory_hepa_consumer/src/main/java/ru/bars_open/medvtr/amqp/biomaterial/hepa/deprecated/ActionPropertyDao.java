package ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated;


import org.springframework.transaction.annotation.Transactional;
import ru.bars_open.medvtr.db.entities.Action;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.actionProperty.APValue;

import java.util.List;


/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 16:32 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Transactional(transactionManager = "hospitalTransactionManager")
public interface ActionPropertyDao {
    APValue setValue(ActionProperty ap, int index, Object value);

    default APValue setValue(ActionProperty ap, Object value) {
        return setValue(ap, 0, value);
    }

    APValue getValue(ActionProperty ap, int index);

    default APValue getValue(ActionProperty ap) {
        return getValue(ap, 0);
    }

    ActionProperty get(Integer id);

    Integer save(ActionProperty entity);

    void update(ActionProperty entity);


    List<ActionProperty> getAssignedByAction(Action action);

    ActionProperty getByActionAndCode(Action action, String actionPropertyTypeCode);
}

