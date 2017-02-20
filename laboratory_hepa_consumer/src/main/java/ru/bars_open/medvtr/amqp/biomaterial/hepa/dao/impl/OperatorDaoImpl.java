package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.OperatorDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Operator;

import java.util.List;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertToDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("operatorDao")
public class OperatorDaoImpl extends AbstractDaoImpl<Operator> implements OperatorDao {


    @Override
    public Class<Operator> getEntityClass() {
        return Operator.class;
    }

    @Override
    public Operator get(final String code) {
        final List<Operator> resultList = em.createQuery("SELECT a FROM Operator a WHERE a.name = :name", getEntityClass())
                .setParameter("name",convertToDb(code)).getResultList();
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
