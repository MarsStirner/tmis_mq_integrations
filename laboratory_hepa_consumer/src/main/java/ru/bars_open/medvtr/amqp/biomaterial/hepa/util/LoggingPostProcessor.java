package ru.bars_open.medvtr.amqp.biomaterial.hepa.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.ConfigurationKeys;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 17:43 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: [A] Логгирование:
 */
@Component("amqpMessagePostProcessor")
public class LoggingPostProcessor implements MessagePostProcessor {
    private static Logger log = LoggerFactory.getLogger("AMQP");

    private final String ROUTING_KEY_SEND;

    @Autowired
    public LoggingPostProcessor(final ConfigurationHolder cfg) {
        this.ROUTING_KEY_SEND = cfg.getString(ConfigurationKeys.REQUEST_SEND_ROUTING_KEY);
    }

    /**
     * [A] Логгирование:
     * -- [A.1] выставить недостающие свойства сообщению (amqp.properties)
     * -- [A.2] заллогировать сообщение
     * @param message
     * @return
     */
    @Override
    public Message postProcessMessage(final Message message) {
        MDCHelper.start(message.getMessageProperties().getDeliveryTag());
        // -- [A.1] выставить недостающие свойства сообщению (amqp.properties)
        enrichMessageProperties(message.getMessageProperties());
        // -- [A.2] заллогировать сообщение
        logMessage(message);
        return message;
    }

    private void logMessage(final Message message) {
        final Charset encoding = DeserializationFactory.getEncoding(message.getMessageProperties().getContentEncoding());
        log.info("### Receive new amqpMessage [RK='{}']:\n{}", message.getMessageProperties().getReceivedRoutingKey(), new String(message.getBody(), encoding));
        if (log.isDebugEnabled()) { log.debug("{}", message.getMessageProperties()); }
    }

    private void enrichMessageProperties(final MessageProperties messageProperties) {
        if(StringUtils.isEmpty(messageProperties.getContentEncoding())){
            final String value = StandardCharsets.UTF_8.name();
            log.warn("ContentEncoding NOT SET! Assign [{}] by DEFAULT", value);
            messageProperties.setContentEncoding(value);
        }
        if(messageProperties.getCorrelationId() != null && messageProperties.getCorrelationId().length > 0){
            String value = new String(messageProperties.getCorrelationId(), StandardCharsets.UTF_8);
            messageProperties.setCorrelationIdString(value);
        } else if (StringUtils.isEmpty(messageProperties.getCorrelationIdString())) {
            final String value = UUID.randomUUID().toString();
            log.warn("CorrelationId NOT SET! Assign [{}] by RANDOM", value);
            messageProperties.setCorrelationIdString(value);
        }
        if (StringUtils.isEmpty(messageProperties.getType())) {
            if (ROUTING_KEY_SEND.equalsIgnoreCase(messageProperties.getReceivedRoutingKey())) {
                final String value = BiologicalMaterialMessage.class.getSimpleName();
                log.warn("Type NOT SET! Assign [{}] by RK[{}]", value, messageProperties.getReceivedRoutingKey());
                messageProperties.setType(value);
            }
        }
        if (StringUtils.isEmpty(messageProperties.getContentType())) {
            if (ROUTING_KEY_SEND.equalsIgnoreCase(messageProperties.getReceivedRoutingKey())) {
                final String value = "application/json";
                log.warn("ContentType NOT SET! Assign [{}] by RK[{}]", value, messageProperties.getReceivedRoutingKey());
                messageProperties.setContentType(value);
            }
        }
    }
}

