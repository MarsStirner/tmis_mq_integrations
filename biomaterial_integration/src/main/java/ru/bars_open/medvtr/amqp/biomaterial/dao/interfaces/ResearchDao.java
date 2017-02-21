package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDaoWithExternal;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface ResearchDao extends AbstractDaoWithExternal<Research> {
    Research create(Analysis research, Biomaterial biomaterial, Message message);
    Research findOrCreate(Analysis research, Biomaterial biomaterial, Message message);

    List<Research> getByBiomaterial(Biomaterial biomaterial);
}
