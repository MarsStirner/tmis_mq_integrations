package ru.bars_open.medvtr.amqp.biomaterial.dao.util;

import org.joda.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 19:00 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(final LocalDate ld) {
        return ld == null ? null : ld.toDate();
    }

    @Override
    public LocalDate convertToEntityAttribute(final Date date) {
        return date == null ? null : new LocalDate(date);
    }
}
