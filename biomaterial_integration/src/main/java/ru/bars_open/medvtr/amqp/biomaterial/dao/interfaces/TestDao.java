package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Test;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface TestDao extends AbstractDao<Test> {
    Test findOrCreate(ru.bars_open.medvtr.mq.entities.base.util.Test source, Research research, Message message);
}
