package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;

import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.2017, 16:15 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface MappingDao {
    Map<RbLaboratory, MapResearchTypeToLaboratory> findLaboratoryMapping(Research dbResearch, Biomaterial dbBiomaterial);
}
