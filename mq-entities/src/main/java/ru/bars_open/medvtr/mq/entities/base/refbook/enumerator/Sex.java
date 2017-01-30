
package ru.bars_open.medvtr.mq.entities.base.refbook.enumerator;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Sex {

    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");
    private final String value;
    private final static Map<String, Sex> CONSTANTS = new HashMap<String, Sex>();

    static {
        for (Sex c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private Sex(String value) {
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
    public static Sex fromValue(String value) {
        Sex constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
