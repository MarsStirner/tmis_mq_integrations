package ru.bars_open.medvtr.db.entities.util.converter;

import ru.bars_open.medvtr.db.entities.util.TypeName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Author: Upatov Egor <br>
 * Date: 06.03.2017, 10:41 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Converter(autoApply = true)
public class ActionPropertyType_TypeNameConverter implements AttributeConverter<TypeName, String> {

    @Override
    public String convertToDatabaseColumn(final TypeName attribute) {
        return attribute.getValue();
    }

    @Override
    public TypeName convertToEntityAttribute(final String dbData) {
        return TypeName.fromString(dbData);
    }
}
