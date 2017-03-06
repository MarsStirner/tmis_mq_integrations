package ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.entities.Action;
import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.actionProperty.APValue;
import ru.bars_open.medvtr.db.entities.mapped.compositeKeys.IndexedIdentifiedPK;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 16:40 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("actionPropertyDao")
public class ActionPropertyDaoImpl implements ActionPropertyDao {

    protected static final Logger log = LoggerFactory.getLogger(ActionPropertyDaoImpl.class);

    @PersistenceContext(unitName = PersistenceConfig.PERSISTENCE_UNIT_NAME_HOSPITAL)
    private EntityManager em;

    @PostConstruct
    public void init() {
        log.info("<init> for work with [{}] and EM[@{}]", ActionProperty.class.getSimpleName(), Integer.toHexString(em.hashCode()));
    }

    @Override
    public ActionProperty get(Integer id) {
        return em.find(ActionProperty.class, id);
    }

    @Override
    public Integer save(ActionProperty entity) {
        em.persist(entity);
        log.debug("Save entity: {}", entity.toString());
        return entity.getId();
    }

    @Override
    public void update(ActionProperty entity) {
        em.merge(entity);
        log.debug("Update entity: {}", entity);
    }

    @Override
    public List<ActionProperty> getAssignedByAction(final Action action) {
        return em.createNamedQuery("ActionProperty.getAssignedByAction", ActionProperty.class).setParameter("actionId", action.getId())
                .getResultList();
    }

    @Override
    public ActionProperty getByActionAndCode(final Action action, final String actionPropertyTypeCode) {
        return em.createQuery("SELECT a FROM ActionProperty a WHERE a.action.id = :actionId AND a.type.code = :aptCode AND a.deleted = 0",
                              ActionProperty.class
        ).setParameter("actionId", action.getId()).setParameter("aptCode", actionPropertyTypeCode).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public APValue setValue(final ActionProperty ap, int index, final Object value) {
        APValue result = getValue(ap, index);
        if (result == null) {
            return createValue(ap, index, value);
        } else {
            result.setValue(value);
            return em.merge(result);
        }
    }

    public APValue createValue(final ActionProperty ap, final int index, final Object value) {
        try {
            final APValue result = ap.getType().getTypeName().getValueClass().newInstance();
            result.setId(ap.getId(), index);
            result.setValue(value);
            em.persist(result);
            return result;
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("Cannot create APValue for {}", ap, ex);
            return null;
        }
    }


    @Override
    public APValue getValue(final ActionProperty ap, int index) {
        return em.find(ap.getType().getTypeName().getValueClass(), new IndexedIdentifiedPK(ap.getId(), index));
    }
}
