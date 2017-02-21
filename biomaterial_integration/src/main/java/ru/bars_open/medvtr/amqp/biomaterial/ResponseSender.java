package ru.bars_open.medvtr.amqp.biomaterial;

import com.typesafe.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.util.MessageHolder;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.Tuple;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 15:47 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class ResponseSender {
    private static final Logger log = LoggerFactory.getLogger(ResponseSender.class);
    private final String appId;

    private final Tuple<String, String> NOT_ASSIGNED;
    private final Tuple<String, String> SENT;
    private final Tuple<String, String> WAIT;
    private final Tuple<String, String> ERROR;


    @Autowired
    private RabbitTemplate template;

    


    @Autowired
    public ResponseSender(final ConfigurationHolder cfg) {
        this.appId = cfg.getAppId();
        final Config responseCfg = cfg.getConfig(ConfigurationKeys.AMQP_RESPONSE);
        this.NOT_ASSIGNED = createTupleFromCfg(responseCfg.getConfig("not_assigned"));
        this.SENT = createTupleFromCfg(responseCfg.getConfig("sent"));
        this.WAIT = createTupleFromCfg(responseCfg.getConfig("wait"));
        this.ERROR = createTupleFromCfg(responseCfg.getConfig("error"));
    }

    private Tuple<String, String> createTupleFromCfg(final Config cfg) {
        return new Tuple<>(cfg.getString("exchange"), cfg.getString("routing_key"));
    }


    private Message postProcessMessage(final Message source, final String message) {
        final MessageProperties messageProperties = source.getMessageProperties();
        messageProperties.setAppId(appId);
        messageProperties.setCorrelationId(messageProperties.getCorrelationIdString().getBytes(StandardCharsets.UTF_8));
        messageProperties.setTimestamp(new LocalDateTime().toDate());
        if (StringUtils.isNotEmpty(message)) {
            messageProperties.setHeader("message", message);
        }
        return source;
    }

    public void wait(final Message message, final Set<RbLaboratory> laboratories) {
        template.send(WAIT.left,
                      WAIT.right,
                      postProcessMessage(message,
                                         MessageFormat.format(MessageHolder.WAIT,
                                                              laboratories.stream().map(RbLaboratory::getName).collect(Collectors.toSet())
                                         )
                      )
        );
    }

    public void noLaboratoryAssigned(final Message message) {
        template.send(NOT_ASSIGNED.left, NOT_ASSIGNED.right, postProcessMessage(message, MessageHolder.NO_LABORATORY_ASSIGNED));
    }

    public void messageIsIncorrect(final Message message, final Set<String> errors) {
        template.send(ERROR.left, ERROR.right, postProcessMessage(message, MessageFormat.format(MessageHolder.CONTENT_INCORRECT, errors)));
    }

    public void unknownRoutingKey(final Message message, final Set<String> possibleKeys) {
        template.send(ERROR.left, ERROR.right, postProcessMessage(message,
                                                                  MessageFormat.format(MessageHolder.UNKNOWN_ROUTING_KEY,
                                                                                       message.getMessageProperties().getReceivedRoutingKey(),
                                                                                       possibleKeys
                                                                  )
        ));
    }

    public void unknownException(final Message message, final String description) {
        template.send(ERROR.left, ERROR.right, postProcessMessage(message, MessageFormat.format(MessageHolder.UNKNOWN_EXCEPTION, description)));
    }

    public void sent(final Message message) {
        template.send(SENT.left, SENT.right, postProcessMessage(message, MessageHolder.SENDED));
    }
}
