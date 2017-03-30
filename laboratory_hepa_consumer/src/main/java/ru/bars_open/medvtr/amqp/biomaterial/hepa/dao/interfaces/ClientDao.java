package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

import java.time.LocalDate;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:36 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface ClientDao extends AbstractDao<Client>{

    Client findOrCreate(Integer externalId, String lastName, String firstName, String patrName, LocalDate birthDate, Sex sex);

    Client getByNameAndBirthDate(String lastName, String firstName, String patrName, LocalDate birthDate, Sex sex);

    Client getByExternalId(Integer externalId);
}
