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
    Biomaterial findOrCreate(final ru.bars_open.medvtr.mq.entities.base.Biomaterial biomaterial);

    Biomaterial create(final ru.bars_open.medvtr.mq.entities.base.Biomaterial biomaterial);

    Biomaterial getByExternalId(final String number);
}
