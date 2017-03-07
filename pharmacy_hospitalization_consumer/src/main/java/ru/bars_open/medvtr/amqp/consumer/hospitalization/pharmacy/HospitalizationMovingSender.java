package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.Event;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.StationaryMoving;
import ru.bars_open.medvtr.mq.entities.message.HospitalizationMovingMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.ValidationFactory;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;

import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.2017, 18:03 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class HospitalizationMovingSender {
    private static final Logger log = LoggerFactory.getLogger(HospitalizationMovingSender.class);

    @Autowired
    private WSFactory webserviceFactory;

    public boolean send(final long tag, final Charset encoding, final Message amqpMessage) throws MalformedURLException, MessageIsIncorrectException {
        final HospitalizationMovingMessage message = DeserializationFactory.parse(amqpMessage.getBody(),
                                                                                  amqpMessage.getMessageProperties().getContentType(),
                                                                                  encoding,
                                                                                  HospitalizationMovingMessage.class
        );
        validateMessage(message);
        log.info("#{}: message is valid", tag);
        final Event event = webserviceFactory.createEvent(message.getEvent());
        final StationaryMoving move = webserviceFactory.createMoving(message.getMoving());
        log.info("#{}: Request parameters constructed", tag);
        final String response = webserviceFactory.getWebService().addMoving(event, move);
        log.info("#{}: Response from WS = {}", tag, response);
        if (StringUtils.isEmpty(response) || !"OK".equals(response)) {
            throw new WebServiceException("Webservice['" + webserviceFactory
                    .getServiceURL() + "'] after processing addMoving returned unexpected result=" + response);
        }
        return true;
    }

    private void validateMessage(final HospitalizationMovingMessage message) throws MessageIsIncorrectException {
        final Set<String> errors = ValidationFactory.getErrors(message, "HospitalizationMovingMessage");
        if (!errors.isEmpty()) {
            throw new MessageIsIncorrectException(errors);
        }
    }


}
