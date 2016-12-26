package ru.bars_open.medvtr.amqp.consumer.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.mq.entities.message.InvoiceMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

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
        final Charset encoding = getEncoding(tag, properties.getContentEncoding());
        log.info("#{} Receive new message[{}]: '{}'", tag, encoding.displayName(), new String(body, encoding));
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder("#").append(tag).append(" MessageProperties");
            properties.appendPropertyDebugStringTo(sb);
            log.debug("{}", sb.toString());
        }
        try {
            final InvoiceMessage message = DeserializationFactory.parse(body,
                                                                        properties.getContentType(),
                                                                        properties.getContentEncoding(),
                                                                        InvoiceMessage.class
            );
            if (validateMessage(tag, message)) {
                final String wsResult = webservice.sendRefund(tag, message);
                log.info("#{} End. Successfully.", tag);
            } else {
                log.error("#{} Message is not valid. Skip it!", tag);
                publisher.publishToErrorQueue(tag, errorExchange, properties, body, "Message is not valid");
            }
        } catch (final Exception e) {
            log.error("#{} Exception occurred:", tag, e);
            publisher.publishWithDelay(
                    tag,
                    errorExchange,
                    envelope.getRoutingKey(),
                    properties,
                    body,
                    "Exception occurred " + e.getMessage(),
                    120000
            );
        }
    }

    private Charset getEncoding(final long tag, final String contentEncoding) {
        if (StringUtils.isNotEmpty(contentEncoding)) {
            try {
                return Charset.forName(contentEncoding);
            } catch (UnsupportedCharsetException e) {
                log.warn("#{}: Encoding with name '{}' is not supported, will use UTF-8", tag, contentEncoding);
                return StandardCharsets.UTF_8;
            }
        }
        log.warn("#{}: Encoding not set, will use UTF-8", tag, contentEncoding);
        return StandardCharsets.UTF_8;
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
