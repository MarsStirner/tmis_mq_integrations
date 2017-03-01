package ru.bars_open.medvtr.mq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * Author: Upatov Egor <br>
 * Date: 22.12.2016, 18:15 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class JSONSerializer implements Serializer {

    private static JSONSerializer instance;

    private static final Logger log = LoggerFactory.getLogger(JSONSerializer.class);

    private final ObjectMapper mapper;

    private JSONSerializer() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //  mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static JSONSerializer getInstance() {
        if (instance == null) {
            instance = new JSONSerializer();
        }
        return instance;
    }


    @Override
    public <T> T parse(final byte[] content, Charset encoding, final Class<T> clazz) {
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8;
        }
        try {
            return mapper.readValue(new InputStreamReader(new ByteArrayInputStream(content), encoding), clazz);
        } catch (IOException e) {
            log.error("Cannot parse byte array to {}", clazz.getCanonicalName());
            return null;
        }
    }

    @Override
    public <T> byte[] serialize(final T value, final Charset encoding) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            log.error("Cannot parse {} to byte array", value.getClass());
            return null;
        }
    }
}
