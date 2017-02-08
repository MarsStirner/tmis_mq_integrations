package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractReferenceBookDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MeasurementDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbBiomaterialTypeDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbSexDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.util.EntityFactory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbBiomaterialType;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbSex;

import javax.transaction.Transactional;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("biomaterialTypeDao")
@Transactional
public class RbBiomaterialTypeDaoImpl extends AbstractReferenceBookDaoImpl<RbBiomaterialType> implements RbBiomaterialTypeDao {

    @Autowired
    private MeasurementDao measurementDao;

    @Autowired
    private RbSexDao sexDao;

    @Override
    public RbBiomaterialType create(final ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType source) {
        if (source == null) { return null; }
        final RbBiomaterialType parent = findOrCreate(source.getParent());
        final RbSex sex = sexDao.get(source.getSex());
        final RbBiomaterialType result = EntityFactory.create(source, parent, sex);
        save(result);
        return result;
    }

    @Override
    public RbBiomaterialType findOrCreate(final ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType source) {
        if (source == null) { return null; }
        final RbBiomaterialType result = getByCode(source.getCode());
        return result != null ? result : create(source);
    }

    @Override
    public Class<RbBiomaterialType> getEntityClass() {
        return RbBiomaterialType.class;
    }
}
