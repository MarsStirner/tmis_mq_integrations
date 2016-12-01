package ru.bars_open.medvtr.amqp.consumer.finance.util;

import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMISPortType;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.PersonName;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ServiceCompleteInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

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
    /**
     * @param idTreatment
     * @param dateTreatment
     * @param numTreatment
     * @param numInvoice
     * @param sumInvoice
     * @param codePatient
     * @param patientName
     * @param codePayer
     * @param payerName
     * @param remove
     * @return returns java.math.BigInteger
     */
    @Override
    public int putTreatment(
            @WebParam(name = "idTreatment", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final int idTreatment,
            @WebParam(name = "dateTreatment", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final XMLGregorianCalendar dateTreatment,
            @WebParam(name = "numTreatment", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final String numTreatment,
            @WebParam(name = "numInvoice", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final String numInvoice,
            @WebParam(name = "sumInvoice", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final int sumInvoice,
            @WebParam(name = "codePatient", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final String codePatient,
            @WebParam(name = "patientName", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final PersonName patientName,
            @WebParam(name = "codePayer", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final String codePayer,
            @WebParam(name = "payerName", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final PersonName payerName,
            @WebParam(name = "remove", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final int remove
    ) {
        return idTreatment;
    }

    @Override
    public String putService(
            @WebParam(name = "idTreatment", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final int idTreatment,
            @WebParam(name = "paidName", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final PersonName paidName,
            @WebParam(name = "listServiceComplete", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope") final ServiceCompleteInfo listServiceComplete
    ) {
        return "It's a Mock";
    }
}
