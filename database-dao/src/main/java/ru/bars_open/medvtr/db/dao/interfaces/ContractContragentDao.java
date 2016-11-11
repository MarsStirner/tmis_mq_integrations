package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.entities.ContractContragent;

public interface ContractContragentDao extends AbstractDeletableDao<ContractContragent> {
    ContractContragent get(Integer id);
}
