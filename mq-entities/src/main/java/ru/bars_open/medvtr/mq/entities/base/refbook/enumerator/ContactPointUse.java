
package ru.bars_open.medvtr.mq.entities.base.refbook.enumerator;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactPointUse {

    HOME("home"),
    WORK("work"),
    TEMP("temp"),
    OLD("old"),
    MOBILE("mobile");
    private final String value;
    private final static Map<String, ContactPointUse> CONSTANTS = new HashMap<String, ContactPointUse>();

    static {
        for (ContactPointUse c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ContactPointUse(String value) {
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
    public static ContactPointUse fromValue(String value) {
        ContactPointUse constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
