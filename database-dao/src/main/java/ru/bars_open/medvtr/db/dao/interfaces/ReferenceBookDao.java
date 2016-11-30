package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import java.util.Collection;
import java.util.Map;

public interface ReferenceBookDao extends AbstractDao<ReferenceBookEntity> {
    <E extends ReferenceBookEntity> E getByCode(Class<E> clazz, final String code);
    <E extends ReferenceBookEntity> Map<String, E> getMapByCodes(Class<E> clazz, final Collection<String> codes);
}
