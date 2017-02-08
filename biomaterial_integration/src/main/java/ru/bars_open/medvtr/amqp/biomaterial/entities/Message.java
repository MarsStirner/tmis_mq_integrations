package ru.bars_open.medvtr.amqp.biomaterial.entities;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 14:24 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "Message")
public class Message extends IdentifiedEntity{

    /**
     *Dashed-string UUID (length=36)
     */
    @Column(name="correlationId",  nullable = false)
    private String correlationId;

    /**
     * Направление запроса (IN - входящий, OUT-исходящий)
     */
    @Column(name="direction", nullable = false)
    @Enumerated(EnumType.STRING)
    private Direction direction;

    /**
     * Ключ с которым поступило или было отправлено сообщение
     */
    @Column(name="routingKey", nullable = false)
    private String routingKey;

    /**
     * Временная метка сообщения
     */
    @Column(name="timestamp", nullable = false)
    private LocalDateTime timestamp;

    /**
     * AMQP property
     */
    @Column(name="type", nullable = false)
    private String type;


    /**
     * Сами данные сообщения
     */
    @Column(name="body", nullable = false)
    private String body;


    /**
     * Порядковый номер сообщения в рамках сессии обмена (для поиска в логе)
     */
    @Column(name="deliveryTag", nullable = false)
    private int deliveryTag;

    /**
     * Ссылка на биоматериал, к которому относится сообщение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="biomaterial_id", nullable = false)
    private Biomaterial biomaterial;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(final String routingKey) {
        this.routingKey = routingKey;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public int getDeliveryTag() {
        return deliveryTag;
    }

    public void setDeliveryTag(final int deliveryTag) {
        this.deliveryTag = deliveryTag;
    }

    public Biomaterial getBiomaterial() {
        return biomaterial;
    }

    public void setBiomaterial(final Biomaterial biomaterial) {
        this.biomaterial = biomaterial;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("correlationId='").append(correlationId).append('\'');
        sb.append(", direction=").append(direction);
        sb.append(", routingKey='").append(routingKey).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", type='").append(type).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", deliveryTag=").append(deliveryTag);
        sb.append(", biomaterial=").append(biomaterial);
        sb.append('}');
        return sb.toString();
    }
}
