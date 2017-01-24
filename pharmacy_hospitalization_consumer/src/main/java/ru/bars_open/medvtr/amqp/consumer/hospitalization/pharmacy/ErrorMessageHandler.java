package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.2017, 14:21 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class ErrorMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorMessageHandler.class);

    private final String exchange;
    private final int retryAttempts;
    private final String routingKey;
    private final int delay;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    public ErrorMessageHandler(final ConfigurationHolder cfg) {
        this.exchange = cfg.getString(ConfigurationKeys.EXCHANGE_ERROR);
        this.retryAttempts = cfg.getInt(ConfigurationKeys.RETRY_ATTEMPTS);
        this.routingKey = cfg.getString(ConfigurationKeys.ROUTING_KEY_ERROR);
        this.delay = cfg.getInt(ConfigurationKeys.ERROR_DELAY);
    }

    public void handleErrorMessage(final long tag, final Message message, final boolean requeue, final String errorMessage) {
        if (requeue) {
            requeue(tag, message, errorMessage);
        } else {
            sendToErrorQueue(tag, message, errorMessage);
        }
    }

    public void requeue(final long tag, final Message message, final String errorMessage) {
        final Integer retryCount = (Integer) message.getMessageProperties().getHeaders().getOrDefault("x-retry-count", 0);
        if (retryCount < retryAttempts) {
            message.getMessageProperties().setHeader("ERROR_NOTE", errorMessage);
            message.getMessageProperties().setHeader("x-retry-count", retryCount + 1);
            message.getMessageProperties().setDelay(delay);
            message.getMessageProperties().setRedelivered(true);
            template.send(exchange, message.getMessageProperties().getReceivedRoutingKey(), message, new CorrelationData(message.getMessageProperties().getCorrelationIdString()));
            log.info("#{}: Published with delay and message='{}': {}", tag, errorMessage, message.getMessageProperties());
        } else {
            sendToErrorQueue(tag, message, "Message reach attempts limit" );
        }
    }

    public void sendToErrorQueue(final long tag, final Message message, final String errorMessage) {
        message.getMessageProperties().setHeader("ERROR_MESSAGE", errorMessage);
        message.getMessageProperties().setDelay(-1);
        template.send(exchange, routingKey, message, new CorrelationData(message.getMessageProperties().getCorrelationIdString()));
        log.info("#{}: Published to ErrorQueue with message='{}': {}", tag, errorMessage, message.getMessageProperties());
    }


}
