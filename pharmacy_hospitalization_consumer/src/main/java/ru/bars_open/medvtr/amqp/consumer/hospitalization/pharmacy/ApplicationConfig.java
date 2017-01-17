package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

import com.rabbitmq.client.*;
import com.typesafe.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 15:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Класс с конфигом (Java-based Spring config)
 */

@Configuration
@ComponentScan
public class ApplicationConfig {
    private static final Logger log = LoggerFactory.getLogger("ROOT");
    private static ApplicationContext context;

    /**
     * Инициализация приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        final String pid = ManagementFactory.getRuntimeMXBean().getName();
        long startTime = System.currentTimeMillis();
        log.info("PID {}: Start application with args: {}", pid.split("@")[0], args);
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        log.info("End initialization of application in {} seconds. Good luck!", (System.currentTimeMillis() - startTime) / 1000f);
    }

    @Bean("localConfig")
    public Config localConfig(){
        final Config result = ConfigFactory.parseResources("application.conf", ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF));
        log.info("Load local config from classpath: {}", result.root().render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false)));
        return result;
    }
    
    @Bean("configurationHolder")
    @Scope("singleton")
    public ConfigurationHolder configurationHolder(@Qualifier("localConfig") Config localConfig){
         final ConfigurationHolder result = new ConfigurationHolder(localConfig());
         log.info("Initialized: {}", result);
         return result;
    }

    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory(final ConfigurationHolder cfg) throws IOException, TimeoutException {
        final ConnectionFactory result = new ConnectionFactory();
        result.setPassword(cfg.getString(ConfigurationKeys.AMQP_PASSWORD));
        result.setUsername(cfg.getString(ConfigurationKeys.AMQP_USERNAME));
        result.setHost(cfg.getString(ConfigurationKeys.AMQP_SERVER_HOST));
        result.setPort(cfg.getInt(ConfigurationKeys.AMQP_SERVER_PORT));
        result.setAutomaticRecoveryEnabled(true);
        result.setNetworkRecoveryInterval(10000);

        final String clientUUID = cfg.getString(ConfigurationKeys.CONSUMER_UUID).toUpperCase();
        final Map<String, Object> clientProperties = result.getClientProperties();
        clientProperties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        clientProperties.put("clientUUID", clientUUID);
        clientProperties.put("ip-address", java.net.InetAddress.getLocalHost().toString());

        log.info("ConnectionFactory: '{}@{}:{}/{}'", result.getUsername(), result.getHost(), result.getPort(), result.getVirtualHost());
        return result;
    }

    @Bean("connection")
    public Connection connection(final ConnectionFactory factory) throws IOException, TimeoutException {
        final Connection result = factory.newConnection("connection_tmis_finance_consumer");
        final Object clientUUID = result.getClientProperties().get("clientUUID");
        log.info("Connection [@{}][{}] {}", Integer.toHexString(result.hashCode()), clientUUID, result);
        return result;
    }

    @Bean("channel")
    public Channel channel(final Connection connection) throws IOException {
        final Channel result = connection.createChannel();
        result.addShutdownListener(cause -> log.error("Channel [@{}] shutdown cause '{}'", Integer.toHexString(result.hashCode()), cause.toString()));
        // We also can theoretically use non-recover channel & connection
        if (Recoverable.class.isAssignableFrom(result.getClass())) {
            ((Recoverable) result).addRecoveryListener(new RecoveryListener() {
                @Override
                public void handleRecovery(final Recoverable recoverable) {
                    log.warn("Channel [@{}] recovered!", Integer.toHexString(result.hashCode()));
                }

                @Override
                public void handleRecoveryStarted(final Recoverable recoverable) {
                    log.warn("Channel [@{}] recovery started!", Integer.toHexString(result.hashCode()));
                }
            });
        }
        result.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            log.debug("Channel return: {}, {}, {}, {}, {}, {}", replyCode, replyText, exchange, routingKey, properties, body);
        });
        log.info("Channel [@{}] {}", Integer.toHexString(result.hashCode()), result.getChannelNumber());
        return result;
    }





}
