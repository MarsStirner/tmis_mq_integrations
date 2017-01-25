package ru.bars_open.medvtr.mq.util.exceptions;

import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 19.01.2017, 16:58 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class UnknownRoutingKeyException extends Exception {
    private final String routingKey;
    private final Set<String> possibleKeys;

    public UnknownRoutingKeyException(final String routingKey, final Set<String> possibleKeys) {
        super();
        this.routingKey = routingKey;
        this.possibleKeys = possibleKeys;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public Set<String> getPossibleKeys() {
        return possibleKeys;
    }

    @Override
    public String getMessage() {
        return "Unknown routingKey='"+routingKey+"'. Known keys are "+ possibleKeys;
    }
}
