package ru.bars_open.medvtr.soap.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bars_open.medvtr.db.dao.InvoiceDao;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 16:08 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Service
@WebService(
        targetNamespace = "ru.bars_open.medvtr.soap.ws.finance",
        name = "payment-results")
public class PaymentResultService {
    private static final Logger log = LoggerFactory.getLogger(PaymentResultService.class);


    @Autowired
    private InvoiceDao invoiceDao;

    @PostConstruct
    public void init(){
         log.info("INIT! , dao = {}", invoiceDao);
    }

    @WebMethod
    public int applyPayment(
            @WebParam(name = "sum",  targetNamespace = "ru.bars_open.medvtr.soap.ws.finance") final double sum,
            @WebParam(name = "trxDatetime",  targetNamespace = "ru.bars_open.medvtr.soap.ws.finance") final Date trxDateTime,
            @WebParam(name = "invoice_number",  targetNamespace = "ru.bars_open.medvtr.soap.ws.finance") final String  invoiceNumber,
            @WebParam(name = "pay_type",  targetNamespace = "ru.bars_open.medvtr.soap.ws.finance") final PayType payType,
            @WebParam(name = "contragent_id",  targetNamespace = "ru.bars_open.medvtr.soap.ws.finance") int contragentId
            ){
        return invoiceDao.getByNumber(invoiceNumber).getId();
    }

}
