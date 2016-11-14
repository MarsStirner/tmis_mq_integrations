package ru.bars_open.medvtr.db.dao.interfaces;

import ru.bars_open.medvtr.db.entities.Client;
import ru.bars_open.medvtr.db.entities.ContractContragent;

public interface ContractContragentDao extends AbstractDao<ContractContragent>{
    ContractContragent getByClient(final Integer contragentId);
    ContractContragent getByClient(final Client client);
}
