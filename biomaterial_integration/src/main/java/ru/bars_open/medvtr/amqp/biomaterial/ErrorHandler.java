package ru.bars_open.medvtr.amqp.biomaterial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.mq.util.exceptions.MessageIsIncorrectException;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 15:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("errorHandler")
public class ErrorHandler implements org.springframework.util.ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);
    @Override
    public void handleError(final Throwable t) {
        final ListenerExecutionFailedException wrapper = (ListenerExecutionFailedException) t;
        final Message failedMessage = wrapper.getFailedMessage();
        final MessageProperties messageProperties = failedMessage.getMessageProperties();
        log.error("Error while processing message [{}][{}]", messageProperties.getDeliveryTag(), messageProperties.getCorrelationIdString());
        if(wrapper.getCause() instanceof MessageIsIncorrectException){
            final MessageIsIncorrectException ex = (MessageIsIncorrectException) t.getCause();
            //TODO
            log.error("", ex);
            throw new AmqpRejectAndDontRequeueException("No need to requeue, cause custom handler do all job", ex);
        }
        log.error("", t);
    }
}
