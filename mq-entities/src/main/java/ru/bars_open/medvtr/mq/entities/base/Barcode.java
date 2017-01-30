
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Баркод для этого биозабора (Штрихкод)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "period",
    "code"
})
public class Barcode implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("period")
    private Integer period;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("code")
    private Integer code;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -243293140944889285L;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("period")
    public Integer getPeriod() {
        return period;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("period")
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
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
        return new HashCodeBuilder().append(period).append(code).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Barcode) == false) {
            return false;
        }
        Barcode rhs = ((Barcode) other);
        return new EqualsBuilder().append(period, rhs.period).append(code, rhs.code).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
