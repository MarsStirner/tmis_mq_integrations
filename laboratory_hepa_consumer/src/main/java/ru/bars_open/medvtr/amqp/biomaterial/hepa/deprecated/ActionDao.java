package ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated;

import org.springframework.transaction.annotation.Transactional;
import ru.bars_open.medvtr.db.entities.Action;
import ru.bars_open.medvtr.db.entities.util.ActionStatus;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.2017, 17:10 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Transactional(transactionManager = "hospitalTransactionManager")
public interface ActionDao {
    Action setStatus(Action action, ActionStatus status, String comment);

    default Action setStatus(Action action, ActionStatus status){
        return setStatus(action, status, null);
    }
}
