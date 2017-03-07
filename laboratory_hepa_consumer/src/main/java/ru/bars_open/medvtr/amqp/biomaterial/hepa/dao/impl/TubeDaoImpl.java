package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.TubeDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Client;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Tube;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("tubeDao")
public class TubeDaoImpl extends AbstractDaoImpl<Tube> implements TubeDao {


    @Override
    public Class<Tube> getEntityClass() {
        return Tube.class;
    }

    public Tube getByClientAndDate(final Client client, final LocalDateTime datetimeTaken) {
        final List<Tube> resultList = em.createQuery("SELECT a FROM Tube a WHERE a.client.id = :client AND a.adate = :adate", getEntityClass())
          .setParameter("client", client.getId())
          .setParameter("adate", datetimeTaken.toLocalDate())
          .getResultList();
        switch (resultList.size()) {
            case 0: {
                log.debug("Not found by client[{}] and date[{}]", client.getId(), datetimeTaken.toLocalDate());
                return null;
            }
            case 1: {
                return resultList.iterator().next();
            }
            default: {
                log.warn("By by client[{}] and date[{}] founded {} rows. Return first", client.getId(), datetimeTaken.toLocalDate(), resultList.size());
                return resultList.iterator().next();
            }
        }
    }

    @Override
    public Tube findOrCreate(final Client client, final LocalDateTime datetimeTaken) {
        final Tube result = getByClientAndDate(client, datetimeTaken);
        return result != null ? result : create(client, datetimeTaken);
    }

    private Tube create(final Client client, final LocalDateTime datetimeTaken) {
        final Tube result = new Tube();
        result.setAdate(datetimeTaken.toLocalDate());
        result.setClient(client);
        save(result);
        return result;
    }
}
