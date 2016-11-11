package ru.bars_open.medvtr.db;

import java.text.MessageFormat;
import java.util.HashMap;


public class ErrorMessage {
    private static final HashMap<String, String> errorMessages = new HashMap<>(100);

    static {
      errorMessages.put("NOT_FOUND_JNDI_DATASOURCE", "DataSource not found by JNDI name '{0}'");
    }

    public static String format(final String key, Object... args){
        return MessageFormat.format(errorMessages.get(key), args);
    }
}
