package ru.bars_open.medvtr.amqp.consumer.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMIS;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ExchangeMISPortType;
import ru.bars_open.medvtr.amqp.consumer.finance.util.SHandler;
import ru.bars_open.medvtr.amqp.consumer.finance.util.WebServiceObjectFactory;
import ru.bars_open.medvtr.amqp.consumer.finance.util.amqp.Queue;
import ru.bars_open.medvtr.mq.entities.Event;
import ru.bars_open.medvtr.mq.entities.Person;
import ru.bars_open.medvtr.mq.entities.finance.Invoice;
import ru.bars_open.medvtr.mq.entities.finance.InvoiceData;

import javax.annotation.PostConstruct;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * Author: Upatov Egor <br>
 * Date: 27.10.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class Consumer implements com.rabbitmq.client.Consumer {

    private static final Logger log = LoggerFactory.getLogger("CONSUMER");
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ConfigManager configManager;

    @Autowired
    private Channel channel;

    @Autowired
    @Qualifier("queue")
    private Queue queue;

    @Autowired
    private Publisher publisher;


    @Override
    public void handleConsumeOk(final String consumerTag) {
    }

    @Override
    public void handleCancelOk(final String consumerTag) {
    }

    @Override
    public void handleCancel(final String consumerTag) throws IOException {
    }

    @Override
    public void handleShutdownSignal(final String consumerTag, final ShutdownSignalException sig) {
        log.debug("{} shutdown. Cause='{}')", consumerTag, sig.getReason());
    }

    @Override
    public void handleRecoverOk(final String consumerTag) {
    }

    @Override
    public void handleDelivery(
            final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body
    ) throws IOException {
        final long messageTag = envelope.getDeliveryTag();
        final String message = new String(body, StandardCharsets.UTF_8);
        log.info("#{} Receive new message:\'{}\'", messageTag, message);
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder("#").append(messageTag).append(" MessageProperties");
            properties.appendPropertyDebugStringTo(sb);
            log.debug("{}", sb.toString());
        }
        try {
            final Invoice invoice = parseInvoiceFromText(messageTag, message);
            if (invoice != null) {
                final int wsResult = sendInvoiceToWebService(messageTag, invoice);
                if (Objects.equals(wsResult, invoice.getEvent().getId())) {
                    log.info("#{} End. Successfully.", messageTag);
                } else {
                    log.warn("#{} WS result is not correct. Expected[{}], actual[{}]", messageTag, invoice.getEvent().getId(), wsResult);
                    publisher.publishWithDelay(messageTag, "WS result is not correct " + wsResult, properties, body, 60000);
                }
            } else {
                log.error("#{} Message has unknown format, Skip it!", messageTag);
                publisher.publishToErrorQueue(messageTag, "Message has unknown format", properties, body);
            }
        } catch (final Exception e) {
            log.error("#{} Exception occurred:", messageTag, e);
            publisher.publishWithDelay(messageTag, "Exception occurred " + e.getMessage(), properties, body, 120000);
        }
    }


    public ExchangeMISPortType createFinanceWebService() {
        final URL serviceUrl = configManager.getValue(ConfigManager.PROP_KEY_FINANCE_SERVICE_URL, URL.class);
        final QName serviceName = new QName(
                configManager.getValue(ConfigManager.PROP_KEY_FINANCE_SERVICE_NAMESPACE),
                configManager.getValue(ConfigManager.PROP_KEY_FINANCE_SERVICE_NAME)
        );
        final ExchangeMIS exchangeMIS = new ExchangeMIS(serviceUrl, serviceName);
        exchangeMIS.setHandlerResolver(portInfo -> {
            final List<Handler> hchain = new ArrayList<>();
            hchain.add(new SHandler());
            return hchain;
        });
        log.info("WS initialized: {} - {}", serviceUrl, serviceName);
        return exchangeMIS.getExchangeMISSoap();
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("Start init Consumer [@{}]", Integer.toHexString(this.hashCode()));
        mapper.registerModule(new JodaModule());
        final String consumerTag = configManager.getValue(ConfigManager.PROP_KEY_AMQP_CONSUMER_TAG);
        channel.basicConsume(queue.getName(), true, consumerTag, this);
        log.info("Register Consumer [@{}] to Queue[{}] with TAG[{}]", Integer.toHexString(this.hashCode()), queue.getName(), consumerTag);
        //TEST ONLY!!! Endpoint.publish("http://dnosov-nb:8080/Mock_1Cwebservice?wsdl", new MockFinanceWebService());
    }

    private int sendInvoiceToWebService(final long messageTag, final Invoice invoice) {
        final Event event = invoice.getEvent();
        final InvoiceData invoiceData = invoice.getInvoiceData();
        final Person client = invoice.getClient();
        final Person payer = invoice.getPayer();
        final ExchangeMISPortType financeWebService = createFinanceWebService();
        final int result = financeWebService.putTreatment(
                event.getId(),
                wrapDate(event.getSetDate()),
                event.getExternalId(),
                invoiceData.getNumber(),
                invoiceData.getSum().intValue(),
                String.valueOf(client.getId()),
                WebServiceObjectFactory.createPersonName(client),
                String.valueOf(payer.getId()),
                WebServiceObjectFactory.createPersonName(payer),
                invoiceData.getDeleted() ? 1 : 0
        );
        log.info("#{} WebService answer - {}", messageTag, result);
        return result;
    }


    private Invoice parseInvoiceFromText(final long messageTag, final String message) {
        try {
            final Invoice result = mapper.readValue(message, Invoice.class);
            if (result.getEvent() == null || result.getClient() == null || result.getPayer() == null || result.getInvoiceData() == null) {
                log.error("#{} Cannot parse Message to Invoice", messageTag);
                return null;
            }
            log.info("#{} Parsed: {}", messageTag, result);
            return result;
        } catch (IOException e) {
            log.error("#{} Exception while parse JSON from text", messageTag, e.getMessage(), message);
            return null;
        }
    }

    private XMLGregorianCalendar wrapDate(final DateTime date) {
        final GregorianCalendar calendar = new GregorianCalendar(date.getZone().toTimeZone());
        calendar.setTimeInMillis(date.getMillis());
        return new XMLGregorianCalendarImpl(calendar);
    }


}
