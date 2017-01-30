
package ru.bars_open.medvtr.mq.entities.base.refbook.enumerator;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionStatus {

    STARTED("STARTED"),
    WAIT("WAIT"),
    FINISHED("FINISHED");
    private final String value;
    private final static Map<String, ActionStatus> CONSTANTS = new HashMap<String, ActionStatus>();

    static {
        for (ActionStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ActionStatus(String value) {
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
    public static ActionStatus fromValue(String value) {
        ActionStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
