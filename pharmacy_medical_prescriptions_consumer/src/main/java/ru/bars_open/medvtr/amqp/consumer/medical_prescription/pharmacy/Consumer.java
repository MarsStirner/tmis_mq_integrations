package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CloseRequest;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CloseResponse;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CreateRequest;
import ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws.CreateResponse;
import ru.bars_open.medvtr.mq.entities.message.PrescriptionListMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.ValidationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

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

    private final Set<String> possibleKeys;


    @Autowired
    public Consumer(final ConfigurationHolder cfg) {
        this.possibleKeys = new HashSet<>(3);
        this.ROUTING_KEY_CREATE = cfg.getString(ConfigurationKeys.ROUTING_KEY_CREATE);
        possibleKeys.add(ROUTING_KEY_CREATE);
        this.ROUTING_KEY_CLOSE = cfg.getString(ConfigurationKeys.ROUTING_KEY_CLOSE);
        possibleKeys.add(ROUTING_KEY_CLOSE);
        log.info("Constructor called. Possible keys = {}", possibleKeys);
    }

    @Autowired
    private ErrorMessageHandler errorHandler;

    @Autowired
    private WSFactory webserviceFactory;


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
        boolean processed = false;
        boolean requeue = false;
        String errorMessage = StringUtils.EMPTY;
        try {
            final PrescriptionListMessage parsedMessage = DeserializationFactory.parse(message.getBody(),
                                                                                       props.getContentType(),
                                                                                       encoding,
                                                                                       PrescriptionListMessage.class
            );
            log.debug("#{}: Parsed", tag);
            validate(parsedMessage);
            log.info("#{}: message is valid", tag);
            if (ROUTING_KEY_CREATE.equals(routingKey)) {
                final CreateRequest request = webserviceFactory.createCreateRequest(parsedMessage);
                log.info("#{}: Request parameters constructed", tag);
                final CreateResponse response = webserviceFactory.getWebService().create(request);
                log.info("#{}: Response from WS = {}", tag, response != null ? response.getReturn() : "null");
                if (response == null || StringUtils.isEmpty(response.getReturn()) || !"OK".equals(response.getReturn())) {
                    throw new WebServiceException("Webservice['" + webserviceFactory
                            .getServiceURL() + "'] after processing createHospitalization returned unexpected result=" + (response != null ? response
                            .getReturn() : "null"));
                }
            } else if (ROUTING_KEY_CLOSE.equals(routingKey)) {
                final CloseRequest request = webserviceFactory.createCloseRequest(parsedMessage);
                log.info("#{}: Request parameters constructed", tag);
                final CloseResponse response = webserviceFactory.getWebService().close(request);
                log.info("#{}: Response from WS = {}", tag, response != null ? response.getReturn() : "null");
                if (response == null || StringUtils.isEmpty(response.getReturn()) || !"OK".equals(response.getReturn())) {
                    throw new WebServiceException("Webservice['" + webserviceFactory
                            .getServiceURL() + "'] after processing createHospitalization returned unexpected result=" + (response != null ? response
                            .getReturn() : "null"));
                }
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

    private void validate(final PrescriptionListMessage message) throws MessageIsIncorrectException {
        final Set<String> errors = ValidationFactory.getErrors(message, "PrescriptionListMessage");
        if (!errors.isEmpty()) {
            throw new MessageIsIncorrectException(errors);
        }
    }


}
