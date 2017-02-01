package ru.bars_open.medvtr.soap.ws.finance;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.bars_open.medvtr.business.interfaces.InvoiceBusinessLogic;
import ru.bars_open.medvtr.db.dao.interfaces.ContractContragentDao;
import ru.bars_open.medvtr.db.dao.interfaces.InvoiceDao;
import ru.bars_open.medvtr.db.dao.interfaces.mapped.ReferenceBookDao;
import ru.bars_open.medvtr.db.entities.ContractContragent;
import ru.bars_open.medvtr.db.entities.Invoice;
import ru.bars_open.medvtr.db.entities.RbPayType;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentRequest;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentResponse;
import ru.bars_open.medvtr.soap.ws.finance.generated.PayType;

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
    private InvoiceBusinessLogic invoiceBusinessLogic;

    @Autowired
    private ReferenceBookDao referenceBookDao;

    @Autowired
    private ContractContragentDao contractContragentDao;


    @PayloadRoot(namespace = "ru.bars_open.medvtr.soap.ws.finance", localPart = "applyPaymentRequest")
    @ResponsePayload
    @Transactional
    public ApplyPaymentResponse applyPayment(@RequestPayload ApplyPaymentRequest request) throws Exception {
        final int logTag = counter.incrementAndGet();
        log.info("#{} Call PaymentResultService.applyPayment(sum={}, trxDateTime='{}', invoiceId='{}', invoiceNumber='{}', payType={}, contragentId={})",
                 logTag,
                 request.getSum(),
                 request.getTrxDatetime(),
                 request.getInvoiceId(),
                 request.getInvoiceNumber(),
                 request.getPayType(),
                 request.getContragentId()
        );
        final LocalDateTime transactionDateTime = new LocalDateTime(request.getTrxDatetime().toGregorianCalendar());
        final ApplyPaymentResponse response = new ApplyPaymentResponse();
        // Ищем счет по номеру
        final Invoice invoice = invoiceDao.get(request.getInvoiceId());
        if (invoice == null) {
            log.error("#{} Error: no Invoice[{}] found", logTag, request.getInvoiceNumber());
            throw new Exception("Error: no Invoice["+request.getInvoiceId()+"] found");
        } else if(!StringUtils.equals(request.getInvoiceNumber(), invoice.getNumber())){
            log.error("#{} Error: Invoice[{}] found, but has another number['{}']", logTag, invoice.getId(), request.getInvoiceNumber());
            throw new Exception("Error: Invoice["+request.getInvoiceId()+"] found, but with different number="+invoice.getNumber());
        }
        log.info("#{} found {}", logTag, invoice);
        // Ищем плательщика
        final ContractContragent contragent = contractContragentDao.getByClient(request.getContragentId());
        if (contragent == null) {
            log.error("#{} Error: no ContractContragent[with Client[{}]] found", logTag, request.getContragentId());
            throw new Exception("Error: no ContractContragent[with Client["+request.getContragentId()+"]] found");
        }
        log.info("#{} found {}", logTag, contragent);
        final RbPayType payType = referenceBookDao.getByCode(RbPayType.class,
                                                             PayType.CASH.equals(request.getPayType()) ? RbPayType.CODE_CASH : RbPayType.CODE_CASHLESS
        );
        final boolean payed = invoiceBusinessLogic.pay(invoice, request.isRefund(), contragent, request.getSum(), payType, transactionDateTime);
        if(!payed){
            log.error("#{} Error: not payed", logTag, request.getContragentId());
            final Double fullSumm = invoiceBusinessLogic.getFullSumm(invoice, request.isRefund());
            throw new Exception("Error: reject partial pay. Full sum is "+fullSumm);
        }
        log.info("#{} End PaymentResultService.applyPayment with result = {}", logTag, response.getResult());
        return response;
    }

}
