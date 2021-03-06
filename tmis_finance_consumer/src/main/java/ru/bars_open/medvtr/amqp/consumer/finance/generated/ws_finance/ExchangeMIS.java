
package ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance;

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
@WebServiceClient(name = "Exchange_MIS", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope", wsdlLocation = "/Exchange_MIS.wsdl")
public class ExchangeMIS
    extends Service
{

    private final static URL EXCHANGEMIS_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMIS.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMIS.class.getResource(".");
            url = new URL(baseUrl, "/Exchange_MIS.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '/Exchange_MIS.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        EXCHANGEMIS_WSDL_LOCATION = url;
    }

    public ExchangeMIS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ExchangeMIS() {
        super(EXCHANGEMIS_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/soap/envelope", "Exchange_MIS"));
    }

    /**
     * 
     * @return
     *     returns ExchangeMISPortType
     */
    @WebEndpoint(name = "Exchange_MISSoap")
    public ExchangeMISPortType getExchangeMISSoap() {
        return super.getPort(new QName("http://schemas.xmlsoap.org/soap/envelope", "Exchange_MISSoap"), ExchangeMISPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ExchangeMISPortType
     */
    @WebEndpoint(name = "Exchange_MISSoap")
    public ExchangeMISPortType getExchangeMISSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.xmlsoap.org/soap/envelope", "Exchange_MISSoap"), ExchangeMISPortType.class, features);
    }

}
