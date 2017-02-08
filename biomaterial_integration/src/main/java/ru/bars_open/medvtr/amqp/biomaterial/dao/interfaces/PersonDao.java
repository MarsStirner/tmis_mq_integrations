package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Person;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface PersonDao extends AbstractDao<Person> {

    Person findOrCreate(ru.bars_open.medvtr.mq.entities.base.Person source);

    Person create(ru.bars_open.medvtr.mq.entities.base.Person source);

    Person getByExternalId(Integer number);

}
