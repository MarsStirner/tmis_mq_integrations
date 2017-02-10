package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import org.joda.time.DateTime;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:36 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface ClientDao extends AbstractDao<Client>{

    Client findOrCreate(String lastName, String firstName, String patrName, DateTime birthDate, final Sex sex);
}
