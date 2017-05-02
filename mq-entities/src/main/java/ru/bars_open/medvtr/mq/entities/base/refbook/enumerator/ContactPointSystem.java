
package ru.bars_open.medvtr.mq.entities.base.refbook.enumerator;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactPointSystem {

    PHONE("phone"),
    FAX("fax"),
    EMAIL("email"),
    PAGER("pager"),
    URL("url"),
    SMS("sms"),
    OTHER("other");
    private final String value;
    private final static Map<String, ContactPointSystem> CONSTANTS = new HashMap<String, ContactPointSystem>();

    static {
        for (ContactPointSystem c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ContactPointSystem(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static ContactPointSystem fromValue(String value) {
        ContactPointSystem constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
