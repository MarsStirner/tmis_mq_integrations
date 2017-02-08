package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbTestTubeType;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 20:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface RbTestTubeTypeDao extends ReferenceBookDao<RbTestTubeType> {

    RbTestTubeType create(ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType source);

    RbTestTubeType findOrCreate(ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType testTubeType);
}
