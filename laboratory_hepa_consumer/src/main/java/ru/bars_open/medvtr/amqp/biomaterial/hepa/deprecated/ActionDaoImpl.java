package ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.entities.Action;
import ru.bars_open.medvtr.db.entities.util.ActionStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.2017, 17:12 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("actionDao")
public class ActionDaoImpl implements ActionDao {


    @PersistenceContext(unitName = PersistenceConfig.PERSISTENCE_UNIT_NAME_HOSPITAL)
    private EntityManager em;


    @Override
    public Action setStatus(final Action action, final ActionStatus status, final String comment) {
        action.setStatus(status);
        if(StringUtils.isNotEmpty(comment)){
            action.setNote(comment);
        }
        switch (status){
            case STARTED:
                break;
            case WAITING:
                break;
            case FINISHED:
                action.setEndDate(new org.joda.time.LocalDateTime());
                break;
            case CANCELLED:
                break;
            case UNKNOWN:
                break;
        }
        em.merge(action);
        return action;
    }
}
