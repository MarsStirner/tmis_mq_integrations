package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.PersistenceConfig;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MappingDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.2017, 16:16 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Transactional
@Repository("mappingDao")
public class MappingDaoImpl implements MappingDao {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(PersistenceConfig.SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with SessionFactory[@{}]", Integer.toHexString(sessionFactory.hashCode()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<RbLaboratory, MapResearchTypeToLaboratory> findLaboratoryMapping(final Research research, final Biomaterial biomaterial) {
        if (research.getResearchType() == null) { return Collections.emptyMap(); }
        final DetachedCriteria criteria = DetachedCriteria.forClass(MapResearchTypeToLaboratory.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("laboratory", "laboratory", JoinType.INNER_JOIN);
        criteria.createAlias("researchType", "researchType", JoinType.INNER_JOIN);
        criteria.createAlias("biomaterialType", "biomaterialType", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("testTubeType", "testTubeType", JoinType.LEFT_OUTER_JOIN);

        criteria.add(Restrictions.eq("researchType.code", research.getResearchType().getCode()));
        criteria.add(Restrictions.or(
                Restrictions.eq("biomaterialType.id", biomaterial.getBiomaterialType().getId()),
                Restrictions.isNull("biomaterialType")
        ));
        criteria.add(Restrictions.or(
                Restrictions.eq("testTubeType.id", biomaterial.getTestTybeType().getId()),
                Restrictions.isNull("testTubeType")
        ));
        final Session session = sessionFactory.getCurrentSession();
        final List<MapResearchTypeToLaboratory> resultList = criteria.getExecutableCriteria(session).list();
        return !resultList.isEmpty() ? getBestMatch(resultList, biomaterial) : Collections.emptyMap();
    }

    private Map<RbLaboratory, MapResearchTypeToLaboratory> getBestMatch(final List<MapResearchTypeToLaboratory> source, final Biomaterial biomaterial) {
        final Set<RbLaboratory> laboratories = getDistinctLaboratories(source);
        final Map<RbLaboratory, MapResearchTypeToLaboratory> result = new HashMap<>(laboratories.size());
        for (RbLaboratory laboratory : laboratories) {
            result.put(laboratory, getBestMatch(laboratory, source, biomaterial));
        }
        return result;
    }

    private Set<RbLaboratory> getDistinctLaboratories(final List<MapResearchTypeToLaboratory> source) {
        final Set<RbLaboratory> result = new HashSet<>(Math.min(5, source.size()));
        for (MapResearchTypeToLaboratory entry : source) {
            result.add(entry.getLaboratory());
        }
        return result;
    }

    private MapResearchTypeToLaboratory getBestMatch(final RbLaboratory laboratory, final List<MapResearchTypeToLaboratory> source, final Biomaterial biomaterial) {
        final Set<MapResearchTypeToLaboratory> filtered = source.stream().filter(x -> laboratory.equals(x.getLaboratory())).collect(Collectors.toSet());
        Optional<MapResearchTypeToLaboratory> result;
        final RbBiomaterialType biomaterialType = biomaterial.getBiomaterialType();
        final RbTestTubeType testTybeType = biomaterial.getTestTybeType();
        result = filtered.stream().filter(x -> biomaterialType.equals(x.getBiomaterialType()) && testTybeType.equals(x.getTestTubeType())).findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by BT, TTT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        result = filtered.stream().filter(x -> biomaterialType.equals(x.getBiomaterialType())).findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by BT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        result = filtered.stream().filter(x -> testTybeType.equals(x.getTestTubeType())).findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by TTT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        final Iterator<MapResearchTypeToLaboratory> iterator = filtered.iterator();
        final MapResearchTypeToLaboratory typed = iterator.hasNext() ?  iterator.next() : null;
        log.debug("Lab[{}]: BestMatch by WILDCARDS: {}", laboratory.getCode(), typed);
        return typed;
    }


}
