package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

public interface ReferenceBookDao extends AbstractDao<ReferenceBookEntity> {
    <E> E getByCode(Class<E> clazz, final String code);
}
