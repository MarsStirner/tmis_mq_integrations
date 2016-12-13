package ru.bars_open.medvtr.amqp.consumer.finance;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Author: Upatov Egor <br>
 * Date: 03.11.2016, 17:15 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class ErrorPublisher {

    private static final Logger log = LoggerFactory.getLogger("PUBLISHER");
    private static final int ATTEMPS_LIMIT = 3;
    private static final String HEADER_DELAY = "x-delay";
    private static final String HEADER_ATTEMPTS = "ATTEMPTS";
    private static final String HEADER_NOTE = "NOTE";
    private static final String HEADER_ERROR_NOTE = "ERROR_NOTE";

    @Autowired
    private Channel channel;

    @Autowired
    private ConfigManager cfg;


    public void publish(
            final long messageTag,
            final String exchange,
            final String routingKey,
            final AMQP.BasicProperties props,
            final byte[] body
    ) throws IOException {
        log.info("#{} Start publish to Exchange[{}] with KEY[{}]", messageTag, exchange, routingKey);
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder("#").append(messageTag).append(" MessageProperties");
            props.appendPropertyDebugStringTo(sb);
            log.debug("{}", sb.toString());
        }
        channel.basicPublish(exchange, routingKey, props, body);
        log.info("#{} Published", messageTag);
    }

    public void publishWithDelay(
            final long messageTag,
            final String exchange,
            final String routingKey,
            final AMQP.BasicProperties props,
            final byte[] body,
            final String note,
            final int delay
    ) throws IOException {
        final Map<String, Object> headers = new HashMap<>(props.getHeaders());
        headers.put(HEADER_NOTE, note);
        headers.put(HEADER_DELAY, delay);
        int attempts = (int) headers.getOrDefault(HEADER_ATTEMPTS, 0);
        headers.put(HEADER_ATTEMPTS, ++attempts);

        final AMQP.BasicProperties publishProperties = new AMQP.BasicProperties.Builder()
                .contentType(props.getContentType())
                .contentEncoding(StringUtils.isNotEmpty(props.getContentEncoding()) ? props.getContentEncoding() : StandardCharsets.UTF_8.name())
                .headers(headers)
                .deliveryMode(props.getDeliveryMode())
                .priority(props.getPriority())
                .correlationId(StringUtils.isNotEmpty(props.getCorrelationId()) ? props.getCorrelationId() : UUID.randomUUID().toString())
                .replyTo(props.getReplyTo())
                .expiration(props.getExpiration())
                .timestamp(props.getTimestamp() != null ? props.getTimestamp() : new DateTime().toDate())
                .messageId(props.getMessageId())
                .userId(props.getUserId())
                .appId(props.getAppId())
                .clusterId(props.getClusterId())
                .build();
        if(ATTEMPS_LIMIT < attempts){
            log.warn("#{} Message reach attempts limit", messageTag);
            publishToErrorQueue(messageTag, exchange, routingKey, publishProperties, body, "Message reach attempts limit");
        } else {
            publish(messageTag,  cfg.getValue(ConfigManager.ERROR_EXCHANGE), routingKey , publishProperties, body);
        }
    }

    public void publishToErrorQueue(
            final long messageTag,
            final String exchange,
            final String routingKey,
            final AMQP.BasicProperties props,
            final byte[] body,
            final String note
    ) throws IOException {
        final Map<String, Object> headers = new HashMap<>(props.getHeaders());
        headers.put(HEADER_ERROR_NOTE, note);
        headers.remove(HEADER_DELAY);
        final AMQP.BasicProperties publishProperties = new AMQP.BasicProperties.Builder()
                .contentType(props.getContentType())
                .contentEncoding(StringUtils.isNotEmpty(props.getContentEncoding()) ? props.getContentEncoding() : StandardCharsets.UTF_8.name())
                .headers(headers)
                .deliveryMode(props.getDeliveryMode())
                .priority(props.getPriority())
                .correlationId(StringUtils.isNotEmpty(props.getCorrelationId()) ? props.getCorrelationId() : UUID.randomUUID().toString())
                .replyTo(props.getReplyTo())
                .expiration(null)
                .timestamp(props.getTimestamp() != null ? props.getTimestamp() : new DateTime().toDate())
                .messageId(props.getMessageId())
                .userId(props.getUserId())
                .appId(props.getAppId())
                .clusterId(props.getClusterId())
                .build();
        publish(messageTag, exchange,  routingKey, publishProperties, body);
    }
}
