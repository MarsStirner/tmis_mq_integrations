package ru.bars_open.medvtr.db.entities.util;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.db.entities.*;

/**
 * Фабрика конструирующая новые сущности БД
 */
public class EntityFactory {

    public static FinanceTransaction createFinanceTransaction(
            final Person creator,
            final LocalDateTime transactionDateTime,
            final RbFinanceTransactionType transactionType,
            final RbFinanceOperationType financeOperationType,
            final ContractContragent contragent,
            final Invoice invoice,
            final RbPayType payType,
            double sum) {
        final FinanceTransaction result = new FinanceTransaction();
        result.setCreatePerson(creator);
        result.setTrxDateTime(transactionDateTime);
        result.setTransactionType(transactionType);
        result.setFinanceOperationType(financeOperationType);
        result.setContragent(contragent);
        result.setInvoice(invoice);
        result.setPayType(payType);
        result.setSum(sum);
        return result;
    }
}
