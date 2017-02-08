package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbSex;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 15:14 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface RbSexDao extends ReferenceBookDao<RbSex> {
    RbSex get(Sex sex);
}
