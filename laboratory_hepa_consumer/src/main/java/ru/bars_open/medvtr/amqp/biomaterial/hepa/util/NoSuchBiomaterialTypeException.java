package ru.bars_open.medvtr.amqp.biomaterial.hepa.util;

/**
 * Author: Upatov Egor <br>
 * Date: 21.02.2017, 5:11 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class NoSuchBiomaterialTypeException extends Exception {
    private final String name;
    private final String code;

    public NoSuchBiomaterialTypeException(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return "NoSuchBiomaterialType with code='" + code + "' and name='" + name + "'";
    }
}

