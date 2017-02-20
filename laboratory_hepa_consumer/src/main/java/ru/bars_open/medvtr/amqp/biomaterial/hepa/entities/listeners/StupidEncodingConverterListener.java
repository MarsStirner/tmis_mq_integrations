package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 18:54 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public abstract class StupidEncodingConverterListener<T> {
    private static final Logger log = LoggerFactory.getLogger(StupidEncodingConverterListener.class);

    private static final Charset KOI8_R = Charset.forName("koi8-r");
    private static final Charset CP_1252 = Charset.forName("cp1252");


    public abstract void convertToDb(T entity);


    public abstract void convertFromDb(T entity);

    public StupidEncodingConverterListener() {
        final String value = "abcdefg12345абвгдеж";
        log.info("Древнее зло пробудилось!\n {} = {} = {}", value, convertToDb(value), convertFromDb(convertToDb(value)));
    }

    public static String convertToDb(String value) {
        return value != null ? new String(value.getBytes(KOI8_R), CP_1252) : null;
    }

    public static String convertFromDb(String value) {
        return value != null ? new String(value.getBytes(CP_1252), KOI8_R) : null;
    }
}
