
package ru.bars_open.medvtr.mq.entities.base.util;

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
import ru.bars_open.medvtr.mq.entities.base.refbook.RbUnit;


/**
 * ValueAndUnit
 * <p>
 * Структура содержащая единицу измерения и само значение
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "unit"
})
public class ValueAndUnit implements Serializable
{

    /**
     * Значение
     * (Required)
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private Double value;
    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * (Required)
     * 
     */
    @JsonProperty("unit")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0415\u0434\u0438\u043d\u0438\u0446 \u0418\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f")
    private RbUnit unit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6190164133802277597L;

    /**
     * Значение
     * (Required)
     * 
     */
    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    /**
     * Значение
     * (Required)
     * 
     */
    @JsonProperty("value")
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * (Required)
     * 
     */
    @JsonProperty("unit")
    public RbUnit getUnit() {
        return unit;
    }

    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * (Required)
     * 
     */
    @JsonProperty("unit")
    public void setUnit(RbUnit unit) {
        this.unit = unit;
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
        return new HashCodeBuilder().append(value).append(unit).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ValueAndUnit) == false) {
            return false;
        }
        ValueAndUnit rhs = ((ValueAndUnit) other);
        return new EqualsBuilder().append(value, rhs.value).append(unit, rhs.unit).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
