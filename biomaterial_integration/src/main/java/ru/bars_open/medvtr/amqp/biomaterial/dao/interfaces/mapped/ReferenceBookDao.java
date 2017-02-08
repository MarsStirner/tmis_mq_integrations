package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.mapped;


import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import java.util.Map;
import java.util.Set;

public interface ReferenceBookDao<T extends ReferenceBookEntity> extends AbstractDao<T> {
    T getByCode(final String code);
    Map<String, T> getMap(final Set<String> codes);
}
