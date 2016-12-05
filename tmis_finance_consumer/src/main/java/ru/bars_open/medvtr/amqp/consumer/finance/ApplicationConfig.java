package ru.bars_open.medvtr.amqp.consumer.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.bars_open.medvtr.amqp.consumer.finance.util.YamlReaderWrapper;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Author: Upatov Egor <br>
 * Date: 26.10.2016, 14:48 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
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

    @Bean(name = "yaml_properties_application")
    public Properties getApplicationYamlProperties() {
        return YamlReaderWrapper.getYamlPropertiesFromResource("application.yml");
    }

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(final ConfigManager configManager) throws IOException, TimeoutException {
        final ConnectionFactory result = new ConnectionFactory();
        result.setPassword(configManager.getValue(ConfigManager.AMQP_PASSWORD, ConnectionFactory.DEFAULT_PASS));
        result.setUsername(configManager.getValue(ConfigManager.AMQP_USERNAME, ConnectionFactory.DEFAULT_USER));
        result.setHost(configManager.getValue(ConfigManager.AMQP_SERVER_HOST, "http://www.bars-open.ru/medvtr/rabbitmq"));
        result.setPort(configManager.getValue(ConfigManager.AMQP_SERVER_PORT, ConnectionFactory.DEFAULT_AMQP_PORT, Integer.class));
        result.setAutomaticRecoveryEnabled(true);
        result.setNetworkRecoveryInterval(10000);

        final String clientUUID = configManager.getValue(ConfigManager.CONSUMER_UUID, UUID.randomUUID().toString()).toUpperCase();
        final Map<String, Object> clientProperties = result.getClientProperties();
        clientProperties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        clientProperties.put("clientUUID", clientUUID);
        clientProperties.put("ip-address", java.net.InetAddress.getLocalHost().toString());

        log.info("ConnectionFactory: '{}@{}:{}/{}'", result.getUsername(), result.getHost(), result.getPort(), result.getVirtualHost());
        return result;
    }

    @Bean(name = "connection")
    public Connection connection(final ConnectionFactory factory) throws IOException, TimeoutException {
        final Connection result = factory.newConnection("connection_tmis_finance_consumer");
        final Object clientUUID = result.getClientProperties().get("clientUUID");
        log.info("Connection [@{}][{}] {}", Integer.toHexString(result.hashCode()), clientUUID, result);
        return result;
    }

    @Bean(name = "channel")
    public Channel channel(final ConfigManager configManager, final Connection connection) throws IOException {
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

    @Bean(name="jsonMapper")
    public ObjectMapper jsonMapper(){
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        return mapper;
    }



}
