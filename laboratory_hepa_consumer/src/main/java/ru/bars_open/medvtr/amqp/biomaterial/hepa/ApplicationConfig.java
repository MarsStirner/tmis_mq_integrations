package ru.bars_open.medvtr.amqp.biomaterial.hepa;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.typesafe.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
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
import java.util.concurrent.TimeoutException;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.ConfigurationKeys.*;

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

    @Bean("localConfig")
    public Config localConfig() {
        final Config result = ConfigFactory.parseResources("application.conf", ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF));
        log.info("Load local config from classpath: {}",
                 result.root().render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false))
        );
        return result;
    }

    @Bean("configurationHolder")
    @Scope("singleton")
    public ConfigurationHolder configurationHolder(@Qualifier("localConfig") Config localConfig) {
        final ConfigurationHolder result = new ConfigurationHolder(localConfig());
        log.info("Initialized: {}", result);
        return result;
    }

    @Bean("mqConnectionFactory")
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

    @Bean("messagePropertiesConverter")
    public MessagePropertiesConverter messagePropertiesConverter(){
        final DefaultMessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();
        messagePropertiesConverter.setCorrelationIdAsString(DefaultMessagePropertiesConverter.CorrelationIdPolicy.STRING);
        return messagePropertiesConverter;
    }

    @Bean("hospitalizationMessageListener")
    public SimpleMessageListenerContainer hospitalizationMessageListener(
            final ConnectionFactory mqConnectionFactory,
            final ConfigurationHolder cfg,
            final MessagePropertiesConverter messagePropertiesConverter,
            @Qualifier("consumer") final ChannelAwareMessageListener consumer
    ) {
        final SimpleMessageListenerContainer result = new SimpleMessageListenerContainer();
        result.setConnectionFactory(mqConnectionFactory);
        result.setAutoStartup(true);
        result.setAutoDeclare(false);
        result.setExclusive(false);
        result.setAcknowledgeMode(AcknowledgeMode.AUTO);
        result.setConsumerTagStrategy(queue -> cfg.getString(CONSUMER_TAG) + "_" + queue);
        result.setQueueNames(cfg.getString(REQUEST_SEND_QUEUE));
        result.setMessageListener(consumer);
        result.setMaxConcurrentConsumers(1);
        result.setTaskExecutor(new SimpleAsyncTaskExecutor("consumerThread_"));
        result.setMessagePropertiesConverter(messagePropertiesConverter);
        result.setDefaultRequeueRejected(true);
        log.info("{}", result);
        return result;
    }

    @Bean("amqpTemplate")
    public RabbitTemplate amqpTemplate(
            final ConnectionFactory mqConnectionFactory,
            final MessagePropertiesConverter messagePropertiesConverter
    ) {
        final RabbitTemplate result = new RabbitTemplate(mqConnectionFactory);
        result.setEncoding(StandardCharsets.UTF_8.name());
        result.setMessagePropertiesConverter(messagePropertiesConverter);
        return result;
    }
}
