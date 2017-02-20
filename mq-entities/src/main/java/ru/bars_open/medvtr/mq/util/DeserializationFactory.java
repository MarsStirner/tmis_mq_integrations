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
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private static final Logger log = LoggerFactory.getLogger(DeserializationFactory.class);

    private static Serializer defaultSerializer = JSONSerializer.getInstance();


    public static <T> T parse(final byte[] content, final String contentType, final Class<T> clazz){
        return parse(content, contentType, (Charset) null, clazz);
    }

    public static <T> T parse(final byte[] content, final String contentType, final String encoding, final Class<T> clazz){
        return parse(content, contentType, getEncoding(encoding), clazz);
    }

    public static <T> T parse(final byte[] content, final String contentType, final Charset encoding, final Class<T> clazz){
      return getSerializer(contentType).parse(content, encoding, clazz);
    }

    public static <T> byte[] serialize(final T value, final String contentType, final Charset encoding){
        return getSerializer(contentType).serialize(value, encoding);
    }

    private static Serializer getSerializer(final String contentType) {
        if(StringUtils.isEmpty(contentType)){
            log.warn("No matching serializer found for '{}' contentType. Try with default Serializer.", contentType);
            return defaultSerializer;
        }
       switch(contentType){
           case CONTENT_TYPE_APPLICATION_JSON: {
               return JSONSerializer.getInstance();
           }
           default: {
               log.warn("No matching serializer found for '{}' contentType. Try with default Serializer.", contentType);
               return defaultSerializer;
           }
       }
    }



    public static Charset getEncoding(final Logger log, final String contentEncoding) {
        if (StringUtils.isNotEmpty(contentEncoding)) {
            try {
                return Charset.forName(contentEncoding);
            } catch (UnsupportedCharsetException e) {
                log.warn("Encoding with name '{}' is not supported, will use UTF-8", contentEncoding);
                return StandardCharsets.UTF_8;
            }
        }
        log.warn("Encoding not set, will use UTF-8", contentEncoding);
        return StandardCharsets.UTF_8;
    }

    public static Charset getEncoding(final String contentEncoding) {
       return getEncoding(log, contentEncoding);
    }   
}
