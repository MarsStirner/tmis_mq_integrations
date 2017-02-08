package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractReferenceBookDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbUnitDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbUnit;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 15:13 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("rbUnitDao")
public class RbUnitDaoImpl extends AbstractReferenceBookDaoImpl<RbUnit> implements RbUnitDao {
    @Override
    public Class<RbUnit> getEntityClass() {
        return RbUnit.class;
    }
}
