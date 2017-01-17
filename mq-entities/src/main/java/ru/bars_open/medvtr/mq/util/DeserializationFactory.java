package ru.bars_open.medvtr.mq.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Author: Upatov Egor <br>
 * Date: 22.12.2016, 18:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class DeserializationFactory {

    private static final Logger log = LoggerFactory.getLogger(DeserializationFactory.class);

    private static Serializer defaultSerializer = JSONSerializer.getInstance();


    public static <T> T parse(final byte[] content, final String protocol, final Class<T> clazz){
        return parse(content, protocol, (Charset) null, clazz);
    }

    public static <T> T parse(final byte[] content, final String protocol, final String encoding, final Class<T> clazz){
        return parse(content, protocol, Charset.forName(encoding), clazz);
    }

    public static <T> T parse(final byte[] content, final String protocol, final Charset encoding, final Class<T> clazz){
      return getSerializer(protocol).parse(content, encoding, clazz);
    }

    private static Serializer getSerializer(final String protocol) {
       switch(protocol){
           case "application/json": {
               return JSONSerializer.getInstance();
           }
           default: {
               log.warn("No matching serializer found for '{}' protocol. Try with default Serializer.", protocol);
               return defaultSerializer;
           }
       }
    }



    public static Charset getEncoding(final Logger log, final long logTag, final String contentEncoding) {
        if (StringUtils.isNotEmpty(contentEncoding)) {
            try {
                return Charset.forName(contentEncoding);
            } catch (UnsupportedCharsetException e) {
                log.warn("#{}: Encoding with name '{}' is not supported, will use UTF-8", logTag, contentEncoding);
                return StandardCharsets.UTF_8;
            }
        }
        log.warn("#{}: Encoding not set, will use UTF-8", logTag, contentEncoding);
        return StandardCharsets.UTF_8;
    }

    public static Charset getEncoding(final long logTag, final String contentEncoding) {
       return getEncoding(log, logTag, contentEncoding);
    }

    public static Charset getEncoding(final String contentEncoding) {
        return getEncoding(0, contentEncoding);
    }

}
