package ru.bars_open.medvtr.amqp.biomaterial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.util.LaboratoryNotAssignedException;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;
import ru.bars_open.medvtr.mq.util.exceptions.UnknownRoutingKeyException;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 15:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("errorHandler")
public class ErrorHandler implements org.springframework.util.ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @Autowired
    private ResponseSender responseSender;

    @Override
    public void handleError(final Throwable t) {
        final ListenerExecutionFailedException wrapper = (ListenerExecutionFailedException) t;
        final Throwable exception = wrapper.getCause();
        final Message failedMessage = wrapper.getFailedMessage();
        final MessageProperties messageProperties = failedMessage.getMessageProperties();
        log.error(
                "Error while processing message [{}][{}]: {}",
                messageProperties.getDeliveryTag(),
                messageProperties.getCorrelationIdString(),
                exception.getMessage()
        );
        if (exception instanceof MessageIsIncorrectException) {
            responseSender.messageIsIncorrect(failedMessage, ((MessageIsIncorrectException) exception).getErrors());
        } else if (exception instanceof LaboratoryNotAssignedException) {
            responseSender.noLaboratoryAssigned(failedMessage);
        } else if (exception instanceof UnknownRoutingKeyException) {
            responseSender.unknownRoutingKey(failedMessage, ((UnknownRoutingKeyException) exception).getPossibleKeys());
        } else {
            log.error("", t);
            responseSender.unknownException(failedMessage, exception.getMessage());
        }
        throw new ImmediateAcknowledgeAmqpException("No need to reject or requeue");
    }
}
