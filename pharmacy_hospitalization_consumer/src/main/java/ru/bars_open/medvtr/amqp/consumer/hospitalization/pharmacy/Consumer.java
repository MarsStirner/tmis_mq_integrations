package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.exceptions.UnknownRoutingKeyException;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;

import javax.xml.ws.WebServiceException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 18.01.2017, 18:58 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class Consumer implements ChannelAwareMessageListener {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String ROUTING_KEY_CREATE;
    private final String ROUTING_KEY_CLOSE;
    private final String ROUTING_KEY_MOVING;

    private final Set<String> possibleKeys;


    @Autowired
    public Consumer(final ConfigurationHolder cfg) {
        this.possibleKeys = new HashSet<>(3);
        this.ROUTING_KEY_CREATE = cfg.getString(ConfigurationKeys.ROUTING_KEY_CREATE);
        possibleKeys.add(ROUTING_KEY_CREATE);
        this.ROUTING_KEY_CLOSE = cfg.getString(ConfigurationKeys.ROUTING_KEY_CLOSE);
        possibleKeys.add(ROUTING_KEY_CLOSE);
        this.ROUTING_KEY_MOVING = cfg.getString(ConfigurationKeys.ROUTING_KEY_MOVING);
        possibleKeys.add(ROUTING_KEY_MOVING);
        log.info("Constructor called. Possible keys = {}", possibleKeys);
    }


    @Autowired
    private HospitalizationCreateSender hospitalizationCreateSender;

    @Autowired
    private HospitalizationMovingSender hospitalizationMovingSender;

    @Autowired
    private HospitalizationCloseSender hospitalizationCloseSender;

    @Autowired
    private ErrorMessageHandler errorHandler;


    @Override
    public void onMessage(final Message message, final Channel channel) throws Exception {
        final MessageProperties props = message.getMessageProperties();
        final long tag = props.getDeliveryTag();
        final String routingKey = props.getReceivedRoutingKey();
        final Charset encoding = DeserializationFactory.getEncoding(log, tag, props.getContentEncoding());
        log.info("###{}: Receive new message[RK='{}']({}):\n{}", tag, routingKey, encoding, new String(message.getBody(), encoding));
        if (log.isDebugEnabled()) {
            log.debug("#{}: {}", tag, props);
        }
        boolean processed = false;
        boolean requeue = false;
        String errorMessage = StringUtils.EMPTY;
        try {
            if (ROUTING_KEY_MOVING.equals(routingKey)) {
                hospitalizationMovingSender.send(tag, encoding, message);
            } else if (ROUTING_KEY_CREATE.equals(routingKey)) {
                hospitalizationCreateSender.send(tag, encoding, message);
            } else if (ROUTING_KEY_CLOSE.equals(routingKey)) {
                hospitalizationCloseSender.send(tag, encoding, message);
            } else {
                throw new UnknownRoutingKeyException(routingKey, possibleKeys);
            }
            channel.basicAck(tag, false);
            processed = true;
            log.info("###{}: End. Successfully processed", tag);
        } catch (WebServiceException e) {
            requeue = true;
            errorMessage = e.getMessage();
            log.error("#{}: Error. Webservice exception:", tag, e);
        } catch (MessageIsIncorrectException e) {
            errorMessage = e.getMessage() + ". Constraints failed:" + e.getErrors();
            log.error("#{}: Error. MessageIsIncorrectException: ", tag, errorMessage);
        } catch (UnknownRoutingKeyException e) {
            errorMessage = e.getMessage();
            log.error("#{}: Error. RoutingKey is unknown:", tag, e);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            log.error("#{}: Error. UnknownException:", tag, e);
        } finally {
            if (!processed) {
                try {
                    errorHandler.handleErrorMessage(tag, message, requeue, errorMessage);
                    channel.basicAck(tag, false);
                } catch (Exception e) {
                    log.error("#{}: Cannot republish message. Send AMQP.NACK with requeue", tag, errorMessage);
                    channel.basicNack(tag, false, true);
                }
                log.info("###{}: End. With error: {}", tag, errorMessage);
            }
        }
    }


}
