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
import ru.bars_open.medvtr.amqp.biomaterial.entities.MapToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapBiomaterialTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTubeTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings.MapTestTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.LaboratoryMappingPK;

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

    private static final Logger log = LoggerFactory.getLogger(MappingDaoImpl.class);

    @Autowired
    @Qualifier(PersistenceConfig.SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with SessionFactory[@{}]", Integer.toHexString(sessionFactory.hashCode()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<MapToLaboratory> findLaboratoryMapping(final String researchType, final String biomaterialType, final String testTubeType) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(MapToLaboratory.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("laboratory", "laboratory", JoinType.INNER_JOIN);

        criteria.add(Restrictions.eq("researchType", researchType));
        criteria.add(Restrictions.or(Restrictions.eq("biomaterialType", biomaterialType), Restrictions.isNull("biomaterialType")));
        criteria.add(Restrictions.or(Restrictions.eq("testTubeType", testTubeType), Restrictions.isNull("testTubeType")));
        final Session session = sessionFactory.getCurrentSession();
        final List<MapToLaboratory> resultList = criteria.getExecutableCriteria(session).list();
        return !resultList.isEmpty() ? getBestMatch(resultList, biomaterialType, testTubeType) : Collections.emptySet();
    }

    @Override
    public MapBiomaterialTypeToLaboratory getBiomaterialType(final RbLaboratory laboratory, final String code) {
        return sessionFactory.getCurrentSession().find(MapBiomaterialTypeToLaboratory.class, new LaboratoryMappingPK(laboratory, code));
    }

    @Override
    public MapTestTubeTypeToLaboratory getTestTubeType(final RbLaboratory laboratory, final String code) {
        return sessionFactory.getCurrentSession().find(MapTestTubeTypeToLaboratory.class, new LaboratoryMappingPK(laboratory, code));
    }

    @Override
    public MapTestTypeToLaboratory getTestType(final RbLaboratory laboratory, final String code) {
        return sessionFactory.getCurrentSession().find(MapTestTypeToLaboratory.class, new LaboratoryMappingPK(laboratory, code));
    }

    @Override
    public MapResearchTypeToLaboratory getResearchType(final RbLaboratory laboratory, final String code) {
        return sessionFactory.getCurrentSession().find(MapResearchTypeToLaboratory.class, new LaboratoryMappingPK(laboratory, code));
    }

    private Set<MapToLaboratory> getBestMatch(final List<MapToLaboratory> source, final String biomaterialType, final String testTubeType) {
        final Set<RbLaboratory> laboratories = getDistinctLaboratories(source);
        final Set<MapToLaboratory> result = new HashSet<>(laboratories.size());
        for (RbLaboratory laboratory : laboratories) {
            result.add(getBestMatch(laboratory, source, biomaterialType, testTubeType));
        }
        return result;
    }

    private Set<RbLaboratory> getDistinctLaboratories(final List<MapToLaboratory> source) {
        final Set<RbLaboratory> result = new HashSet<>(Math.min(5, source.size()));
        for (MapToLaboratory entry : source) {
            result.add(entry.getLaboratory());
        }
        return result;
    }

    private MapToLaboratory getBestMatch(
            final RbLaboratory laboratory,
            final List<MapToLaboratory> source,
            final String biomaterialType,
            final String testTubeType
    ) {
        final Set<MapToLaboratory> filtered = source.stream().filter(x -> laboratory.equals(x.getLaboratory())).collect(Collectors.toSet());
        Optional<MapToLaboratory> result;
        result = filtered.stream().filter(x -> biomaterialType.equals(x.getBiomaterialType()) && testTubeType.equals(x.getTestTubeType()))
                .findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by BT, TTT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        result = filtered.stream().filter(x -> biomaterialType.equals(x.getBiomaterialType())).findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by BT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        result = filtered.stream().filter(x -> testTubeType.equals(x.getTestTubeType())).findFirst();
        if (result.isPresent()) {
            log.debug("Lab[{}]: Best match by TTT: {}", laboratory.getCode(), result.get());
            return result.get();
        }
        final Iterator<MapToLaboratory> iterator = filtered.iterator();
        final MapToLaboratory typed = iterator.hasNext() ? iterator.next() : null;
        log.debug("Lab[{}]: BestMatch by WILDCARDS: {}", laboratory.getCode(), typed);
        return typed;
    }


}
