package ru.bars_open.medvtr.mq.util;

import com.typesafe.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 13.01.2017, 20:15 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class ConfigurationHolder {
    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    private final Config object;

    public ConfigurationHolder(final Config baseCfg) {
        final Config defaultCfg = baseCfg.hasPath("default") ? baseCfg.getConfig("default") : ConfigFactory.empty();
        final Config overrideCfg = baseCfg.hasPath("override") ? baseCfg.getConfig("override") : ConfigFactory.empty();
        final Config remoteCfg = baseCfg.hasPath("configService") ? loadConfigFromConfigService(baseCfg.getConfig("configService")) : ConfigFactory
                .empty();
        //http://stackoverflow.com/questions/35779151/merging-multiple-typesafe-config-files-and-resolving-only-after-they-are-all-mer
        this.object = overrideCfg.withFallback(remoteCfg).withFallback(defaultCfg).resolve();
        if (object.isEmpty()) {
            throw new IllegalStateException("Parsed Configuration is not valid, cause empty!");
        }
    }

    private Config loadConfigFromConfigService(final Config cfg) {
        final URL configURL = parseConfigServiceURL(cfg);
        if (configURL == null) {
            log.error("Cant load config from ConfigService cause configURL is not constructed properly!");
        } else {
            log.info("Try loading from ConfigService[{}]", configURL);
            try {
                final Config result = ConfigFactory.parseURL(configURL);
                if(result != null && HttpURLConnection.HTTP_OK == result.getInt("meta.code")){
                   return result.getConfig("result");
                }
            } catch (final Exception e) {
                log.warn("Cant load config from \'{}\'. Cause : {}", configURL, e.getMessage());
            }
        }
        return ConfigFactory.empty();
    }

    private URL parseConfigServiceURL(final Config cfg) {
        try {
            String path = cfg.getString("resourceName");
            if(!path.startsWith("/")){
                path = "/"+ path;
            }
            return new URL(cfg.getString("protocol"), cfg.getString("host"), cfg.getInt("port"), path);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "ConfigurationHolder[\n" + object.root().render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false)) + ']';
    }

    public ConfigObject root() {
        return object.root();
    }

    public boolean hasPath(final String path) {
        return object.hasPath(path);
    }


    public boolean hasPathOrNull(final String path) {
        return object.hasPathOrNull(path);
    }


    public boolean isEmpty() {
        return object.isEmpty();
    }


    public Set<Map.Entry<String, ConfigValue>> entrySet() {
        return object.entrySet();
    }


    public boolean getIsNull(final String path) {
        return object.getIsNull(path);
    }


    public boolean getBoolean(final String path) {
        return object.getBoolean(path);
    }


    public Number getNumber(final String path) {
        return object.getNumber(path);
    }


    public int getInt(final String path) {
        return object.getInt(path);
    }


    public long getLong(final String path) {
        return object.getLong(path);
    }


    public double getDouble(final String path) {
        return object.getDouble(path);
    }


    public String getString(final String path) {
        return object.getString(path);
    }


    public <T extends Enum<T>> T getEnum(final Class<T> enumClass, final String path) {
        return object.getEnum(enumClass, path);
    }


    public ConfigObject getObject(final String path) {
        return object.getObject(path);
    }


    public Config getConfig(final String path) {
        return object.getConfig(path);
    }


    public Object getAnyRef(final String path) {
        return object.getAnyRef(path);
    }


    public ConfigValue getValue(final String path) {
        return object.getValue(path);
    }


    public Long getBytes(final String path) {
        return object.getBytes(path);
    }


    public ConfigMemorySize getMemorySize(final String path) {
        return object.getMemorySize(path);
    }

    public ConfigList getList(final String path) {
        return object.getList(path);
    }


    public List<Boolean> getBooleanList(final String path) {
        return object.getBooleanList(path);
    }


    public List<Number> getNumberList(final String path) {
        return object.getNumberList(path);
    }


    public List<Integer> getIntList(final String path) {
        return object.getIntList(path);
    }


    public List<Long> getLongList(final String path) {
        return object.getLongList(path);
    }


    public List<Double> getDoubleList(final String path) {
        return object.getDoubleList(path);
    }


    public List<String> getStringList(final String path) {
        return object.getStringList(path);
    }


    public List<? extends ConfigObject> getObjectList(final String path) {
        return object.getObjectList(path);
    }

    public List<?> getAnyRefList(final String path) {
        return object.getAnyRefList(path);
    }


    public List<Long> getBytesList(final String path) {
        return object.getBytesList(path);
    }

    public Config withValue(final String path, final ConfigValue value) {
        return object.withValue(path, value);
    }
}
