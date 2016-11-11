package ru.bars_open.medvtr.amqp.consumer.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
    public static final String PROP_KEY_APP_NAME = "APP_NAME";

    public static final String PROP_KEY_CONFIG_SERVICE_BASE_URL = "CONFIG_SERVICE_BASE_URL";
    public static final String PROP_KEY_EXCHANGE_NAME = "EXCHANGE_NAME";

    public static final String PROP_KEY_AMQP_HOST = "amqp.host";
    public static final String PROP_KEY_AMQP_PORT = "amqp.port";
    public static final String PROP_KEY_AMQP_USER = "amqp.user";
    public static final String PROP_KEY_AMQP_PASSWORD = "amqp.password";

    public static final String PROP_KEY_AMQP_CONSUMER_QUEUE_NAME = "amqp.consumer.queueName";
    public static final String PROP_KEY_AMQP_CONSUMER_TAG = "amqp.consumer.tag";
    public static final String PROP_KEY_AMQP_CONSUMER_UUID = "amqp.consumer.uuid";
    public static final String PROP_KEY_AMQP_CONSUMER_PREFETCH_COUNT = "amqp.consumer.prefetchCount";

    public static final String PROP_KEY_FINANCE_SERVICE_URL = "financeWebservice.url";
    public static final String PROP_KEY_FINANCE_SERVICE_NAME = "financeWebservice.name";
    public static final String PROP_KEY_FINANCE_SERVICE_NAMESPACE = "financeWebservice.targetNamespace";

    private static final Logger log = LoggerFactory.getLogger("CONFIG");



    private Map<String, String> parameterMap = new TreeMap<>();

    public ConfigManager(
            @Qualifier("yaml_properties_application")
            final Properties props
    ) {
        for (final String name : props.stringPropertyNames()) {
            parameterMap.put(name, props.getProperty(name));
        }
        final String baseUrl = getValue(PROP_KEY_CONFIG_SERVICE_BASE_URL, "http://www.bars-open.config-service.ru");
        final String appName = getValue(PROP_KEY_APP_NAME, "TMIS_FINANCE_CONSUMER");
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


    private boolean loadConfigFromRemoteUrl(final String url) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            final ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (log.isDebugEnabled()) {
                log.debug("RAW Response from CCS: {}", rawResponse);
            }
            return true;
        } catch (final Exception e) {
            log.warn("Cant load config from \'{}\'. Cause : {}", url, e.getMessage());
            return false;
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
