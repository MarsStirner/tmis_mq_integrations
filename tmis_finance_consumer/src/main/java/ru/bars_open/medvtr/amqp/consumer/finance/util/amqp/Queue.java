package ru.bars_open.medvtr.amqp.consumer.finance.util.amqp;

import java.util.HashMap;
import java.util.Map;

public class Queue {

    private final Map<String, Object> arguments = new HashMap<>();
    private String name;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;

    /**
     * The queue is durable, non-exclusive and non auto-delete.
     *
     * @param name the name of the queue.
     */
    public Queue(String name) {
        this(name, true, false, false);
    }

    /**
     * Construct a new queue, given a name and durability flag. The queue is non-exclusive and non auto-delete.
     *
     * @param name    the name of the queue.
     * @param durable true if we are declaring a durable queue (the queue will survive a server restart)
     */
    public Queue(String name, boolean durable) {
        this(name, durable, false, false, null);
    }

    /**
     * Construct a new queue, given a name, durability, exclusive and auto-delete flags.
     *
     * @param name       the name of the queue.
     * @param durable    true if we are declaring a durable queue (the queue will survive a server restart)
     * @param exclusive  true if we are declaring an exclusive queue (the queue will only be used by the declarer's
     *                   connection)
     * @param autoDelete true if the server should delete the queue when it is no longer in use
     */
    public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
        this(name, durable, exclusive, autoDelete, null);
    }

    /**
     * Construct a new queue, given a name, durability flag, and auto-delete flag, and arguments.
     *
     * @param name       the name of the queue.
     * @param durable    true if we are declaring a durable queue (the queue will survive a server restart)
     * @param exclusive  true if we are declaring an exclusive queue (the queue will only be used by the declarer's
     *                   connection)
     * @param autoDelete true if the server should delete the queue when it is no longer in use
     * @param arguments  the arguments used to declare the queue
     */
    public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        super();
        this.name = name;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        if(arguments != null) {
            this.arguments.putAll(arguments);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * A durable queue will survive a server restart
     *
     * @return true if durable
     */
    public boolean isDurable() {
        return this.durable;
    }

    public void setDurable(final boolean durable) {
        this.durable = durable;
    }

    /**
     * True if the server should only send messages to the declarer's connection.
     *
     * @return true if auto-delete
     */
    public boolean isExclusive() {
        return this.exclusive;
    }

    public void setExclusive(final boolean exclusive) {
        this.exclusive = exclusive;
    }

    /**
     * True if the server should delete the queue when it is no longer in use (the last consumer is cancelled). A queue
     * that never has any consumers will not be deleted automatically.
     *
     * @return true if auto-delete
     */
    public boolean isAutoDelete() {
        return this.autoDelete;
    }

    public void setAutoDelete(final boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public java.util.Map<java.lang.String, java.lang.Object> getArguments() {
        return this.arguments;
    }

    public Object addArqument(final String key, final Object value) {
        return this.arguments.put(key, value);
    }

    @Override
    public String toString() {
        return "Queue ['" + name + "']{ durable=" + durable + ", autoDelete=" + autoDelete + ", exclusive=" + exclusive + ", arguments=" + arguments + "}";
    }
}