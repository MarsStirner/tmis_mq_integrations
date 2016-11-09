package ru.bars_open.medvtr.soap.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.bars_open.medvtr.db.dao.InvoiceDao;
import ru.bars_open.medvtr.db.entities.Invoice;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentRequest;
import ru.bars_open.medvtr.soap.ws.finance.generated.ApplyPaymentResponse;

import javax.annotation.PostConstruct;
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

    @PayloadRoot(namespace = "ru.bars_open.medvtr.soap.ws.finance", localPart = "applyPaymentRequest")
    @ResponsePayload
    public ApplyPaymentResponse applyPayment(@RequestPayload ApplyPaymentRequest request){
        final int currentRequestNumber = counter.incrementAndGet();
        log.info("#{} Call PaymentResultService.applyPayment({})", currentRequestNumber, request);
        final ApplyPaymentResponse response = new ApplyPaymentResponse();
        final Invoice invoice = invoiceDao.getByNumber(request.getInvoiceNumber());
        log.info("#{} End PaymentResultService.applyPayment with result = {}", currentRequestNumber, response);
        return response;
    }

}
