package ru.bars_open.medvtr.amqp.consumer.finance;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.mq.entities.message.InvoiceMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Author: Upatov Egor <br>
 * Date: 27.10.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class ConsumerCreated extends com.rabbitmq.client.DefaultConsumer {

    private static final Logger log = LoggerFactory.getLogger(ConsumerCreated.class);

    private final String queueName;


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
    public ConsumerCreated(final Channel channel, final ConfigurationHolder cfg) throws IOException {
        super(channel);
        log.info("Start init Consumer [@{}]", Integer.toHexString(this.hashCode()));
        this.queueName = cfg.getString(ConfigurationKeys.QUEUE_CREATED);
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
            final String consumerTag, final Envelope envelope, final AMQP.BasicProperties props, final byte[] body
    ) throws IOException {
        final long tag = envelope.getDeliveryTag();
        final Charset encoding = DeserializationFactory.getEncoding(log, tag, props.getContentEncoding());
        final String routingKey = envelope.getRoutingKey();
        log.info("#{} Receive new message[RK='{}']({}): '{}'", tag, routingKey, encoding, new String(body, encoding));
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder("#").append(tag).append(" MessageProperties");
            props.appendPropertyDebugStringTo(sb);
            log.debug(sb.toString());
        }
        try {
            final InvoiceMessage message = DeserializationFactory.parse(body, props.getContentType(), encoding, InvoiceMessage.class);
            if (validateMessage(tag, message)) {
                final int wsResult = webservice.sendInvoice(tag, message, false);
                if (Objects.equals(wsResult, message.getEvent().getId())) {
                    log.info("#{} End. Successfully.", tag);
                } else {
                    log.warn("#{} WS result is not correct. Expected[{}], actual[{}]", tag, message.getEvent().getId(), wsResult);
                    publisher.publishWithDelay(tag, routingKey, props, body, "WS result is not correct: " + wsResult);
                }
            } else {
                log.error("#{} Message is not valid. Skip it!", tag);
                publisher.messageIsNotValid(tag, props, body);
            }
        } catch (final Exception e) {
            log.error("#{} Exception occurred:", tag, e);
            publisher.publishWithDelay(tag, routingKey, props, body, "Exception occurred " + e.getMessage());
        }
    }

    private boolean validateMessage(final long messageTag, final InvoiceMessage message) {
        if (message == null) {
            log.warn("#{}: message is null", messageTag);
            return false;
        } else if (message.getEvent() == null) {
            log.warn("#{}: message has empty Event", messageTag);
            return false;
        } else if (message.getEvent().getClient() == null) {
            log.warn("#{}: message has empty Event.Client", messageTag);
            return false;
        } else if (message.getInvoice() == null) {
            log.warn("#{}: message has empty Invoice", messageTag);
            return false;
        } else if (message.getInvoice().getContract() == null) {
            log.warn("#{}: message has empty Invoice.Contract", messageTag);
            return false;
        } else if (message.getInvoice().getContract().getPayer() == null) {
            log.warn("#{}: message has empty Invoice.Contract.Payer", messageTag);
            return false;
        }
        return true;
    }

}
