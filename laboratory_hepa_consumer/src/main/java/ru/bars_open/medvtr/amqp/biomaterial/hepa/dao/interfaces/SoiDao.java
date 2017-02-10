package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Soi;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface SoiDao extends AbstractDao<Soi> {
    Soi get(String code);
}
