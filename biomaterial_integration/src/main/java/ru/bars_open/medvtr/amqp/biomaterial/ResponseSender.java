package ru.bars_open.medvtr.amqp.biomaterial;

import com.typesafe.config.Config;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.util.MessageHolder;
import ru.bars_open.medvtr.mq.entities.message.BiologicalMaterialMessage;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.Tuple;

import java.nio.charset.Charset;
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
    private final Tuple<String, String> SENDED;
    private final Tuple<String, String> WAIT;


    @Autowired
    private RabbitTemplate template;


    @Autowired
    public ResponseSender(final ConfigurationHolder cfg) {
        this.appId = cfg.getAppId();
        final Config responseCfg = cfg.getConfig("amqp.response");
        this.NOT_ASSIGNED = createTupleFromCfg(responseCfg.getConfig("not_assigned"));
        this.SENDED = createTupleFromCfg(responseCfg.getConfig("sended"));
        this.WAIT = createTupleFromCfg(responseCfg.getConfig("wait"));
    }

    private Tuple<String, String> createTupleFromCfg(final Config cfg) {
        return new Tuple<>(cfg.getString("exchange"), cfg.getString("routing_key"));
    }


    private Message createMessage(
            final Object value, final String uuid, final Charset encoding, final String contentType, final String type
    ) {
        final byte[] body = DeserializationFactory.serialize(value, contentType, encoding);
        final MessagePropertiesBuilder propertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertiesBuilder.setAppId(appId);
        propertiesBuilder.setContentEncoding(encoding.name());
        propertiesBuilder.setRedelivered(false);
        propertiesBuilder.setContentLength(body.length);
        propertiesBuilder.setContentType(contentType);

        propertiesBuilder.setCorrelationIdString(uuid);
        propertiesBuilder.setCorrelationId(uuid.getBytes(StandardCharsets.UTF_8));

        propertiesBuilder.setTimestamp(new LocalDateTime().toDate());
        propertiesBuilder.setType(type);
        return new Message(body, propertiesBuilder.build());
    }

    public void noLaboratoryAssigned(final String uuid, final BiologicalMaterialMessage parsed) {
        parsed.getAdditionalProperties().put("message", MessageHolder.NO_LABORATORY_ASSIGNED);
        template.send(NOT_ASSIGNED.left, NOT_ASSIGNED.right, createMessage(
                parsed,
                uuid,
                StandardCharsets.UTF_8,
                DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON,
                BiologicalMaterialMessage.class.getSimpleName()
        ));
    }

    public void wait(final String uuid, final BiologicalMaterialMessage parsed, final Set<RbLaboratory> laboratories) {
        parsed.getAdditionalProperties().put("message",
                                             MessageFormat.format(
                                                     MessageHolder.WAIT,
                                                     laboratories.stream().map(RbLaboratory::getName).collect(Collectors.toSet())
                                             )
        );
        template.send(WAIT.left, WAIT.right, createMessage(
                parsed,
                uuid,
                StandardCharsets.UTF_8,
                DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON,
                BiologicalMaterialMessage.class.getSimpleName()
        ));
    }
}
