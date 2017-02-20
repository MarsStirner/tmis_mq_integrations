package ru.bars_open.medvtr.amqp.biomaterial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MappingDao;
import ru.bars_open.medvtr.amqp.biomaterial.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.dto.ResearchContext;
import ru.bars_open.medvtr.amqp.biomaterial.entities.MapToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.util.MDCHelper;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 18:13 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("laboratoryMapper")
public class LaboratoryMapper {

    private static final Logger log = LoggerFactory.getLogger(LaboratoryMapper.class);

    @Autowired
    private MappingDao mappingDao;

    public Map<RbLaboratory, Set<ResearchContext>> map(final MessageContext ctx) {
        final Map<RbLaboratory, Set<ResearchContext>> result = new LinkedHashMap<>(5);
        for (ResearchContext researchContext : ctx.getResearchs()) {
            MDCHelper.push(researchContext.getId());
            log.info("Start [{}]-'{}'", researchContext.getResearchType(), researchContext.getAnalysis().getType().getName());
            final Set<MapToLaboratory> mapping = mappingDao.findLaboratoryMapping(researchContext.getResearchType(),
                                                                                  ctx.getBiomaterialType(),
                                                                                  ctx.getTestTybeType()
            );
            mergeSendStructures(result, mapping, researchContext);
            MDCHelper.pop();
        }
        return result;
    }

    private void mergeSendStructures(
            final Map<RbLaboratory, Set<ResearchContext>> result, final Set<MapToLaboratory> mapping, final ResearchContext researchContext
    ) {
        if (mapping.isEmpty()) {
            log.debug("No mapping found");
            return;
        }
        for (MapToLaboratory entry : mapping) {
            final Set<ResearchContext> resultSet = result.getOrDefault(entry.getLaboratory(), new LinkedHashSet<>());
            resultSet.add(researchContext);
            result.put(entry.getLaboratory(), resultSet);
        }
    }

}
