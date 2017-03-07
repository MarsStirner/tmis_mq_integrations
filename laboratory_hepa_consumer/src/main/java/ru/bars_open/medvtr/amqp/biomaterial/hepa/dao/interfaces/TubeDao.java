package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Tube;

import java.time.LocalDateTime;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface TubeDao extends AbstractDao<Tube> {
    Tube findOrCreate(Client client, LocalDateTime datetimeTaken);
}
