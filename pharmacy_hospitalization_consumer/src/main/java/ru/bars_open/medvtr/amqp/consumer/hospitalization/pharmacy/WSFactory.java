package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.*;
import ru.bars_open.medvtr.mq.entities.base.VMPTicket;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.SoapLoggingHandler;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class WSFactory {

    private static final Logger log = LoggerFactory.getLogger(WSFactory.class);
    private final static ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final PharmacyHospitalizationPortType webservice;
    private final URL serviceURL;
    private final QName serviceName;


    @Autowired
    public WSFactory(ConfigurationHolder cfg) throws MalformedURLException {
        this.serviceURL = new URL(cfg.getString(ConfigurationKeys.WEBSERVICE_URL));
        this.serviceName = new QName(cfg.getString(ConfigurationKeys.WEBSERVICE_NAMESPACE), cfg.getString(ConfigurationKeys.WEBSERVICE_NAME));
        final PharmacyHospitalization service = new PharmacyHospitalization(
                getClass().getClassLoader().getResource("PharmacyHospitalization.wsdl"),
                serviceName
        );
        service.setHandlerResolver(portInfo -> new ArrayList<>(Collections.singletonList(new SoapLoggingHandler())));
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

    public static XMLGregorianCalendar wrapDate(final LocalDateTime date) {
        if (date == null) {
            return null;
        }
        try {
            final XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar
                                                                                                              .from(date.atZone(ZoneId.systemDefault())));
            result.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            return result;
        } catch (DatatypeConfigurationException e) {
            log.error("Cannot convert Date[{}] to XMLGregorianCalendar", date, e);
            return null;
        }
    }

//    public CloseHospitalizationRequest createHospitalizationRequest(final HospitalizationFinishMessage message) {
//        final CloseHospitalizationRequest result = OBJECT_FACTORY.createCloseHospitalizationRequest();
//        result.setEvent(createEvent(message.getEvent()));
//        for (ru.bars_open.medvtr.mq.entities.action.StationaryMoving moving : message.getMovings()) {
//            result.getMoves().add(createMoving(moving));
//        }
//        result.setLeaved(createLeaved(message.getLeaved()));
//        return result;
//    }

    public StationaryLeaved createLeaved(final ru.bars_open.medvtr.mq.entities.action.StationaryLeaved source) {
        final StationaryLeaved result = OBJECT_FACTORY.createStationaryLeaved();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setHospOutcome(source.getHospOutcome());
        return result;
    }

//
//    public CreateHospitalizationRequest createHospitalizationRequest(final HospitalizationCreateMessage message) {
//        final CreateHospitalizationRequest result = OBJECT_FACTORY.createCreateHospitalizationRequest();
//        result.setEvent(createEvent(message.getEvent()));
//        result.setReceived(createReceived(message.getReceived()));
//        return result;
//    }
//
//    public AddMovingRequest createAddMovingRequest(final HospitalizationMovingMessage message) {
//        final AddMovingRequest result = OBJECT_FACTORY.createAddMovingRequest();
//        result.setEvent(createEvent(message.getEvent()));
//        result.setMoving(createMoving(message.getMoving()));
//        return result;
//    }

    public StationaryMoving createMoving(final ru.bars_open.medvtr.mq.entities.action.StationaryMoving source) {
        final StationaryMoving result = OBJECT_FACTORY.createStationaryMoving();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setOrgStructStay(createOrgStructure(source.getOrgStructStay()));
        result.setOrgStructReceived(createOrgStructure(source.getOrgStructReceived()));
        return result;
    }

    public StationaryReceived createReceived(final ru.bars_open.medvtr.mq.entities.action.StationaryReceived source) {
        final StationaryReceived result = OBJECT_FACTORY.createStationaryReceived();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        //UX: разные названия из-за того что изначально в ФНКЦ (мы так называли поля постулпения, а в ГНЦ переделали)
        result.setOrgStructDirection(createOrgStructure(source.getOrgStructTransfer()));
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

    public Event createEvent(final ru.bars_open.medvtr.mq.entities.base.Event source) {
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
        if (source.getAddresses() != null && !source.getAddresses().isEmpty()) {
            result.getAddresses().addAll(source.getAddresses().stream().filter(Objects::nonNull).map(this::createAddress)
                                                 .collect(Collectors.toList()));
        }
        return result;
    }

    private Address createAddress(final ru.bars_open.medvtr.mq.entities.base.Address source) {
        final Address result = OBJECT_FACTORY.createAddress();
        result.setId(source.getId());
        result.setValue(source.getValue());
        switch (source.getAddressType()) {
            case REGISTRATION:
                result.setAddressType(AddressType.REGISTRATION);
                break;
            case LIVING:
                result.setAddressType(AddressType.LIVING);
                break;
            case UNKNOWN:
                result.setAddressType(AddressType.UNKNOWN);
                break;
        }
        return result;
    }

    private Sex wrapSex(final ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex source) {
        return source != null ? Sex.valueOf(source.value()) : Sex.UNKNOWN;
    }

    public Moves createMoves(final List<ru.bars_open.medvtr.mq.entities.action.StationaryMoving> movings) {
        final Moves result = OBJECT_FACTORY.createMoves();
        result.getMove().addAll(movings.stream().map(this::createMoving).collect(Collectors.toList()));
        return result;
    }
}
