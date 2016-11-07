package ru.bars_open.medvtr.amqp.consumer.finance.util;

import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.ObjectFactory;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.PersonName;
import ru.bars_open.medvtr.mq.entities.Person;

/**
 * Author: Upatov Egor <br>
 * Date: 31.10.2016, 14:19 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class WebServiceObjectFactory {
    private final static ObjectFactory _factory = new ObjectFactory();

    public static PersonName createPersonName(final String family, final String given, final String partName) {
        final PersonName result = _factory.createPersonName();
        result.setFamily(family);
        result.setGiven(given);
        result.setPartName(partName);
        return result;
    }


    public static PersonName createPersonName(final Person person) {
        return createPersonName(person.getLastName(), person.getFirstName(), person.getPatrName());
    }
}
