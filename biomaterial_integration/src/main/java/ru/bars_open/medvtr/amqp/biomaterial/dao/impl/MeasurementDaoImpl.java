package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MeasurementDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbUnitDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Measurement;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbUnit;
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;

import javax.transaction.Transactional;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("measurementDao")
@Transactional
public class MeasurementDaoImpl extends AbstractDaoImpl<Measurement> implements MeasurementDao {

    @Autowired
    private RbUnitDao unitDao;

    @Override
    public Measurement create(final ValueAndUnit source) {
        if (source == null) { return null; }
        final RbUnit unit = unitDao.getByCode(source.getUnit().getCode());
        final Measurement result = EntityFactory.create(source.getValue(), unit);
        save(result);
        return result;
    }

    @Override
    public Class<Measurement> getEntityClass() {
        return Measurement.class;
    }
}
