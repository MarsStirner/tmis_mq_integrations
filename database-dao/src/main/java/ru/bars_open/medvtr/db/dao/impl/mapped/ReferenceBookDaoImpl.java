package ru.bars_open.medvtr.db.dao.impl.mapped;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

@Repository("referenceBookDao")
public class ReferenceBookDaoImpl extends AbstractReferenceBookDaoImpl implements ReferenceBookDao {
    @Override
    public Class<ReferenceBookEntity> getEntityClass() {
        return ReferenceBookEntity.class;
    }
}
