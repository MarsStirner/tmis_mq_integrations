package ru.bars_open.medvtr.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.bars_open.medvtr.configuration_loader.ConfigurationLoader;

import java.io.IOException;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 18:32 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class ConfigurationLoaderTest {

    private static final Logger log = LoggerFactory.getLogger("TEST");


    @Test(priority = 1, expectedExceptions = IllegalArgumentException.class)
    public void nullPath() throws IOException {
        log.info("Start test: nullPath");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration((String) null);
    }

    @Test(priority = 2, expectedExceptions = IllegalArgumentException.class)
    public void emptyPath() throws IOException {
        log.info("Start test: emptyPath");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("");
    }

    @Test(priority = 3, expectedExceptions = IllegalArgumentException.class)
    public void nonExistedConfig() throws IOException {
        log.info("Start test: nonExistedConfig");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("nonExistsConfigFile.json");
    }


    @Test(priority = 4, expectedExceptions = IllegalArgumentException.class)
    public void emptyConfig() throws IOException {
        log.info("Start test: emptyConfig");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("empty.json");
    }

    @Test(priority = 5, expectedExceptions = IllegalArgumentException.class)
    public void emptyJsonConfig() throws IOException {
        log.info("Start test: emptyJson");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("emptyJson.json");
    }

    @Test(priority = 6, expectedExceptions = IllegalArgumentException.class)
    public void nonJsonContent() throws IOException {
        log.info("Start test: notJson");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("notJson.txt");
    }

    @Test(priority = 7, expectedExceptions = IllegalArgumentException.class)
    public void jsonWithoutRequiredFields() throws IOException {
        log.info("Start test: jsonWithoutRequiredFields");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("jsonWithoutRequiredFields.json");
    }

    @Test(priority = 8, expectedExceptions = IllegalArgumentException.class)
    public void appNameOnly() throws IOException {
        log.info("Start test: appNameOnly");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("appNameOnly.json");
    }

    @Test(priority = 9, expectedExceptions = IllegalArgumentException.class)
    public void appNameWithOtherAttr() throws IOException {
        log.info("Start test: appNameWithOtherAttr");
        ConfigurationLoader obj = new ConfigurationLoader();
        obj.loadConfiguration("appNameWithOtherAttr.json");
    }

}
