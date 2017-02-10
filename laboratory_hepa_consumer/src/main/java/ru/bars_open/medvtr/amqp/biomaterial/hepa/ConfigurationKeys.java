package ru.bars_open.medvtr.amqp.biomaterial.hepa;

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

    // Настройки работы с AMQP
    public static final String AMQP_SERVER_HOST = "amqp.server.host";
    public static final String AMQP_SERVER_PORT = "amqp.server.port";
    public static final String AMQP_USERNAME = "amqp.user.username";
    public static final String AMQP_PASSWORD = "amqp.user.password";

    //Откуда брать сообщения
    public static final String REQUEST_SEND_QUEUE = "amqp.request.send.queue";
    public static final String REQUEST_SEND_ROUTING_KEY = "amqp.request.send.routing_key";


    //Куда отвечать на сообщения
    public static final String RESPONSE_ROUTING_KEY_NO_RESEARCH_TYPE = "amqp.response.no_research_type.routing_key";
    public static final String RESPONSE_EXCHANGE_NO_RESEARCH_TYPE = "amqp.response.no_research_type.exchange";

    public static final String RESPONSE_ROUTING_KEY_NO_LABORATORY = "amqp.response.no_laboratory.routing_key";
    public static final String RESPONSE_EXCHANGE_NO_LABORATORY = "amqp.response.no_laboratory.exchange";

    public static final String RESPONSE_ROUTING_KEY_WAIT = "amqp.response.wait.routing_key";
    public static final String RESPONSE_EXCHANGE_WAIT = "amqp.response.wait.exchange";

    public static final String RESPONSE_ROUTING_KEY_SENDED = "amqp.response.sended.routing_key";
    public static final String RESPONSE_EXCHANGE_SENDED = "amqp.response.sended.exchange";

}
