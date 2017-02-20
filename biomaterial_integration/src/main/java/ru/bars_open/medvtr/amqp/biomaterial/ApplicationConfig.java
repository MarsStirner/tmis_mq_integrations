package ru.bars_open.medvtr.amqp.biomaterial;


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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.ErrorHandler;
import ru.bars_open.medvtr.amqp.biomaterial.util.LoggingPostProcessor;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static ru.bars_open.medvtr.amqp.biomaterial.ConfigurationKeys.*;

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

    @Bean("configurationHolder")
    public ConfigurationHolder configurationHolder() {
        final Config localCfg = ConfigFactory.parseResources("application.conf", ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF));
        final ConfigurationHolder result = new ConfigurationHolder(localCfg);
        log.info("Initialized: {}", result);
        return result;
    }

    @Bean("amqpConnectionFactory")
    public ConnectionFactory amqpConnectionFactory(final ConfigurationHolder config) throws UnknownHostException {
        final CachingConnectionFactory result = new CachingConnectionFactory(config.getString(AMQP_SERVER_HOST), config.getInt(AMQP_SERVER_PORT));
        result.setPassword(config.getString(AMQP_PASSWORD));
        result.setUsername(config.getString(AMQP_USERNAME));
        result.setApplicationContext(context);
        result.setConnectionThreadFactory(new CustomizableThreadFactory("rabbitmq_thread_"));

        final com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = result.getRabbitConnectionFactory();
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(10000);

        final String clientUUID = config.getString(CONSUMER_UUID).toUpperCase();
        final Map<String, Object> clientProperties = rabbitConnectionFactory.getClientProperties();
        clientProperties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        clientProperties.put("clientUUID", clientUUID);
        clientProperties.put("ip-address", java.net.InetAddress.getLocalHost().toString());
        log.info("ConnectionFactory: '{}@{}:{}/{}'", result.getUsername(), result.getHost(), result.getPort(), result.getVirtualHost());
        return result;
    }

    @Bean("amqpTemplate")
    public RabbitTemplate amqpTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate result = new RabbitTemplate(connectionFactory);
        result.setEncoding(StandardCharsets.UTF_8.name());
        return result;
    }

    @Bean("biomaterialMessageListener")
    public SimpleMessageListenerContainer biomaterialMessageListener(
            final ConnectionFactory connectionFactory,
            final ConfigurationHolder config,
            @Qualifier("consumer") final ChannelAwareMessageListener consumer,
            @Qualifier("errorHandler") final ErrorHandler errorHandler,
            @Qualifier("amqpMessagePostProcessor") final LoggingPostProcessor postProcessor
    ) {
        final SimpleMessageListenerContainer result = new SimpleMessageListenerContainer(connectionFactory);
        result.setMessageListener(consumer);
        result.setQueueNames(config.getString(REQUEST_SEND_QUEUE));
        result.setAcknowledgeMode(AcknowledgeMode.AUTO);
        result.setAutoStartup(true);
        result.setAutoDeclare(false);
        result.setExclusive(false);
        result.setConcurrentConsumers(1);
        result.setPrefetchCount(3);
        result.setMaxConcurrentConsumers(3);

        result.setErrorHandler(errorHandler);
        result.setAfterReceivePostProcessors(postProcessor);
        result.setDefaultRequeueRejected(true);

        result.setTaskExecutor(new SimpleAsyncTaskExecutor("consumerThread_"));
        result.setConsumerTagStrategy(queue -> config.getString(CONSUMER_TAG) + "_" + queue);

        log.info("{}", result);
        return result;
    }




}
