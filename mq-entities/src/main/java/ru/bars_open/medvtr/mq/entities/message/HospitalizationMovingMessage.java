
package ru.bars_open.medvtr.mq.entities.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.bars_open.medvtr.mq.entities.action.StationaryMoving;
import ru.bars_open.medvtr.mq.entities.base.Event;


/**
 * HospitalizationMovingMessage
 * <p>
 * Сообщения содержащее даные о созданном обращении
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "moving"
})
public class HospitalizationMovingMessage implements Serializable
{

    /**
     * Event
     * <p>
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    @JsonPropertyDescription("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435")
    private Event event;
    /**
     * StationaryMoving
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("moving")
    @JsonPropertyDescription("\u042d\u043a\u0448\u0435\u043d-\u043f\u043e\u0441\u0442\u0443\u043f\u043b\u0435\u043d\u0438\u044f \u0432 \u0441\u0442\u0430\u0446\u0438\u043e\u043d\u0430\u0440\u0435")
    private StationaryMoving moving;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3654860313827818113L;

    /**
     * Event
     * <p>
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     * Event
     * <p>
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * StationaryMoving
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("moving")
    public StationaryMoving getMoving() {
        return moving;
    }

    /**
     * StationaryMoving
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("moving")
    public void setMoving(StationaryMoving moving) {
        this.moving = moving;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(event).append(moving).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HospitalizationMovingMessage) == false) {
            return false;
        }
        HospitalizationMovingMessage rhs = ((HospitalizationMovingMessage) other);
        return new EqualsBuilder().append(event, rhs.event).append(moving, rhs.moving).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
