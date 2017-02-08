package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface ResearchDao extends AbstractDao<Research> {

    Research create(Analysis research, Biomaterial biomaterial, Message message);

    Research getByExternalId(Integer number);

    Research findOrCreate(Analysis research, Biomaterial biomaterial, Message message);
}
