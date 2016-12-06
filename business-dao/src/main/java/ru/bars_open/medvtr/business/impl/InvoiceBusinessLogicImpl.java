package ru.bars_open.medvtr.business.impl;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.business.interfaces.InvoiceBusinessLogic;
import ru.bars_open.medvtr.db.dao.interfaces.FinanceTransactionDao;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceDao;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceItemDao;
import ru.bars_open.medvtr.db.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.*;
import ru.bars_open.medvtr.db.entities.util.EntityFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Author: Upatov Egor <br>
 * Date: 05.12.2016, 20:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Repository("invoiceBusinessLogic")
@Transactional
public class InvoiceBusinessLogicImpl implements InvoiceBusinessLogic {


    @Autowired
    private ReferenceBookDao refbook;

    @Autowired
    private FinanceTransactionDao financeTransactionDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private InvoiceItemDao invoiceItemDao;

    /**
     * создавать две записи в таблице FinanceTransaction
     * Записи должны быть идентичны по всем полям кроме trxType_id и financeOperationType_id, payType_id(о нем ниже)
     * Для первой строки эти параметры должны быть 1, 1 ., для второй - 2, 3
     * (первая строка - поступление денег на баланс плательщика, вторая строка - списание денег с баланса для оплаты счета)
     * Для первой строки заполнять поле payType_id по данным пришедшим из 1С, для второй ставить NUL
     *
     * @param invoice             счет на оплату
     * @param contractContragent  контрагент - плательщик
     * @param sum                 сумма поступивших стредств
     * @param payType             тип оплаты
     * @param transactionDateTime дата+время проведения транзакции
     */
    @Override
    public boolean pay(
            final Invoice invoice,
            final ContractContragent contractContragent,
            final Double sum,
            final RbPayType payType,
            final LocalDateTime transactionDateTime
    ) {
        if(!isFullSumm(invoice, sum, false)){
            return false;
        }
        final FinanceTransaction payerIncomeMoneyTransaction = EntityFactory.createFinanceTransaction(
                null,
                transactionDateTime,
                //Движение средств на счёте плательщика
                refbook.getByCode(RbFinanceTransactionType.class, RbFinanceTransactionType.CODE_PAYER_BALANCE),
                //Поступление денежных средств
                refbook.getByCode(RbFinanceOperationType.class, RbFinanceOperationType.CODE_PAYER_BALANCE_IN),
                contractContragent,
                invoice,
                payType,
                sum
        );
        final FinanceTransaction payTransaction = EntityFactory.createFinanceTransaction(
                null,
                transactionDateTime,
                //Движение средств по выставленным счетам
                refbook.getByCode(RbFinanceTransactionType.class, RbFinanceTransactionType.CODE_INVOICE),
                //Оплата по счёту
                refbook.getByCode(RbFinanceOperationType.class, RbFinanceOperationType.CODE_INVOICE_PAY),
                contractContragent,
                invoice,
                null,
                sum
        );

        financeTransactionDao.save(payerIncomeMoneyTransaction);
        financeTransactionDao.save(payTransaction);

        invoice.setSettleDate(transactionDateTime.toLocalDate());
        invoiceDao.update(invoice);
        return true;
    }


    /**
     * Создавать две записи в таблице FinanceTransaction, для них указывать trxType_id и financeOperationType_id как:
     * 2, 4 (Движение средств по выставленным счетам, отмена оплаты по счету)
     * 1, 2 (движение средств на счете, Возврат денежных средств)
     * Для первой строки в поле payType_id ставить NULL, для второй - подставлять данные, пришедшие из 1С.
     *
     * @param invoice             счет на оплату
     * @param contractContragent  контрагент - плательщик
     * @param sum                 сумма поступивших стредств
     * @param payType             тип оплаты
     * @param transactionDateTime дата+время проведения транзакции
     * */
    @Override
    public boolean refund(
            final Invoice invoice,
            final ContractContragent contractContragent,
            final Double sum,
            final RbPayType payType,
            final LocalDateTime transactionDateTime
    ) {
        if(!isFullSumm(invoice, sum, true)){
            return false;
        }
        final FinanceTransaction cancelPayTransaction = EntityFactory.createFinanceTransaction(
                null,
                transactionDateTime,
                //Движение средств по выставленным счетам
                refbook.getByCode(RbFinanceTransactionType.class, RbFinanceTransactionType.CODE_INVOICE),
                //Отмена оплаты по счёту
                refbook.getByCode(RbFinanceOperationType.class, RbFinanceOperationType.CODE_INVOICE_CANCEL),
                contractContragent,
                invoice,
                null,
                sum
        );
        final FinanceTransaction cashBackTransaction = EntityFactory.createFinanceTransaction(
                null,
                transactionDateTime,
                //Движение средств на счёте плательщика
                refbook.getByCode(RbFinanceTransactionType.class, RbFinanceTransactionType.CODE_PAYER_BALANCE),
                //Возврат денежных средств
                refbook.getByCode(RbFinanceOperationType.class, RbFinanceOperationType.CODE_PAYER_BALANCE_OUT),
                contractContragent,
                invoice,
                payType,
                sum
        );

        financeTransactionDao.save(cancelPayTransaction);
        financeTransactionDao.save(cashBackTransaction);

        invoice.setSettleDate(transactionDateTime.toLocalDate());
        invoiceDao.update(invoice);
        return true;
    }

    public boolean isFullSumm(final Invoice invoice, final Double sum, final boolean isRefund) {
        return Objects.equals(sum, getFullSumm(invoice, isRefund));
    }

    private Double getFullSumm(final Invoice invoice, final boolean isRefund) {
        //Сумма учитывается только если возврат или если услуга - не явялется компонентом другой услуги

        // return invoiceItemDao.getByInvoice(invoice, isRefund)
        //                .stream().filter((x)-> isRefund || x.getParent() == null).mapToDouble(InvoiceItem::getSum).sum();
        final List<InvoiceItem> items = invoiceItemDao.getByInvoice(invoice, isRefund);
        Double result = 0.0;
        for (InvoiceItem item : items) {
            if (isRefund || item.getParent() == null) {
                result += item.getSum();
            }
        }
        return result;
    }
}
