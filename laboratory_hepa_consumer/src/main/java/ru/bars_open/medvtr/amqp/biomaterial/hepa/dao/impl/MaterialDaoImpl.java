package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.impl.mapped.AbstractDaoImpl;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.MaterialDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.Material;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("materialDao")
public class MaterialDaoImpl extends AbstractDaoImpl<Material> implements MaterialDao {


    @Override
    public Class<Material> getEntityClass() {
        return Material.class;
    }

    @Override
    public Material get(final String code) {
        try {
            return get(Integer.valueOf(code));
        } catch (NumberFormatException e){
            log.error("Search by NON-numeric code[{}] not allowed", code);
            return null;
        }
    }
}
