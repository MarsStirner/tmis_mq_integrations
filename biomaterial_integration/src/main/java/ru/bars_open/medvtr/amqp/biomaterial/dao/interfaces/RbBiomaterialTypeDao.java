package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbBiomaterialType;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 20:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface RbBiomaterialTypeDao extends ReferenceBookDao<RbBiomaterialType> {

    RbBiomaterialType create(ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType source);

    RbBiomaterialType findOrCreate(ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType source);
}
