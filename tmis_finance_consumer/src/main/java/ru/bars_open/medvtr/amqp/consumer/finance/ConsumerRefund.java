package ru.bars_open.medvtr.amqp.consumer.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.mq.entities.finance.Invoice;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Author: Upatov Egor <br>
 * Date: 27.10.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class ConsumerRefund extends com.rabbitmq.client.DefaultConsumer {

    private static final Logger log = LoggerFactory.getLogger(ConsumerRefund.class);
    private final String queueName;

    private final String errorExchange;
    private final String errorRoutingKey;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ErrorPublisher publisher;

    @Autowired
    private FinanceSender webservice;


    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    @Autowired
    public ConsumerRefund(final Channel channel, final ConfigManager cfg) throws IOException {
        super(channel);
        log.info("Start init Consumer [@{}]", Integer.toHexString(this.hashCode()));
        this.queueName = cfg.getValue(ConfigManager.QUEUE_REFUND);
        this.errorExchange = cfg.getValue(ConfigManager.ERROR_EXCHANGE);
        this.errorRoutingKey = cfg.getValue(ConfigManager.ERROR_ROUTING_KEY);
    }

    @PostConstruct
    public void init() throws IOException {
        getChannel().basicConsume(queueName, true, this);
        log.info("Register Consumer [@{}] to Queue[{}]", Integer.toHexString(this.hashCode()), queueName);
    }

    @Override
    public void handleShutdownSignal(final String consumerTag, final ShutdownSignalException sig) {
        log.debug("{} shutdown. Cause='{}')", consumerTag, sig.getReason());
    }

    @Override
    public void handleDelivery(
            final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body
    ) throws IOException {
        final long tag = envelope.getDeliveryTag();
        final String message = new String(body, StandardCharsets.UTF_8);
        log.info("#{} Receive new message: '{}'", tag, message);
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder("#").append(tag).append(" MessageProperties");
            properties.appendPropertyDebugStringTo(sb);
            log.debug("{}", sb.toString());
        }
        try {
            final Invoice invoice = parseInvoiceFromText(tag, message);
            if (invoice != null) {
                if( invoice.getParent() == null){
                    log.warn("#{} Invoice hasnt Parent, but stored in this queue['{}']", tag, queueName);
                }
                final int wsResult = webservice.sendInvoice(tag, invoice, false, true);
                if (Objects.equals(wsResult, invoice.getEvent().getId())) {
                    log.info("#{} End. Successfully.", tag);
                } else {
                    log.warn("#{} WS result is not correct. Expected[{}], actual[{}]", tag, invoice.getEvent().getId(), wsResult);
                    publisher.publishWithDelay(tag, errorExchange, envelope.getRoutingKey(), properties, body, "WS result is not correct: " + wsResult,  60000);
                }
            } else {
                log.error("#{} Message has unknown format, Skip it!", tag);
                publisher.publishToErrorQueue(tag, errorExchange, errorRoutingKey, properties, body, "Message has unknown format");
            }
        } catch (final Exception e) {
            log.error("#{} Exception occurred:", tag, e);
            publisher.publishWithDelay(tag, errorExchange, envelope.getRoutingKey(), properties, body, "Exception occurred " + e.getMessage(), 120000);
        }
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
}
