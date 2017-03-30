package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.ClientDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

import java.time.LocalDate;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;


/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("clientDao")
public class ClientDaoImpl extends AbstractDaoImpl<Client> implements ClientDao {


    public Client create(
            final Integer externalId, final String lastName, final String firstName, final String patrName, final LocalDate birthDate, final Sex sex
    ) {
        final Client result = new Client();
        result.setExternalId(externalId);
        result.setFirstName(convertToDb(firstName));
        result.setLastName(convertToDb(lastName));
        result.setPatrName(convertToDb(patrName));
        result.setBirthDate(birthDate);
        switch (sex) {
            case MALE:
                result.setSex(1);
                break;
            case FEMALE:
                result.setSex(2);
                break;
            case UNKNOWN:
                result.setSex(null);
                break;
        }
        result.setComment(null);
        save(result);
        return result;
    }


    @Override
    public Client findOrCreate(
            final Integer externalId,
            final String lastName,
            final String firstName,
            final String patrName,
            final LocalDate birthDate,
            final Sex sex
    ) {
        Client result = getByExternalId(externalId);
        if (result != null) {
            log.debug("Found by externalId[{}] - {}", externalId, result.toShortString());
            return result;
        } else {
            result = getByNameAndBirthDate(lastName, firstName, patrName, birthDate, sex);
            if (result != null) {
                log.debug("Found by FIO and BirthDay - {}", result.toShortString());
                result.setExternalId(externalId);
                result.setBirthDate(birthDate);
                return em.merge(result);
            }
        }
        return create(externalId, lastName, firstName, patrName, birthDate, sex);
    }


    @Override
    public Client getByNameAndBirthDate(final String lastName, final String firstName, final String patrName, final LocalDate birthDate, final Sex sex) {
        return em.createQuery(
                "SELECT c " +
                        "FROM Client c " +
                        "WHERE c.lastName = :lastName " +
                        "AND c.firstName = :firstName " +
                        "AND c.patrName = :patrName " +
                        "AND (c.birthDate = :birthDate OR c.birthDate = :birthYear) " +
                        "ORDER BY c.birthDate DESC",
                Client.class
        )
                .setParameter("lastName", convertToDb(lastName))
                .setParameter("firstName", convertToDb(firstName))
                .setParameter("patrName", convertToDb(patrName))
                .setParameter("birthDate", birthDate)
                .setParameter("birthYear", birthDate.withDayOfYear(1))
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Client getByExternalId(final Integer externalId) {
        return em.createQuery("SELECT c FROM Client c WHERE c.externalId = :externalId", Client.class).setParameter("externalId", externalId)
                .getResultList().stream().findFirst().orElse(null);
    }


    @Override
    public Class<Client> getEntityClass() {
        return Client.class;
    }
}
