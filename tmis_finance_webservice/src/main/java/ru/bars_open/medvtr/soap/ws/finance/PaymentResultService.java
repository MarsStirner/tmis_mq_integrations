package ru.bars_open.medvtr.soap.ws.finance;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.bars_open.medvtr.db.dao.interfaces.ContractContragentDao;
import ru.bars_open.medvtr.db.dao.interfaces.FinanceTransactionDao;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceDao;
import ru.bars_open.medvtr.db.dao.interfaces.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.*;
import ru.bars_open.medvtr.db.entities.util.EntityFactory;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentRequest;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentResponse;
import ru.bars_open.medvtr.soap.ws.finance.generated.PayType;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 16:08 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Endpoint
public class PaymentResultService {
    private static final Logger log = LoggerFactory.getLogger(PaymentResultService.class);
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private ContractContragentDao contractContragentDao;

    @Autowired
    private ReferenceBookDao referenceBookDao;

    @Autowired
    private FinanceTransactionDao financeTransactionDao;


    @PostConstruct
    public void init() {
        log.info("Initialized [@{}] with InvoiceDao[@{}], ContractContragentDao[@{}]",
                Integer.toHexString(this.hashCode()),
                invoiceDao == null ? "NULL" : Integer.toHexString(invoiceDao.hashCode()),
                contractContragentDao == null ? "NULL" : Integer.toHexString(contractContragentDao.hashCode())
        );
    }

    @PayloadRoot(namespace = "ru.bars_open.medvtr.soap.ws.finance", localPart = "applyPaymentRequest")
    @ResponsePayload
    @Transactional
    public ApplyPaymentResponse applyPayment(@RequestPayload ApplyPaymentRequest request) {
        final int logTag = counter.incrementAndGet();
        log.info("#{} Call PaymentResultService.applyPayment(" +
                        "sum={}, trxDateTime='{}', invoiceNumber='{}', payType={}, contragentId={})", logTag,
                request.getSum(), request.getTrxDatetime(), request.getInvoiceNumber(), request.getPayType(), request.getContragentId()
        );
        final ApplyPaymentResponse response = new ApplyPaymentResponse();

        final Invoice invoice = invoiceDao.getByNumber(request.getInvoiceNumber());
        if (invoice == null) {
            log.error("#{} Error: no Invoice[number='{}'] found", logTag, request.getInvoiceNumber());
            response.setResult(-1);
            log.info("#{} End PaymentResultService.applyPayment with result = {}", logTag, response.getResult());
            return response;
        }
        log.info("#{} found {}", logTag, invoice);

        final ContractContragent contragent = contractContragentDao.getByClient(request.getContragentId());
        if (contragent == null) {
            log.error("#{} Error: no ContractContragent[with Client[{}]] found", logTag, request.getContragentId());
            response.setResult(-2);
            log.info("#{} End PaymentResultService.applyPayment with result = {}", logTag, response.getResult());
            return response;
        }
        log.info("#{} found {}", logTag, contragent);

        final RbFinanceTransactionType transactionType = referenceBookDao.getByCode(
                RbFinanceTransactionType.class,
                RbFinanceTransactionType.CODE_PAYER_BALANCE_OUT
        );

        final RbFinanceOperationType financeOperationType = referenceBookDao.getByCode(
                RbFinanceOperationType.class,
                RbFinanceOperationType.CODE_INVOICE_PAY
        );

        final RbPayType payType = referenceBookDao.getByCode(
                RbPayType.class,
                PayType.CASH.equals(request.getPayType()) ? RbPayType.CODE_CASH : RbPayType.CODE_CASHLESS
        );

        FinanceTransaction transaction = EntityFactory.createFinanceTransaction(
                null,
                new LocalDateTime(request.getTrxDatetime().toGregorianCalendar()),
                transactionType,
                financeOperationType,
                contragent,
                invoice,
                payType,
                request.getSum()
        );
        financeTransactionDao.save(transaction);



        log.info("#{} End PaymentResultService.applyPayment with result = {}", logTag, response.getResult());
        return response;
    }

}
