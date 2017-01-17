package ru.bars_open.medvtr.amqp.consumer.finance;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMIS;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMISPortType;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ObjectFactory;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.PersonName;
import ru.bars_open.medvtr.amqp.consumer.finance.util.SHandler;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;


/**
 * Author: Upatov Egor <br>
 * Date: 05.12.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class FinanceWSFactory {

    private static final Logger log = LoggerFactory.getLogger(FinanceWSFactory.class);
    private final static ObjectFactory OBJECT_FACTORY = new ObjectFactory();


    @Autowired
    private ConfigurationHolder cfg;


    public ExchangeMISPortType getWebService() throws MalformedURLException {
        final URL serviceUrl = new URL(cfg.getString(ConfigurationKeys.WEBSERVICE_URL));
        final QName serviceName = new QName(cfg.getString(ConfigurationKeys.WEBSERVICE_NAMESPACE), cfg.getString(ConfigurationKeys.WEBSERVICE_NAME));
        final ExchangeMIS exchangeMIS = new ExchangeMIS(serviceUrl, serviceName);
        exchangeMIS.setHandlerResolver(portInfo -> new ArrayList<>(Collections.singletonList(new SHandler())));
        log.info("WS initialized: {} - {}", serviceUrl, serviceName);
        return exchangeMIS.getExchangeMISSoap();
    }

    public XMLGregorianCalendar wrapDate(final DateTime date) {
        final GregorianCalendar calendar = new GregorianCalendar(date.getZone().toTimeZone());
        calendar.setTimeInMillis(date.getMillis());
        return new XMLGregorianCalendarImpl(calendar);
    }

    public PersonName createPersonName(final String family, final String given, final String partName) {
        final PersonName result = OBJECT_FACTORY.createPersonName();
        result.setFamily(family);
        result.setGiven(given);
        result.setPartName(partName);
        return result;
    }

    public PersonName createPersonName(final Person person) {
        return createPersonName(person.getLastName(), person.getFirstName(), person.getPatrName());
    }
}
