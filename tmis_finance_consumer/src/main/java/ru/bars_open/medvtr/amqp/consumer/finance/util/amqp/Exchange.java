package ru.bars_open.medvtr.amqp.consumer.finance.util.amqp;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 03.11.2016, 15:47 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class Exchange {

    private final Map<String, Object> arguments = new HashMap<>();
    private String name;
    private boolean durable;
    private boolean autoDelete;
    private String type;
    private boolean internal;

    /**
     * Construct a new Exchange for bean usage.
     * @param name the name of the exchange.
     */
    public Exchange(String name, String type) {
        this(name, type, true, false);
    }

    /**
     * Construct a new Exchange, given a name, durability flag, auto-delete flag.
     * @param name the name of the exchange.
     * @param durable true if we are declaring a durable exchange (the exchange will survive a server restart)
     * @param autoDelete true if the server should delete the exchange when it is no longer in use
     */
    public Exchange(String name, String type, boolean durable, boolean autoDelete) {
        this(name, type, durable, autoDelete, null);
    }

    /**
     * Construct a new Exchange, given a name, durability flag, and auto-delete flag, and arguments.
     * @param name the name of the exchange.
     * @param durable true if we are declaring a durable exchange (the exchange will survive a server restart)
     * @param autoDelete true if the server should delete the exchange when it is no longer in use
     * @param arguments the arguments used to declare the exchange
     */
    public Exchange(String name, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        super();
        this.name = name;
        this.durable = durable;
        this.autoDelete = autoDelete;
        this.type = type;
        if (arguments != null) {
            this.arguments.putAll(arguments);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public Object addArqument(final String key, final Object value) {
        return this.arguments.put(key, value);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDurable(final boolean durable) {
        this.durable = durable;
    }

    public void setAutoDelete(final boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Return the collection of arbitrary arguments to use when declaring an exchange.
     * @return the collection of arbitrary arguments to use when declaring an exchange.
     */
    public Map<String, Object> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Exchange ['" + name +
                "']{type=" + this.getType() +
                ", durable=" + durable +
                ", autoDelete=" + autoDelete +
                ", arguments="	+ arguments + "}";
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(final boolean internal) {
        this.internal = internal;
    }
}
