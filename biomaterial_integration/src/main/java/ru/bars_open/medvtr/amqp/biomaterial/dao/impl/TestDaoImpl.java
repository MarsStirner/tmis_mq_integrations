package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoWithExternalImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.TestDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Test;

import javax.transaction.Transactional;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("testDao")
@Transactional
public class TestDaoImpl extends AbstractDaoWithExternalImpl<Test> implements TestDao {

    @Override
    public Class<Test> getEntityClass() {
        return Test.class;
    }

    @Override
    public Test findOrCreate(
            final ru.bars_open.medvtr.mq.entities.base.util.Test source, final Research research, final Message message
    ) {
        final Test result = getByExternalId(String.valueOf(source.getId()));
        return result != null ? result : create(source, research, message);
    }

    private Test create(final ru.bars_open.medvtr.mq.entities.base.util.Test source, final Research research, final Message message) {
        final Test result = new Test();
        result.setResearch(research);
        result.setTestType(source.getTest().getCode());
        result.setExternalId(String.valueOf(source.getId()));
        save(result);
        return result;
    }
}
