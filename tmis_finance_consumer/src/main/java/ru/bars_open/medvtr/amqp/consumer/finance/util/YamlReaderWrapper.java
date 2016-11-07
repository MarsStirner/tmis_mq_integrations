package ru.bars_open.medvtr.amqp.consumer.finance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * Author: Upatov Egor <br>
 * Date: 01.11.2016, 17:27 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class YamlReaderWrapper {

    private static final Logger log = LoggerFactory.getLogger("YAML_READER");
    /**
     * Read yaml properties file and return java.util.Properties (or empty properties object if fail)
     * @param resource path to yaml file with properties
     * @return java.util.Properties parsed from yaml file
     */
    public static Properties getYamlPropertiesFromResource(final String resource) {
        log.info("Read YAML resource[{}]", resource);
        try {
            final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(resource));
            final Properties result = yaml.getObject();
            log.info("YAML resource[{}] properties read: {}", resource, result.size());
            if(log.isDebugEnabled()){
                result.forEach((key, value) -> log.debug("{} = \'{}\'", key, value));
            }
            return result;
        } catch (final Exception e) {
            log.error("Cannot read properties from YAML resource[{}]. Return empty object.", resource, e);
            return new Properties();
        }
    }
}
