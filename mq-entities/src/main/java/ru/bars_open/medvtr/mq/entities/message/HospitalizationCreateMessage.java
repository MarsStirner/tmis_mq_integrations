
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
import ru.bars_open.medvtr.mq.entities.action.StationaryReceived;
import ru.bars_open.medvtr.mq.entities.base.Event;


/**
 * HospitalizationCreatedMessage
 * <p>
 * Сообщения содержащее даные о созданном обращении
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "received"
})
public class HospitalizationCreateMessage implements Serializable
{

    /**
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    @JsonPropertyDescription("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435")
    private Event event;
    /**
     * StationaryReceived
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("received")
    @JsonPropertyDescription("\u042d\u043a\u0448\u0435\u043d-\u043f\u043e\u0441\u0442\u0443\u043f\u043b\u0435\u043d\u0438\u044f \u0432 \u0441\u0442\u0430\u0446\u0438\u043e\u043d\u0430\u0440\u0435")
    private StationaryReceived received;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6241526287310789985L;

    /**
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * StationaryReceived
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("received")
    public StationaryReceived getReceived() {
        return received;
    }

    /**
     * StationaryReceived
     * <p>
     * Экшен-поступления в стационаре
     * (Required)
     * 
     */
    @JsonProperty("received")
    public void setReceived(StationaryReceived received) {
        this.received = received;
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
        return new HashCodeBuilder().append(event).append(received).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HospitalizationCreateMessage) == false) {
            return false;
        }
        HospitalizationCreateMessage rhs = ((HospitalizationCreateMessage) other);
        return new EqualsBuilder().append(event, rhs.event).append(received, rhs.received).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
