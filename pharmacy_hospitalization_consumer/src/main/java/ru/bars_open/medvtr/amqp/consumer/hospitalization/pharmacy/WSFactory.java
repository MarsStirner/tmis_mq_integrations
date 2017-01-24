package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.*;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.util.SHandler;
import ru.bars_open.medvtr.mq.entities.base.VMPTicket;
import ru.bars_open.medvtr.mq.entities.message.HospitalizationCreateMessage;
import ru.bars_open.medvtr.mq.entities.message.HospitalizationFinishMessage;
import ru.bars_open.medvtr.mq.entities.message.HospitalizationMovingMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Map;

@Repository
public class WSFactory{

    private static final Logger log = LoggerFactory.getLogger(WSFactory.class);
    private final static ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final PharmacyHospitalizationPortType webservice;
    private final URL serviceURL;
    private final QName serviceName;


    @Autowired
    public WSFactory(ConfigurationHolder cfg) throws MalformedURLException {
        this.serviceURL = new URL(cfg.getString(ConfigurationKeys.WEBSERVICE_URL));
        this.serviceName = new QName(cfg.getString(ConfigurationKeys.WEBSERVICE_NAMESPACE), cfg.getString(ConfigurationKeys.WEBSERVICE_NAME));
        final PharmacyHospitalization service = new PharmacyHospitalization();
        service.setHandlerResolver(portInfo -> new ArrayList<>(Collections.singletonList(new SHandler())));
        // Timeout in millis
        int requestTimeout = 10000;
        int connectTimeout = 1000;

        final PharmacyHospitalizationPortType port = service.getPharmacyHospitalizationSoap();
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


    public PharmacyHospitalizationPortType getWebService() {
        return webservice;
    }

    public URL getServiceURL() {
        return serviceURL;
    }

    public QName getServiceName() {
        return serviceName;
    }

    public static XMLGregorianCalendar wrapDate(final DateTime date) {
        if (date == null) {
            return null;
        }
        final GregorianCalendar calendar = new GregorianCalendar(date.getZone().toTimeZone());
        calendar.setTimeInMillis(date.getMillis());
        return new XMLGregorianCalendarImpl(calendar);
    }

    public CloseHospitalizationRequest createHospitalizationRequest(final HospitalizationFinishMessage message) {
        final CloseHospitalizationRequest result = OBJECT_FACTORY.createCloseHospitalizationRequest();
        result.setEvent(createEvent(message.getEvent()));
        for (ru.bars_open.medvtr.mq.entities.action.StationaryMoving moving : message.getMovings()) {
            result.getMoves().add(createMoving(moving));
        }
        result.setLeaved(createLeaved(message.getLeaved()));
        return result;
    }

    private StationaryLeaved createLeaved(final ru.bars_open.medvtr.mq.entities.action.StationaryLeaved source) {
        final StationaryLeaved result = OBJECT_FACTORY.createStationaryLeaved();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setHospOutcome(source.getHospOutcome());
        return result;
    }


    public CreateHospitalizationRequest createHospitalizationRequest(final HospitalizationCreateMessage message) {
        final CreateHospitalizationRequest result = OBJECT_FACTORY.createCreateHospitalizationRequest();
        result.setEvent(createEvent(message.getEvent()));
        result.setReceived(createReceived(message.getReceived()));
        return result;
    }

    public AddMovingRequest createAddMovingRequest(final HospitalizationMovingMessage message) {
        final AddMovingRequest result = OBJECT_FACTORY.createAddMovingRequest();
        result.setEvent(createEvent(message.getEvent()));
        result.setMoving(createMoving(message.getMoving()));
        return result;
    }

    private StationaryMoving createMoving(final ru.bars_open.medvtr.mq.entities.action.StationaryMoving source) {
        final StationaryMoving result = OBJECT_FACTORY.createStationaryMoving();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setOrgStructStay(createOrgStructure(source.getOrgStructStay()));
        result.setOrgStructReceived(createOrgStructure(source.getOrgStructReceived()));
        return result;
    }

    private StationaryReceived createReceived(final ru.bars_open.medvtr.mq.entities.action.StationaryReceived source) {
        final StationaryReceived result = OBJECT_FACTORY.createStationaryReceived();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setOrgStructDirection(createOrgStructure(source.getOrgStructDirection()));
        result.setOrgStructStay(createOrgStructure(source.getOrgStructStay()));
        return result;
    }

    private Status wrapStatus(String source) {
        return Status.fromValue(source);
    }

    private OrgStructure createOrgStructure(final ru.bars_open.medvtr.mq.entities.base.OrgStructure source) {
        if (source == null) {
            return null;
        }
        final OrgStructure result = OBJECT_FACTORY.createOrgStructure();
        result.setId(source.getId());
        result.setCode(source.getCode());
        result.setName(source.getName());
        return result;
    }

    private Event createEvent(final ru.bars_open.medvtr.mq.entities.base.Event source) {
        final ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.Event result = OBJECT_FACTORY.createEvent();
        result.setId(source.getId());
        result.setSetDate(wrapDate(source.getSetDate()));
        result.setExternalId(source.getExternalId());
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setClient(createPerson(source.getClient()));
        result.setContract(createContract(source.getContract()));
        result.setVmpTicket(createVmpTicket(source.getVmpTicket()));
        return result;
    }

    private VmpTicket createVmpTicket(final VMPTicket source) {
        if (source == null) {
            return null;
        }
        final VmpTicket result = OBJECT_FACTORY.createVmpTicket();
        result.setId(source.getId());
        result.setNumber(source.getNumber());
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setTreatment(createRbTreatment(source.getTreatment()));
        return result;
    }

    private RbTreatment createRbTreatment(final ru.bars_open.medvtr.mq.entities.base.refbook.RbTreatment source) {
        if (source == null) {
            return null;
        }
        final RbTreatment result = OBJECT_FACTORY.createRbTreatment();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setCode(source.getCode());
        return result;
    }

    private Contract createContract(final ru.bars_open.medvtr.mq.entities.base.Contract source) {
        if (source == null) {
            return null;
        }
        final Contract result = OBJECT_FACTORY.createContract();
        result.setId(source.getId());
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setSignDate(wrapDate(source.getSignDate()));
        result.setNumber(source.getNumber());
        result.setPayer(createContragent(source.getPayer()));
        result.setFinance(createRbFinance(source.getFinance()));
        return result;
    }

    private Contragent createContragent(final ru.bars_open.medvtr.mq.entities.base.Contragent source) {
        final Contragent result = OBJECT_FACTORY.createContragent();
        result.setId(source.getId());
        result.setType(wrapContragentType(source.getType().value()));
        switch (result.getType()) {
            case JURIDICAL:
                result.setOrganisation(createOrganisation(source.getOrganisation()));
                break;
            case PHYSICAL:
                result.setPerson(createPerson(source.getPerson()));
                break;
        }
        return result;
    }

    private Organisation createOrganisation(final ru.bars_open.medvtr.mq.entities.base.Organisation source) {
        if (source == null) {
            return null;
        }
        final Organisation result = OBJECT_FACTORY.createOrganisation();
        result.setId(source.getId());
        result.setShortName(source.getShortName());
        result.setUuid(source.getUuid());
        return result;
    }

    private ContragentType wrapContragentType(final String value) {
        return ContragentType.fromValue(value);
    }

    private RbFinance createRbFinance(final ru.bars_open.medvtr.mq.entities.base.refbook.RbFinance source) {
        if (source == null) {
            return null;
        }
        final RbFinance result = OBJECT_FACTORY.createRbFinance();
        result.setId(source.getId());
        result.setCode(source.getCode());
        result.setName(source.getName());
        return result;
    }

    private Person createPerson(final ru.bars_open.medvtr.mq.entities.base.Person source) {
        if (source == null) {
            return null;
        }
        final Person result = OBJECT_FACTORY.createPerson();
        result.setId(source.getId());
        result.setBirthDate(wrapDate(source.getBirthDate()));
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setPatrName(source.getPatrName());
        result.setSex(wrapSex(source.getSex()));
        //TODO addresses
        return result;
    }

    private Sex wrapSex(final ru.bars_open.medvtr.mq.entities.base.Person.Sex source) {
        if (source == null) {
            return Sex.UNKNOWN;
        }
        return Sex.valueOf(source.value());
    }



}
