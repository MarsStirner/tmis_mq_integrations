package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.PersistenceConfig;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.StatusDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.LaboratoryStatus;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.ResearchToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ResearchToLaboratoryPK;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 16:46 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("statusDao")
@Transactional
public class StatusDaoImpl implements StatusDao {

    private static final Logger log = LoggerFactory.getLogger(StatusDaoImpl.class);

    @Autowired
    @Qualifier(PersistenceConfig.SESSION_FACTORY)
    protected SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        log.info("Init for work with SessionFactory[@{}]", Integer.toHexString(sessionFactory.hashCode()));
    }


    @Override
    public ResearchToLaboratory setLaboratoryStatus(
            final Research research, final RbLaboratory laboratory, final LaboratoryStatus status
    ) {
        final ResearchToLaboratory previous = get(research, laboratory);
        if (previous != null) {
            if (status.equals(previous.getStatus())) {
                log.debug("Research[{}] already has relation with RbLaboratory[{}] in status '{}' ", research.getId(), laboratory.getCode(), status);
                return previous;
            } else {
                log.warn(
                        "Research[{}] already has relation with RbLaboratory[{}] in different status '{}'. Override!",
                        research.getId(),
                        laboratory.getCode(),
                        previous.getStatus()
                );
                previous.setStatus(status);
                update(previous);
                return previous;
            }
        } else {
            log.debug("No previous mapping ResearchToLaboratory found, create a new one");
            final ResearchToLaboratory result = new ResearchToLaboratory();
            result.setPk(new ResearchToLaboratoryPK(research, laboratory));
            result.setStatus(status);
            return save(result);
        }
    }

    @Override
    public boolean isSent(final Research research) {
        return getByResearch(research).stream().allMatch(x -> LaboratoryStatus.SENT.equals(x.getStatus()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ResearchToLaboratory> getByResearch(final Research research) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(ResearchToLaboratory.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("pk.research.id", research.getId()));
        final Session session = sessionFactory.getCurrentSession();
        return criteria.getExecutableCriteria(session).list();
    }

    private ResearchToLaboratory get(final Research research, final RbLaboratory laboratory) {
        return sessionFactory.getCurrentSession().find(ResearchToLaboratory.class, new ResearchToLaboratoryPK(research, laboratory));
    }

    public ResearchToLaboratory save(ResearchToLaboratory entity) {
        final Serializable assignedID = sessionFactory.getCurrentSession().save(entity);
        log.trace("Save entity: {}", entity);
        return entity;
    }

    public void update(ResearchToLaboratory entity) {
        sessionFactory.getCurrentSession().update(entity);
        log.trace("Update entity: {}", entity);
    }

}
