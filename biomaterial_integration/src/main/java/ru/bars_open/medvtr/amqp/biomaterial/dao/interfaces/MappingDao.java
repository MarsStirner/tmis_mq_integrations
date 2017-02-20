package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.entities.MapToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapBiomaterialTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTubeTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTypeToLaboratory;

import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.2017, 16:15 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface MappingDao {
    Set<MapToLaboratory> findLaboratoryMapping(final String researchType, final String biomaterialType, final String testTubeType);

    MapBiomaterialTypeToLaboratory getBiomaterialType(RbLaboratory laboratory, String code);

    MapTestTubeTypeToLaboratory getTestTubeType(RbLaboratory laboratory, String code);

    MapTestTypeToLaboratory getTestType(RbLaboratory laboratory, String code);

    MapResearchTypeToLaboratory getResearchType(RbLaboratory laboratory, String code);
}
