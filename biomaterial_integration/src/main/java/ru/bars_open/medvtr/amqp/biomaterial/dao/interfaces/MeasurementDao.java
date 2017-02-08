package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Measurement;
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 20:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface MeasurementDao extends AbstractDao<Measurement> {

    Measurement create(ValueAndUnit source);
}
