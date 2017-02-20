package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import org.joda.time.DateTime;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Tube;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface TubeDao extends AbstractDao<Tube> {
    Tube findOrCreate(Client client, DateTime datetimeTaken);
}
