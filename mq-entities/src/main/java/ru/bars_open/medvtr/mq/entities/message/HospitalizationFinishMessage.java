
package ru.bars_open.medvtr.mq.entities.message;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.bars_open.medvtr.mq.entities.action.StationaryLeaved;
import ru.bars_open.medvtr.mq.entities.action.StationaryMoving;
import ru.bars_open.medvtr.mq.entities.base.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HospitalizationFinishMessage
 * <p>
 * Сообщения содержащее даные о закрытом обращении
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "leaved",
    "moves"
})
public class HospitalizationFinishMessage implements Serializable
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
     * StationaryLeaved
     * <p>
     * Экшен-выписка в стационаре
     * (Required)
     * 
     */
    @JsonProperty("leaved")
    @JsonPropertyDescription("\u042d\u043a\u0448\u0435\u043d-\u0432\u044b\u043f\u0438\u0441\u043a\u0430 \u0432 \u0441\u0442\u0430\u0446\u0438\u043e\u043d\u0430\u0440\u0435")
    private StationaryLeaved leaved;
    /**
     * Движения между отделениями
     * 
     */
    @JsonProperty("moves")
    @JsonPropertyDescription("\u0414\u0432\u0438\u0436\u0435\u043d\u0438\u044f \u043c\u0435\u0436\u0434\u0443 \u043e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u044f\u043c\u0438")
    private List<StationaryMoving> moves = new ArrayList<StationaryMoving>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1498213919000527190L;

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
     * StationaryLeaved
     * <p>
     * Экшен-выписка в стационаре
     * (Required)
     * 
     */
    @JsonProperty("leaved")
    public StationaryLeaved getLeaved() {
        return leaved;
    }

    /**
     * StationaryLeaved
     * <p>
     * Экшен-выписка в стационаре
     * (Required)
     * 
     */
    @JsonProperty("leaved")
    public void setLeaved(StationaryLeaved leaved) {
        this.leaved = leaved;
    }

    /**
     * Движения между отделениями
     * 
     */
    @JsonProperty("moves")
    public List<StationaryMoving> getMoves() {
        return moves;
    }

    /**
     * Движения между отделениями
     * 
     */
    @JsonProperty("moves")
    public void setMoves(List<StationaryMoving> moves) {
        this.moves = moves;
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
        return new HashCodeBuilder().append(event).append(leaved).append(moves).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HospitalizationFinishMessage) == false) {
            return false;
        }
        HospitalizationFinishMessage rhs = ((HospitalizationFinishMessage) other);
        return new EqualsBuilder().append(event, rhs.event).append(leaved, rhs.leaved).append(moves, rhs.moves).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}