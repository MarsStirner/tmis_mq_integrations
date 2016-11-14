package ru.bars_open.medvtr.db.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.AbstractReferenceBookDao;
import ru.bars_open.medvtr.db.dao.interfaces.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

@Repository("referenceBookDao")
public class ReferenceBookDaoImpl extends AbstractReferenceBookDao implements ReferenceBookDao {
    @Override
    public Class<ReferenceBookEntity> getEntityClass() {
        return ReferenceBookEntity.class;
    }
}
