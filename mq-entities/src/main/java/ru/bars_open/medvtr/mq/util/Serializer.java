package ru.bars_open.medvtr.mq.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Author: Upatov Egor <br>
 * Date: 22.12.2016, 18:16 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface Serializer {
    <T> T parse(byte[] content, Charset encoding, Class<T> clazz);

    default <T> T parse(byte[] content, Class<T> clazz) {
        return parse(content, StandardCharsets.UTF_8, clazz);
    }

    <T> byte[] serialize(T value, Charset encoding);
}
