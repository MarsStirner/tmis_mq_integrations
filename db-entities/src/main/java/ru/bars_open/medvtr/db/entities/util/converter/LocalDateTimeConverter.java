package ru.bars_open.medvtr.db.entities.util.converter;

import org.joda.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime ldt) {
        return ldt == null ? null : new Timestamp(ldt.toDateTime().getMillis());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp ts) {
       return ts == null ? null : new LocalDateTime(ts.getTime());
    }
}