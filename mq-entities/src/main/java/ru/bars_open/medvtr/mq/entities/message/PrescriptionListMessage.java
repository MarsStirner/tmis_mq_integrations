
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
import ru.bars_open.medvtr.mq.entities.action.PrescriptionAction;
import ru.bars_open.medvtr.mq.entities.base.Event;


/**
 * PrescriptionListMessage
 * <p>
 * Сообщения содержащее даные о ЛН
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "prescription"
})
public class PrescriptionListMessage implements Serializable
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
     * PrescriptionAction
     * <p>
     * Экшен cо списком ЛН
     * 
     */
    @JsonProperty("prescription")
    @JsonPropertyDescription("\u042d\u043a\u0448\u0435\u043d c\u043e \u0441\u043f\u0438\u0441\u043a\u043e\u043c \u041b\u041d")
    private PrescriptionAction prescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7459708951336928418L;

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
     * PrescriptionAction
     * <p>
     * Экшен cо списком ЛН
     * 
     */
    @JsonProperty("prescription")
    public PrescriptionAction getPrescription() {
        return prescription;
    }

    /**
     * PrescriptionAction
     * <p>
     * Экшен cо списком ЛН
     * 
     */
    @JsonProperty("prescription")
    public void setPrescription(PrescriptionAction prescription) {
        this.prescription = prescription;
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
        return new HashCodeBuilder().append(event).append(prescription).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PrescriptionListMessage) == false) {
            return false;
        }
        PrescriptionListMessage rhs = ((PrescriptionListMessage) other);
        return new EqualsBuilder().append(event, rhs.event).append(prescription, rhs.prescription).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
