package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy;

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

    // Настройки соединения с вебсервисом 1С
    public static final String WEBSERVICE_URL = "webservice.url";
    public static final String WEBSERVICE_NAME = "webservice.name";
    public static final String WEBSERVICE_NAMESPACE = "webservice.targetNamespace";

    // Настройки работы с AMQP
    public static final String AMQP_SERVER_HOST = "amqp.server.host";
    public static final String AMQP_SERVER_PORT = "amqp.server.port";
    public static final String AMQP_USERNAME = "amqp.user.username";
    public static final String AMQP_PASSWORD = "amqp.user.password";

    public static final String QUEUE_NAME = "amqp.queue";

    public static final String ROUTING_KEY_CREATE = "amqp.binding.create.routing_key";
    public static final String ROUTING_KEY_CLOSE = "amqp.binding.close.routing_key";

    public static final String ERROR_EXCHANGE="amqp.error.exchange";
    public static final String ERROR_ROUTING_KEY="amqp.error.routing_key";
    public static final String ERROR_DELAY = "amqp.error.delay";
    public static final String ERROR_REATTEMPTS = "amqp.error.retryAttempts";


}
