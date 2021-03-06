package ru.bars_open.medvtr.amqp.consumer.finance;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.mq.entities.message.InvoiceMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.ValidationFactory;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 27.10.2016, 16:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class ConsumerCreated implements ChannelAwareMessageListener {

    private static final Logger log = LoggerFactory.getLogger(ConsumerCreated.class);


    @Autowired
    private ErrorMessageHandler publisher;

    @Autowired
    private FinanceSender webservice;


    private boolean validateMessage(final long messageTag, final InvoiceMessage message) {
        final Set<String> errors = ValidationFactory.getErrors(message, "InvoiceMessage");
        if (!errors.isEmpty()) {
            log.warn("#{}: Message is not valid. Failed constraints: {}", messageTag, errors);
            return false;
        }
        if (message.getInvoice().getContract().getPayer().getPerson() == null) {
            log.warn("#{}: Message is not valid. InvoiceMessage.Invoice.Contract.Payer.Person is null", messageTag);
            return false;
        }
        return true;
    }

    @Override
    public void onMessage(final Message message, final Channel channel) throws Exception {
        final MessageProperties props = message.getMessageProperties();
        final long tag = props.getDeliveryTag();
        final String routingKey = props.getReceivedRoutingKey();
        final Charset encoding = DeserializationFactory.getEncoding(props.getContentEncoding());
        log.info("###{}: Receive new message[RK='{}']({}):\n{}", tag, routingKey, encoding, new String(message.getBody(), encoding));
        if (log.isDebugEnabled()) {
            log.debug("#{}: {}", tag, props);
        }
        try {
            final InvoiceMessage invoiceMessage = DeserializationFactory.parse(message.getBody(),
                                                                               props.getContentType(),
                                                                               encoding,
                                                                               InvoiceMessage.class
            );
            if (validateMessage(tag, invoiceMessage)) {
                final int wsResult = webservice.sendInvoice(tag, invoiceMessage, false);
                if (Objects.equals(wsResult, invoiceMessage.getEvent().getId())) {
                    log.info("#{} End. Successfully.", tag);
                } else {
                    log.warn("#{} WS result is not correct. Expected[{}], actual[{}]", tag, invoiceMessage.getEvent().getId(), wsResult);
                    publisher.handleErrorMessage(tag, message, true, "WS result is not correct: " + wsResult);
                }
            } else {
                log.error("#{} Message is not valid. Skip it!", tag);
                publisher.sendToErrorQueue(tag, message, "Message is not valid");
            }
        } catch (final Exception e) {
            log.error("#{} Exception occurred:", tag, e);
            publisher.handleErrorMessage(tag, message, true, "Exception occurred " + e.getMessage());
        } finally {
            channel.basicAck(tag, false);
        }
    }
}
