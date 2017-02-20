package ru.bars_open.medvtr.mq.util.exceptions;

import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 19.01.2017, 18:16 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageIsIncorrectException extends Exception {
    private final Set<String> errors;

    public MessageIsIncorrectException(final Set<String> errors) {
        super("Message is not correct");
        this.errors = errors;
    }

    public Set<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "| Found errors=" + errors.toString();
    }

}
