package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

/**
 * Author: Upatov Egor <br>
 * Date: 14.02.2017, 18:24 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface AbstractDaoWithExternal<T extends IdentifiedEntityWithExternal> extends AbstractDao<T> {
    T getByExternalId(final String externalId);
}
