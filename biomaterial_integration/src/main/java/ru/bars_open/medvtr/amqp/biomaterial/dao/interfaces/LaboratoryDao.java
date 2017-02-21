package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;

/**
 * Author: Upatov Egor <br>
 * Date: 21.02.2017, 9:45 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface LaboratoryDao extends AbstractDao<RbLaboratory> {
    RbLaboratory get(String code);
}
