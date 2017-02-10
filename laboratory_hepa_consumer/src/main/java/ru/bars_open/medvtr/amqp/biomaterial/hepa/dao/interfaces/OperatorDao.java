package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Operator;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface OperatorDao extends AbstractDao<Operator> {
    Operator get(String code);
}
