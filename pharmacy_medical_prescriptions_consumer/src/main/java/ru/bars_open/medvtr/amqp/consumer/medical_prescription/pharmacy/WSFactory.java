package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.*;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.RbFinance;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.RbTreatment;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.RbUnit;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.RlsActMatters;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.RlsNomen;

import ru.bars_open.medvtr.mq.entities.message.PrescriptionListMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.SoapLoggingHandler;

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
public class WSFactory {

    private static final Logger log = LoggerFactory.getLogger(WSFactory.class);
    private final static ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final PharmacyMedicalPrescriptionPortType webservice;
    private final URL serviceURL;
    private final QName serviceName;


    @Autowired
    public WSFactory(ConfigurationHolder cfg) throws MalformedURLException {
        this.serviceURL = new URL(cfg.getString(ConfigurationKeys.WEBSERVICE_URL));
        this.serviceName = new QName(cfg.getString(ConfigurationKeys.WEBSERVICE_NAMESPACE), cfg.getString(ConfigurationKeys.WEBSERVICE_NAME));
        final PharmacyMedicalPrescription service = new PharmacyMedicalPrescription();
        service.setHandlerResolver(portInfo -> new ArrayList<>(Collections.singletonList(new SoapLoggingHandler())));
        // Timeout in millis
        int requestTimeout = 10000;
        int connectTimeout = 1000;

        final PharmacyMedicalPrescriptionPortType port = service.getPharmacyMedicalPrescriptionSoap();
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


    public PharmacyMedicalPrescriptionPortType getWebService() {
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

    public CreateRequest createCreateRequest(final PrescriptionListMessage source) {
        final CreateRequest result = OBJECT_FACTORY.createCreateRequest();
        result.setEvent(createEvent(source.getEvent()));
        result.setPrescription(createPrescription(source.getPrescription()));
        return result;
    }

    public CloseRequest createCloseRequest(final PrescriptionListMessage source) {
        final CloseRequest result = OBJECT_FACTORY.createCloseRequest();
        result.setEvent(createEvent(source.getEvent()));
        result.setPrescription(createPrescription(source.getPrescription()));
        return result;
    }

    private PrescriptionAction createPrescription(final ru.bars_open.medvtr.mq.entities.action.PrescriptionAction source) {
        final PrescriptionAction result = OBJECT_FACTORY.createPrescriptionAction();
        result.setId(source.getId());
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setStatus(wrapStatus(source.getStatus().value()));
        for (ru.bars_open.medvtr.mq.entities.base.MedicalPrescription medicalPrescription : source.getPrescriptions()) {
            result.getPrescriptions().add(createMedicalPrescription(medicalPrescription));
        }
        return result;
    }

    private MedicalPrescription createMedicalPrescription(final ru.bars_open.medvtr.mq.entities.base.MedicalPrescription source) {
        final MedicalPrescription result = OBJECT_FACTORY.createMedicalPrescription();
        result.setId(source.getId());
        result.setStatus(wrapStatus(source.getStatus().value()));
        result.setBegDate(wrapDate(source.getBegDate()));
        result.setNote(source.getNote());
        result.setDose(createValueAndUnit(source.getDose()));
        result.setDuration(createValueAndUnit(source.getDuration()));
        result.setFrequency(createValueAndUnit(source.getFrequency()));
        result.setReasonOfCancel(source.getReasonOfCancel());
        result.setRls(createRls(source.getRls()));
        return result;
    }

    private RlsNomen createRls(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsNomen source) {
        final RlsNomen result = OBJECT_FACTORY.createRlsNomen();
        result.setId(source.getId());
        result.setActMatters(createActMatters(source.getActMatters()));
        result.setTradeName(createTradeName(source.getTradeName()));
        result.setForm(createRlsForm(source.getForm()));
        result.setPackaging(createRlsPackaging(source.getPackaging()));
        result.setFilling(createRlsFilling(source.getFilling()));
        result.setUnit(createRbUnit(source.getUnit()));
        result.setDose(createValueAndUnit(source.getDose()));
        result.setDrugLifeTime(source.getDrugLifeTime());
        return result;
    }

    private RlsFilling createRlsFilling(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsFilling source) {
        if (source == null) {
            return null;
        }
        final RlsFilling result = OBJECT_FACTORY.createRlsFilling();
        result.setId(source.getId());
        result.setName(source.getName());
        return result;
    }

    private RlsPackaging createRlsPackaging(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsPackaging source) {
        if (source == null) {
            return null;
        }
        final RlsPackaging result = OBJECT_FACTORY.createRlsPackaging();
        result.setId(source.getId());
        result.setName(source.getName());
        return result;
    }

    private RlsForm createRlsForm(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsForm source) {
        if (source == null) {
            return null;
        }
        final RlsForm result = OBJECT_FACTORY.createRlsForm();
        result.setId(source.getId());
        result.setName(source.getName());
        return result;
    }

    private RlsTradeName createTradeName(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsTradeName source) {
        if (source == null) {
            return null;
        }
        final RlsTradeName result = OBJECT_FACTORY.createRlsTradeName();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setLocalName(source.getLocalName());
        return result;
    }

    private RlsActMatters createActMatters(final ru.bars_open.medvtr.mq.entities.base.refbook.RlsActMatters source) {
        if (source == null) {
            return null;
        }
        final RlsActMatters result = OBJECT_FACTORY.createRlsActMatters();
        result.setId(source.getId());
        result.setLocalName(source.getLocalName());
        result.setName(source.getName());
        return result;
    }

    private ValueAndUnit createValueAndUnit(final ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit source) {
        if (source == null) {
            return null;
        }
        final ValueAndUnit result = OBJECT_FACTORY.createValueAndUnit();
        result.setValue(source.getValue());
        result.setUnit(createRbUnit(source.getUnit()));
        return result;
    }

    private RbUnit createRbUnit(final ru.bars_open.medvtr.mq.entities.base.refbook.RbUnit source) {
        if (source == null) {
            return null;
        }
        final RbUnit result = OBJECT_FACTORY.createRbUnit();
        result.setId(source.getId());
        result.setCode(source.getCode());
        result.setName(source.getName());
        result.setShortName(source.getShortName());
        return result;
    }

    private Status wrapStatus(String source) {
        return Status.fromValue(source);
    }


    private Event createEvent(final ru.bars_open.medvtr.mq.entities.base.Event source) {
        final Event result = OBJECT_FACTORY.createEvent();
        result.setId(source.getId());
        result.setSetDate(wrapDate(source.getSetDate()));
        result.setExternalId(source.getExternalId());
        result.setEndDate(wrapDate(source.getEndDate()));
        result.setClient(createPerson(source.getClient()));
        result.setContract(createContract(source.getContract()));
        result.setVmpTicket(createVmpTicket(source.getVmpTicket()));
        return result;
    }

    private VmpTicket createVmpTicket(final ru.bars_open.medvtr.mq.entities.base.VMPTicket source) {
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
