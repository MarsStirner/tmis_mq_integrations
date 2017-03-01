package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.SoiDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Soi;

import java.util.List;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("soiDao")
public class SoiDaoImpl extends AbstractDaoImpl<Soi> implements SoiDao {


    @Override
    public Class<Soi> getEntityClass() {
        return Soi.class;
    }

    @Override
    public Soi get(final String code) {
        final List<Soi> resultList = em.createQuery("SELECT a FROM Soi a WHERE a.code = :code", getEntityClass())
                .setParameter("code",convertToDb(code)).getResultList();
           switch (resultList.size()) {
            case 0: {
                log.debug("Not found by code[{}]", code);
                return null;
            }
            case 1: {
                return resultList.iterator().next();
            }
            default: {
                log.warn("By code[{}] founded {} rows. Return first", code, resultList.size());
                return resultList.iterator().next();
            }
        }
    }
}
