package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import com.typesafe.config.Config;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.mq.entities.base.Biomaterial;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.DeserializationFactory;
import ru.bars_open.medvtr.mq.util.Tuple;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 15:47 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository
public class ResponseSender {
    private static final Logger log = LoggerFactory.getLogger(ResponseSender.class);

    final Tuple<String, String> sendedCfg;
    final Tuple<String, String> errorCfg;
    private final String appId;

    public ResponseSender(final ConfigurationHolder cfg) {
        this.appId = cfg.getString("appId");
        final Config sendedCfg = cfg.getConfig("amqp.response.sended");
        this.sendedCfg = new Tuple<>(sendedCfg.getString("exchange"), sendedCfg.getString("routing_key"));
        final Config errorCfg = cfg.getConfig("amqp.response.error");
        this.errorCfg = new Tuple<>(errorCfg.getString("exchange"), errorCfg.getString("routing_key"));
        log.info("<init>: appId = '{}'\n SENDED={}\n ERROR={}", appId, sendedCfg, errorCfg);
    }


    @Autowired
    private RabbitTemplate template;

    public void noSuchBiomaterialType(final long tag, final String uuid, final Biomaterial messageBiomaterial) {
        template.send(
                errorCfg.left,
                errorCfg.right,
                createMessage(messageBiomaterial,
                              uuid,
                              StandardCharsets.UTF_8,
                              DeserializationFactory.CONTENT_TYPE_APPLICATION_JSON,
                              "TODO"
                )
        );
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
        propertiesBuilder.setTimestamp(new LocalDateTime().toDate());
        propertiesBuilder.setType(type);
        return new Message(body, propertiesBuilder.build());
    }
}
