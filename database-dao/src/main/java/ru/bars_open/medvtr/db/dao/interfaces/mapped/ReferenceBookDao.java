package ru.bars_open.medvtr.db.dao.interfaces.mapped;

import ru.bars_open.medvtr.db.entities.mapped.ReferenceBookEntity;

import java.util.Map;
import java.util.Set;

public interface ReferenceBookDao extends AbstractDao<ReferenceBookEntity> {
    <E extends ReferenceBookEntity> E getByCode(Class<E> clazz, final String code);

    <E extends ReferenceBookEntity> Map<String, E> getMap(Class<E> clazz, final Set<String> codes);
}
