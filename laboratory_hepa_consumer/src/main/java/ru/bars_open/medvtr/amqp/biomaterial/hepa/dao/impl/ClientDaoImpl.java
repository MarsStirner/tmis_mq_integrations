package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.ClientDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

import java.util.List;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.PersistenceConfig.convertToPizdets;

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
        result.setFirstName(convertToPizdets(firstName));
        result.setLastName(convertToPizdets(lastName));
        result.setPatrName(convertToPizdets(patrName));
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
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("lastName", convertToPizdets(lastName)));
        criteria.add(Restrictions.eq("firstName", convertToPizdets(firstName)));
        criteria.add(Restrictions.eq("patrName", convertToPizdets(patrName)));
        criteria.add(Restrictions.eq("birthDate", birthDate.toLocalDate().toDate()));
        final Session session = sessionFactory.getCurrentSession();
        final List<Client> resultList = criteria.getExecutableCriteria(session).list();
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
