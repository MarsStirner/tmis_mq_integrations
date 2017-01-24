package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.CloseHospitalizationRequest;
import ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws.CloseHospitalizationResponse;
import ru.bars_open.medvtr.mq.entities.message.HospitalizationFinishMessage;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.ValidationFactory;

import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.2017, 19:14 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class HospitalizationCloseSender {
    private static final Logger log = LoggerFactory.getLogger(HospitalizationCreateSender.class);

    @Autowired
    private WSFactory webserviceFactory;

    public boolean send(final long tag, final Charset encoding, final Message amqpMessage) throws MalformedURLException, MessageIsIncorrectException {
        final HospitalizationFinishMessage message = DeserializationFactory.parse(amqpMessage.getBody(),
                                                                                  amqpMessage.getMessageProperties().getContentType(),
                                                                                  encoding,
                                                                                  HospitalizationFinishMessage.class
        );
        validateMessage(message);
        log.info("#{}: message is valid", tag);
        final CloseHospitalizationRequest request = webserviceFactory.createHospitalizationRequest(message);
        log.info("#{}: Request parameters constructed", tag);
        final CloseHospitalizationResponse response = webserviceFactory.getWebService().closeHospitalization(request);
        log.info("#{}: Response from WS = {}", tag, response != null ? response.getReturn() : "null");
        if (response == null || StringUtils.isEmpty(response.getReturn()) || !"OK".equals(response.getReturn())) {
            throw new WebServiceException(
                    "Webservice['" + webserviceFactory.getServiceURL()
                            + "'] after processing createHospitalization returned unexpected result="
                            + (response != null ? response.getReturn() : "null")
            );
        }
        return true;
    }

    private void validateMessage(final HospitalizationFinishMessage message) throws MessageIsIncorrectException {
        final Set<String> errors = ValidationFactory.getErrors(message, "HospitalizationFinishMessage");
        if(!errors.isEmpty()){
            throw new MessageIsIncorrectException(errors);
        }
    }
}
