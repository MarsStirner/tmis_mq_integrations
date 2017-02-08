package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;


/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 20:26 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface BiomaterialDao extends AbstractDao<Biomaterial>{
    Biomaterial findOrCreate(ru.bars_open.medvtr.mq.entities.base.Biomaterial biomaterial);

    Biomaterial create(ru.bars_open.medvtr.mq.entities.base.Biomaterial biomaterial);

    Biomaterial getByExternalId(Integer number);
}
