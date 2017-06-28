package ru.bars_open.medvtr.amqp.consumer.finance;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.*;
import ru.bars_open.medvtr.amqp.consumer.finance.util.SHandler;
import ru.bars_open.medvtr.mq.entities.base.InvoiceItem;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/**
 * Author: Upatov Egor <br>
 * Date: 05.12.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class WSFactory {

    private static final Logger log = LoggerFactory.getLogger(WSFactory.class);
    private final static ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final ExchangeMISPortType webservice;
    private final URL serviceURL;
    private final QName serviceName;


    @Autowired
    public WSFactory(ConfigurationHolder cfg) throws MalformedURLException {
        this.serviceURL = new URL(cfg.getString(ConfigurationKeys.WEBSERVICE_URL));
        this.serviceName = new QName(cfg.getString(ConfigurationKeys.WEBSERVICE_NAMESPACE), cfg.getString(ConfigurationKeys.WEBSERVICE_NAME));
        final ExchangeMIS service = new ExchangeMIS(getClass().getClassLoader().getResource("Exchange_MIS.wsdl"), serviceName);
        service.setHandlerResolver(portInfo -> new ArrayList<>(Collections.singletonList(new SHandler())));
        // Timeout in millis
        int requestTimeout = 10000;
        int connectTimeout = 1000;

        final ExchangeMISPortType port = service.getExchangeMISSoap();
        final Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL.toExternalForm());
        //https://java.net/jira/browse/JAX_WS-1166
        // Weblogic JAX-WS properties
        requestContext.put("com.sun.xml.ws.connect.timeout", connectTimeout);
        requestContext.put("com.sun.xml.ws.request.timeout", requestTimeout);
        // JDK JAX-WS properties
        requestContext.put("com.sun.xml.internal.ws.connect.timeout", connectTimeout);
        requestContext.put("com.sun.xml.internal.ws.request.timeout", requestTimeout);
        // JBOSS CXF JAX-WS properties, warning these might change in the future (CXF-5261)
        requestContext.put("javax.xml.ws.client.connectionTimeout", connectTimeout);
        requestContext.put("javax.xml.ws.client.receiveTimeout", requestTimeout);

        log.info("WS initialized: {} - {}", serviceURL, serviceName);
        this.webservice = port;
    }


    public ExchangeMISPortType getWebService() {
        return webservice;
    }

    public URL getServiceURL() {
        return serviceURL;
    }

    public QName getServiceName() {
        return serviceName;
    }

    public XMLGregorianCalendar wrapDate(final LocalDateTime date) {
        if (date == null) {
            return null;
        }
        final GregorianCalendar calendar = GregorianCalendar.from(date.atZone(ZoneId.systemDefault()));
        return new XMLGregorianCalendarImpl(calendar);
    }

    public PersonName createPersonName(final String family, final String given, final String partName, final LocalDateTime birthDate) {
        final PersonName result = OBJECT_FACTORY.createPersonName();
        result.setFamily(family);
        result.setGiven(given);
        result.setPartName(partName);
        result.setBirthDay(wrapDate(birthDate));
        return result;
    }

    public PersonName createPersonName(final Person person) {
        return createPersonName(person.getLastName(), person.getFirstName(), person.getPatrName(), person.getBirthDate());
    }

    public Services getServices(List<InvoiceItem> items) {
        final Services result = OBJECT_FACTORY.createServices();
        for (InvoiceItem item : items) {
            if(!item.getService().getIsAccumulativePrice() && !item.getService().getService().getIsComplex()) {
                result.getService().add(createService(item));
            }
        }
        return result;
    }

    private Service createService(InvoiceItem item) {
        final Service result = OBJECT_FACTORY.createService();
        result.setAmount(item.getAmount());
        result.setIdService(item.getService().getId());
        result.setPrice(item.getPrice());
        result.setFullName(item.getService().getName());
        result.setNNumber(item.getService().getCode());
        return result;
    }
}
