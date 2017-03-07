package ru.bars_open.medvtr.mq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


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
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[[ ]['T']HH:mm[:ss][XXX]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        mapper.registerModule(javaTimeModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
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
