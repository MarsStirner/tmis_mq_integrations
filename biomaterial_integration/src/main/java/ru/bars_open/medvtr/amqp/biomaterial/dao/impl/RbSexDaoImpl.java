package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractReferenceBookDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.RbSexDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbSex;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 15:13 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("rbSexDao")
public class RbSexDaoImpl extends AbstractReferenceBookDaoImpl<RbSex> implements RbSexDao {
    @Override
    public Class<RbSex> getEntityClass() {
        return RbSex.class;
    }

    @Override
    public RbSex getByCode(final String code) {
        return super.getByCode(StringUtils.isNotEmpty(code) ? code : "UNDEFINED");
    }

    @Override
    public RbSex get(final Sex sex) {
        return getByCode(Sex.UNKNOWN.equals(sex)? "UNDEFINED" : sex.value());
    }
}
