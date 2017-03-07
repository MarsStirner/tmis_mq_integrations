package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoWithExternalImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.ResearchDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Message;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.Person;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 14:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("researchDao")
@Transactional
public class ResearchDaoImpl extends AbstractDaoWithExternalImpl<Research> implements ResearchDao {


    @Override
    public Research create(
            final Analysis source, final Biomaterial biomaterial, final Message message
    ) {
        log.debug("Research-create new entity");
        final Research result = new Research();
        result.setExternalId(String.valueOf(source.getId()));
        result.setBiomaterial(biomaterial);
        result.setMessage(message);
        result.setCancelled(false);
        result.setUrgent(BooleanUtils.toBooleanDefaultIfNull(source.getIsUrgent(), false));
        if (source.getAssigner() != null) {
            final Person person = source.getAssigner();
            result.setAssigner("["+ person.getId()+ "] "+ person.getLastName() + " "+ person.getFirstName() +" "+ person.getPatrName());
        }
        result.setBegDate(source.getBegDate());
        result.setEndDate(source.getEndDate());
        result.setResearchType(source.getType().getCode());
        result.setNote(null);
        save(result);
        return result;
    }

    @Override
    public Research findOrCreate(final Analysis source, final Biomaterial biomaterial, final Message message) {
        final Research result = getByExternalId(String.valueOf(source.getId()));
        return result != null ? result : create(source, biomaterial, message);
    }

    @Override
    public List<Research> getByBiomaterial(final Biomaterial biomaterial) {
        final DetachedCriteria criteria = getEntityCriteria();
        criteria.add(Restrictions.eq("biomaterial.id", biomaterial.getId()));
        final Session session = sessionFactory.getCurrentSession();
        return criteria.getExecutableCriteria(session).list();
    }


    @Override
    public Class<Research> getEntityClass() {
        return Research.class;
    }
}
