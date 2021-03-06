
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "PharmacyMedicalPrescriptionPortType", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PharmacyMedicalPrescriptionPortType {


    /**
     * 
     * @param parameters
     * @return
     *     returns ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CreateResponse
     */
    @WebMethod(action = "http://schemas.xmlsoap.org/soap/envelope#PharmacyMedicalPrescription:create")
    @WebResult(name = "createResponse", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", partName = "parameters")
    public CreateResponse create(
        @WebParam(name = "createRequest", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", partName = "parameters")
        CreateRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CloseResponse
     */
    @WebMethod(action = "http://schemas.xmlsoap.org/soap/envelope#PharmacyMedicalPrescription:close")
    @WebResult(name = "closeResponse", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", partName = "parameters")
    public CloseResponse close(
        @WebParam(name = "closeRequest", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", partName = "parameters")
        CloseRequest parameters);

}
