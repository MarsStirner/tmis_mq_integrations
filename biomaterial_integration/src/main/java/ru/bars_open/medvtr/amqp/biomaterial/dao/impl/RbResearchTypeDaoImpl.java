package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractReferenceBookDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbResearchTypeDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbResearchType;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 15:13 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("rbResearchTypeDao")
public class RbResearchTypeDaoImpl extends AbstractReferenceBookDaoImpl<RbResearchType> implements RbResearchTypeDao {
    @Override
    public Class<RbResearchType> getEntityClass() {
        return RbResearchType.class;
    }
}
