package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.ClientDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;


/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("clientDao")
public class ClientDaoImpl extends AbstractDaoImpl<Client> implements ClientDao {


    public Client create(final String lastName, final String firstName, final String patrName, final DateTime birthDate, final Sex sex) {
        final Client result = new Client();
        result.setFirstName(convertToDb(firstName));
        result.setLastName(convertToDb(lastName));
        result.setPatrName(convertToDb(patrName));
        result.setBirthDate(birthDate.toDate());
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
    public Client findOrCreate(final String lastName, final String firstName, final String patrName, final DateTime birthDate, final Sex sex) {
        final Client result = getByNameAndBirthDate(lastName, firstName, patrName, birthDate, sex);
        return result != null ? result : create(lastName, firstName, patrName, birthDate, sex);
    }

    private Client getByNameAndBirthDate(
            final String lastName, final String firstName, final String patrName, final DateTime birthDate, final Sex sex
    ) {
        final CriteriaBuilder qb = em.getCriteriaBuilder();
        final CriteriaQuery<Client> query = qb.createQuery(getEntityClass());
        final Root<Client> root = query.from(getEntityClass());
        query.select(root).where(
                qb.and(
                        qb.equal(root.get("lastName"), qb.parameter(String.class, "lastName")),
                        qb.equal(root.get("firstName"), qb.parameter(String.class, "firstName")),
                        qb.equal(root.get("patrName"), qb.parameter(String.class, "patrName")),
                        qb.equal(root.get("birthDate"), qb.parameter(Date.class, "birthDate"))
                )
        );
        final List<Client> resultList = em.createQuery(query)
                .setParameter("lastName", convertToDb(lastName))
                .setParameter("firstName",convertToDb(firstName))
                .setParameter("patrName", convertToDb(patrName))
                .setParameter("birthDate", birthDate.toLocalDate().toDate())
                .getResultList();
        switch (resultList.size()) {
            case 0: {
                log.debug("Not found by FIO and BirthDate");
                return null;
            }
            case 1: {
                return resultList.iterator().next();
            }
            default: {
                log.warn("By FIO and BirthDate founded {} rows. Return first", resultList.size());
                return resultList.iterator().next();
            }
        }
    }

    @Override
    public Class<Client> getEntityClass() {
        return Client.class;
    }
}
