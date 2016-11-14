package ru.bars_open.medvtr.db.dao.impl;


import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.db.dao.AbstractDao;
import ru.bars_open.medvtr.db.dao.interfaces.FinanceTransactionDao;
import ru.bars_open.medvtr.db.entities.FinanceTransaction;

@Repository("financeTransactionDao")
public class FinanceTransactionDaoImpl extends AbstractDao<FinanceTransaction> implements FinanceTransactionDao{

    @Override
    public Class<FinanceTransaction> getEntityClass() {
        return FinanceTransaction.class;
    }
}
