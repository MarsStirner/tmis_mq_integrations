package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Material;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface MaterialDao extends AbstractDao<Material> {
    default Material get(RbBiomaterialType biomaterialType) {
        return get(biomaterialType.getCode());
    }

    Material get(String code);

    Material get(Integer code);

}
