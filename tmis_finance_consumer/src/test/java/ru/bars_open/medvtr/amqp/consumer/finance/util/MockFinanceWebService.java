package ru.bars_open.medvtr.amqp.consumer.finance.util;

import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMISPortType;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.PersonName;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.Services;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

/**
 * Author: Upatov Egor <br>
 * Date: 31.10.2016, 14:42 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@WebService(
        endpointInterface = "ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMISPortType",
        serviceName = "Exchange_MIS",
        targetNamespace = "http://schemas.xmlsoap.org/soap/envelope",
        portName = "Exchange_MISSoap"
)
public class MockFinanceWebService implements ExchangeMISPortType {
    @Override
    public BigInteger putTreatment(int idTreatment, XMLGregorianCalendar dateTreatment, String numTreatment, String numInvoice, double sumInvoice, String codePatient, PersonName patientName, String codePayer, PersonName payerName, int remove, int invoiceId, int employeeID, String email, String phoneNumber, Services services) {
        return BigInteger.valueOf(idTreatment);
    }

    @Override
    public String putReturn(String parentNumInvoice, String numInvoice, double sumReturn, int remove, int invoiceId, int parentInvoiceId, int employeeID, String email, String phoneNumber, Services services) {
        return numInvoice;
    }
}
