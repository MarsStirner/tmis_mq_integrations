package ru.bars_open.medvtr.configuration_loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 16:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Загрузчик конфигурации из файла
 */
public class ConfigurationLoader {
    protected static final Logger log = LoggerFactory.getLogger("CONFIG");

    private static final String KEY_RESOURCE_TYPE = "resourceType";
    public static final String APP_NAME = "appName";
    public static final String LOAD_ORDER = "configurationLoadOrder";
    private String appName;

    protected final Map<String, String> parameterMap = new TreeMap<>();

    private void loadConfiguration(final InputStream is) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root;
        try {
            root = objectMapper.readTree(is);
        } catch (IOException e) {
            log.error("Not valid json content", e);
            throw new IllegalArgumentException("Not valid json content", e);
        }
        final JsonNode appName = root.get(APP_NAME);
        if (appName != null && StringUtils.isNotEmpty(appName.textValue())) {
            setAppName(appName.textValue());
            log.debug("Defined '{}'='{}'", APP_NAME, getAppName());
        } else {
            log.error("No '{}' defined in configuration", APP_NAME);
            throw new IllegalArgumentException("No '" + APP_NAME + "' defined in configuration");

        }
        final JsonNode loadOrder = root.get(LOAD_ORDER);
        if (loadOrder == null) {
            log.error("No '{}' defined in configuration", LOAD_ORDER);
            throw new IllegalArgumentException("No '" + LOAD_ORDER + "' defined in configuration");
        }
        final List<ConfigurationSource> configurationSources = new ArrayList<>(loadOrder.size());
        if (loadOrder.isArray()) {
            for (final JsonNode configOrderItem : loadOrder) {
                final ConfigurationSource cfgSource = parseConfigurationSource(configOrderItem);
                if (cfgSource != null && cfgSource.isValid()) {
                    configurationSources.add(cfgSource);
                } else {
                    log.warn("ConfigurationItem [{}] is null or not valid", configOrderItem.toString());
                }
            }
        }
    }

    private ConfigurationSource parseConfigurationSource(final JsonNode node) {
        switch (node.get(KEY_RESOURCE_TYPE).textValue()) {
            case "remote": {
                return new RemoteConfigurationSource(node, appName);
            }
            default: {
                return null;
            }
        }
    }

    public String getAppName() {
        return appName;
    }

    private void setAppName(final String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        final TableStringBuilder<Map.Entry<String, String>> sb = new TableStringBuilder<>();
        sb.addColumn("KEY", Map.Entry::getKey);
        sb.addColumn("VALUE", Map.Entry::getValue);
        return "APP: " + getAppName() + ":\n" + sb.createString(parameterMap.entrySet());
    }

    public void loadConfiguration(final String path) throws IOException {
        log.info("Start load configuration from '{}'", path);
        if (StringUtils.isNotEmpty(path)) {
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            if(inputStream != null){
                log.debug("Resource['{}'] exists", path);
                loadConfiguration(inputStream);
                log.info("Finish load configuration from '{}'", path);
            }
            log.error("Resource['{}'] not exists!", path);
            throw new IllegalArgumentException("Resource['"+path+"'] not exists!");
        } else {
            log.error("Null argument passed as path to configuration resource");
            throw new IllegalArgumentException("Null argument passed as path to configuration resource");
        }
    }
}
