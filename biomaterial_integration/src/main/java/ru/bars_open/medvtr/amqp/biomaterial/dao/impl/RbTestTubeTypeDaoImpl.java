package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractReferenceBookDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MeasurementDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbTestTubeTypeDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Measurement;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbTestTubeType;

import javax.transaction.Transactional;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("testTubeTypeDao")
@Transactional
public class RbTestTubeTypeDaoImpl extends AbstractReferenceBookDaoImpl<RbTestTubeType> implements RbTestTubeTypeDao {

    @Autowired
    private MeasurementDao measurementDao;

   @Override
    public RbTestTubeType create(final ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType source) {
       if (source == null) { return null; }
       final Measurement volume = measurementDao.create(source.getVolume());
       final RbTestTubeType result = EntityFactory.create(source, volume);
       save(result);
       return result;
    }

    @Override
    public RbTestTubeType findOrCreate(final ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType source) {
        if (source == null) { return null; }
        final RbTestTubeType result = getByCode(source.getCode());
        return result != null ? result : create(source);
    }

    @Override
    public Class<RbTestTubeType> getEntityClass() {
        return RbTestTubeType.class;
    }
}
