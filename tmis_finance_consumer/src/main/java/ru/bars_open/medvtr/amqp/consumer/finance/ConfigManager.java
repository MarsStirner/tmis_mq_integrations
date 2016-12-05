package ru.bars_open.medvtr.amqp.consumer.finance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.bars_open.medvtr.amqp.consumer.finance.util.ObjectConverter;
import ru.bars_open.medvtr.amqp.consumer.finance.util.TableStringBuilder;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Author: Upatov Egor <br>
 * Date: 26.10.2016, 15:33 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Bean, which stored all config parameters
 */
@Component("ConfigManager")
@Scope("singleton")
public class ConfigManager {
    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    // Внутренние настройки (не должны перетираться ЦСК)
    public static final String CONSUMER_TAG = "consumer.tag";
    public static final String CONSUMER_UUID = "consumer.uuid";

    // Натсройки коннекта к ЦСК (Центральной системе конфигурации
    public static final String APP_NAME = "APP_NAME";
    public static final String CONFIG_URL = "CONFIG_URL";

    // Настройки соединения с вебсервисом 1С
    public static final String FINANCE_SERVICE_URL = "financeWebservice.url";
    public static final String FINANCE_SERVICE_NAME = "financeWebservice.name";
    public static final String FINANCE_SERVICE_NAMESPACE = "financeWebservice.targetNamespace";

    // Настройки работы с AMQP
    public static final String AMQP_SERVER_HOST = "amqp.server.host";
    public static final String AMQP_SERVER_PORT = "amqp.server.port";
    public static final String AMQP_USERNAME="amqp.user.username";
    public static final String AMQP_PASSWORD="amqp.user.password";

    public static final String QUEUE_REFUND="amqp.queue.refund";
    public static final String QUEUE_DELETED="amqp.queue.deleted";
    public static final String QUEUE_CREATED="amqp.queue.created";

    public static final String ERROR_QUEUE="amqp.error.queue";
    public static final String ERROR_EXCHANGE="amqp.error.exchange";
    public static final String ERROR_ROUTING_KEY="amqp.error.routing_key";

    private Map<String, String> parameterMap = new TreeMap<>();

    public ConfigManager(@Qualifier("yaml_properties_application") final Properties props) {

        for (final String name : props.stringPropertyNames()) {
            parameterMap.put(name, props.getProperty(name));
        }
        final String baseUrl = getValue(CONFIG_URL, "http://www.bars-open.config-service.ru");
        final String appName = getValue(APP_NAME, "mq_integration_finance");
        loadConfigFromRemoteUrl(baseUrl + "/" + appName);
        log.info("Initialized: {}", this);
    }

    public String getValue(final String key, final String defaultValue) {
        final String value = parameterMap.get(key);
        return value != null ? value : defaultValue;
    }

    public String getValue(final String key) {
        return getValue(key, (String) null);
    }

    public <T> T getValue(final String key, Class<T> clazz) {
       return getValue(key, null, clazz);
    }

    public <T> T getValue(final String key, T defaultValue, Class<T> clazz) {
        final String value = getValue(key);
        return value == null ? defaultValue : ObjectConverter.convert(value, clazz);
    }


    private void loadConfigFromRemoteUrl(final String url) {
        final RestTemplate restTemplate = new RestTemplate();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.debug("RAW Response from CCS: {}", rawResponse);
            if(!StringUtils.isEmpty(rawResponse)){
                final JsonNode root = objectMapper.readTree(rawResponse.getBody());
                final JsonNode result = root.path("result");
                if(result.isMissingNode()){
                    log.warn("Cant find 'result' JSON Node");
                    return;
                }
                loadConfigFromJson(result);
            }
        } catch (final Exception e) {
            log.warn("Cant load config from \'{}\'. Cause : {}", url, e.getMessage());
        }
    }

    private void loadConfigFromJson(final JsonNode root) {
        final JsonNode financeWebservice = root.get("financeWebservice");
        if(financeWebservice != null){
            parameterMap.put(FINANCE_SERVICE_URL, financeWebservice.path("url").textValue());
            parameterMap.put(FINANCE_SERVICE_NAME, financeWebservice.path("name").textValue());
            parameterMap.put(FINANCE_SERVICE_NAMESPACE, financeWebservice.path("targetNamespace").textValue());
        }
        final JsonNode amqp = root.get("amqp");
        if(amqp != null){
            final JsonNode server = amqp.get("server");
            if(server != null){
                parameterMap.put(AMQP_SERVER_HOST, server.path("host").textValue());
                parameterMap.put(AMQP_SERVER_PORT, server.path("port").textValue());
            }
            final JsonNode user = amqp.get("user");
            if(user != null){
                parameterMap.put(AMQP_USERNAME, user.path("username").textValue());
                parameterMap.put(AMQP_PASSWORD, user.path("password").textValue());
            }
            parameterMap.put(QUEUE_REFUND, amqp.path("queue.refund").textValue());
            parameterMap.put(QUEUE_DELETED, amqp.path("queue.deleted").textValue());
            parameterMap.put(QUEUE_CREATED, amqp.path("queue.created").textValue());
            final JsonNode error = amqp.get("error");
            if(error != null){
                parameterMap.put(ERROR_EXCHANGE, error.path("exchange").textValue());
                parameterMap.put(ERROR_ROUTING_KEY, error.path("routing_key").textValue());
                parameterMap.put(ERROR_QUEUE, error.path("queue").textValue());
            }
        }
    }

    @Override
    public String toString() {
        final TableStringBuilder<Map.Entry<String, String>> sb = new TableStringBuilder<>();
        sb.addColumn("KEY", Map.Entry::getKey);
        sb.addColumn("VALUE", Map.Entry::getValue);
        return "ConfigManager:\n" + sb.createString(parameterMap.entrySet());
    }

}
