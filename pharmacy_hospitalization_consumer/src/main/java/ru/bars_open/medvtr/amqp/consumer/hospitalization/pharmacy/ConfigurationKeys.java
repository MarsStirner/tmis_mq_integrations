package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy;

/**
 * Author: Upatov Egor <br>
 * Date: 13.01.2017, 20:07 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class ConfigurationKeys {
    // Внутренние настройки (не должны перетираться ЦСК)
    public static final String CONSUMER_TAG = "consumer.tag";
    public static final String CONSUMER_UUID = "consumer.uuid";

    // Натсройки коннекта к ЦСК (Центральной системе конфигурации
    public static final String APP_NAME = "appName";

    // Настройки соединения с вебсервисом 1С
    public static final String FINANCE_SERVICE_URL = "webservice.url";
    public static final String FINANCE_SERVICE_NAME = "webservice.name";
    public static final String FINANCE_SERVICE_NAMESPACE = "webservice.targetNamespace";

    // Настройки работы с AMQP
    public static final String AMQP_SERVER_HOST = "amqp.server.host";
    public static final String AMQP_SERVER_PORT = "amqp.server.port";
    public static final String AMQP_USERNAME = "amqp.user.username";
    public static final String AMQP_PASSWORD = "amqp.user.password";
}
