package ru.bars_open.medvtr.business.interfaces;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.db.entities.ContractContragent;
import ru.bars_open.medvtr.db.entities.Invoice;
import ru.bars_open.medvtr.db.entities.RbPayType;

/**
 * Author: Upatov Egor <br>
 * Date: 05.12.2016, 20:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface InvoiceBusinessLogic {

    boolean pay(
            final Invoice invoice,
            final ContractContragent contractContragent,
            final Double sum,
            final RbPayType payType,
            final LocalDateTime transactionDateTime
    );


    boolean refund(
            final Invoice invoice,
            final ContractContragent contragent,
            final Double sum,
            final RbPayType payType,
            final LocalDateTime transactionDateTime
    );

    default boolean pay(
            final Invoice invoice,
            final boolean isRefund,
            final ContractContragent contragent,
            final Double sum,
            final RbPayType payType,
            final LocalDateTime transactionDateTime
    ) {
        if (isRefund) {
            return refund(invoice, contragent, sum, payType, transactionDateTime);
        } else {
            return pay(invoice, contragent, sum, payType, transactionDateTime);
        }
    }
}
