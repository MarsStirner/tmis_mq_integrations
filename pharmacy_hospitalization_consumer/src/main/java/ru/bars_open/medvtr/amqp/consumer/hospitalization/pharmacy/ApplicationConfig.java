package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.typesafe.config.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.ConfigurationKeys.*;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 15:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Класс с конфигом (Java-based Spring config)
 */

@Configuration
@ComponentScan
public class ApplicationConfig {
    private static final Logger log = LoggerFactory.getLogger("CONFIG");
    private static ApplicationContext context;

    /**
     * Инициализация приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        final String pid = ManagementFactory.getRuntimeMXBean().getName();
        long startTime = System.currentTimeMillis();
        // assume SLF4J is bound to logback in the current environment
        final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
        log.info("####################################################################################################");
        log.info("PID {}: Start application with args: {}", pid.split("@")[0], args);
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        log.info("End initialization of application in {} seconds. Good luck!", (System.currentTimeMillis() - startTime) / 1000f);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.error("PID {}: STOP APPLICATION", ManagementFactory.getRuntimeMXBean().getName());
            log.info("####################################################################################################");
        }));
    }

    @Bean(name = "localConfig")
    public Config localConfig() {
        final Config result = ConfigFactory.parseResources("application.conf", ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF));
        log.info("Load local config from classpath: {}",
                 result.root().render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false))
        );
        return result;
    }

    @Bean(name = "configurationHolder")
    @Scope("singleton")
    public ConfigurationHolder configurationHolder(@Qualifier("localConfig") Config localConfig) {
        final ConfigurationHolder result = new ConfigurationHolder(localConfig());
        log.info("Initialized: {}", result);
        return result;
    }

    @Bean(name = "mqConnectionFactory")
    public ConnectionFactory mqConnectionFactory(final ConfigurationHolder cfg) throws IOException, TimeoutException {
        final CachingConnectionFactory result = new CachingConnectionFactory();
        result.setPassword(cfg.getString(AMQP_PASSWORD));
        result.setUsername(cfg.getString(AMQP_USERNAME));
        result.setHost(cfg.getString(AMQP_SERVER_HOST));
        result.setPort(cfg.getInt(AMQP_SERVER_PORT));
        result.setApplicationContext(context);
        result.setConnectionThreadFactory(new CustomizableThreadFactory("rabbitmq_thread_"));
        result.setPublisherConfirms(true);

        final com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = result.getRabbitConnectionFactory();
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(10000);

        final String clientUUID = cfg.getString(CONSUMER_UUID).toUpperCase();
        final Map<String, Object> clientProperties = rabbitConnectionFactory.getClientProperties();
        clientProperties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        clientProperties.put("clientUUID", clientUUID);
        clientProperties.put("ip-address", java.net.InetAddress.getLocalHost().toString());
        log.info("ConnectionFactory: '{}@{}:{}/{}'", result.getUsername(), result.getHost(), result.getPort(), result.getVirtualHost());
        return result;
    }

    @Bean(name = "hospitalizationMessageListener")
    public SimpleMessageListenerContainer hospitalizationMessageListener(
            final ConnectionFactory mqConnectionFactory, final ConfigurationHolder cfg, final Consumer consumer
    ) {
        final SimpleMessageListenerContainer result = new SimpleMessageListenerContainer();
        result.setConnectionFactory(mqConnectionFactory);
        result.setAutoStartup(true);
        result.setAutoDeclare(false);
        result.setExclusive(false);
        result.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        result.setConsumerTagStrategy(queue -> cfg.getString(CONSUMER_TAG) + "_" + queue);
        result.setQueueNames(cfg.getString(QUEUE_NAME));
        result.setMessageListener(consumer);
        result.setMaxConcurrentConsumers(1);
        result.setTaskExecutor(new SimpleAsyncTaskExecutor("consumerThread_"));
        final DefaultMessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();
        messagePropertiesConverter.setCorrelationIdAsString(DefaultMessagePropertiesConverter.CorrelationIdPolicy.STRING);
        result.setMessagePropertiesConverter(messagePropertiesConverter);
        result.setAfterReceivePostProcessors((MessagePostProcessor) message -> {
            final MessageProperties props = message.getMessageProperties();
            if(StringUtils.isEmpty(props.getCorrelationIdString())){
                props.setCorrelationIdString(UUID.randomUUID().toString());
            }
            return message;
        });
        result.setDefaultRequeueRejected(true);
        log.info("{}", result);
        return result;
    }

    @Bean(name = "amqpTemplate")
    public RabbitTemplate amqpTemplate(final ConnectionFactory mqConnectionFactory, final ConfigurationHolder cfg) {
        final RabbitTemplate result = new RabbitTemplate(mqConnectionFactory);
        result.setEncoding(StandardCharsets.UTF_8.name());
        return result;
    }


//
//    @Bean("connection")
//    public Connection connection(final ConnectionFactory factory) throws IOException, TimeoutException {
//        final Connection result = factory.newConnection("connection_tmis_finance_consumer");
//        final Object clientUUID = result.getClientProperties().get("clientUUID");
//        log.info("Connection [@{}][{}] {}", Integer.toHexString(result.hashCode()), clientUUID, result);
//        return result;
//    }
//
//    @Bean("channel")
//    public Channel channel(final Connection connection) throws IOException {
//        final Channel result = connection.createChannel();
//        result.addShutdownListener(cause -> log.error("Channel [@{}] shutdown cause '{}'", Integer.toHexString(result.hashCode()), cause.toString()));
//        // We also can theoretically use non-recover channel & connection
//        if (Recoverable.class.isAssignableFrom(result.getClass())) {
//            ((Recoverable) result).addRecoveryListener(new RecoveryListener() {
//                @Override
//                public void handleRecovery(final Recoverable recoverable) {
//                    log.warn("Channel [@{}] recovered!", Integer.toHexString(result.hashCode()));
//                }
//
//                @Override
//                public void handleRecoveryStarted(final Recoverable recoverable) {
//                    log.warn("Channel [@{}] recovery started!", Integer.toHexString(result.hashCode()));
//                }
//            });
//        }
//        result.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
//            log.debug("Channel return: {}, {}, {}, {}, {}, {}", replyCode, replyText, exchange, routingKey, properties, body);
//        });
//        log.info("Channel [@{}] {}", Integer.toHexString(result.hashCode()), result.getChannelNumber());
//        return result;
//    }


}
