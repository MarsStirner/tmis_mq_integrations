
package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "PharmacyHospitalization", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", wsdlLocation = "/1C_PharmacyHospitalization.wsdl")
public class PharmacyHospitalization
    extends Service
{

    private final static URL PHARMACYHOSPITALIZATION_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.PharmacyHospitalization.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.PharmacyHospitalization.class.getResource(".");
            url = new URL(baseUrl, "/1C_PharmacyHospitalization.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '/1C_PharmacyHospitalization.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        PHARMACYHOSPITALIZATION_WSDL_LOCATION = url;
    }

    public PharmacyHospitalization(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PharmacyHospitalization() {
        super(PHARMACYHOSPITALIZATION_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/soap/envelope", "PharmacyHospitalization"));
    }

    /**
     * 
     * @return
     *     returns PharmacyHospitalizationPortType
     */
    @WebEndpoint(name = "PharmacyHospitalizationSoap")
    public PharmacyHospitalizationPortType getPharmacyHospitalizationSoap() {
        return super.getPort(new QName("http://schemas.xmlsoap.org/soap/envelope", "PharmacyHospitalizationSoap"), PharmacyHospitalizationPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PharmacyHospitalizationPortType
     */
    @WebEndpoint(name = "PharmacyHospitalizationSoap")
    public PharmacyHospitalizationPortType getPharmacyHospitalizationSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.xmlsoap.org/soap/envelope", "PharmacyHospitalizationSoap"), PharmacyHospitalizationPortType.class, features);
    }

}
