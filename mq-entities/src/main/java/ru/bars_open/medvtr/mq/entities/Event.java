
package ru.bars_open.medvtr.mq.entities;

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
import org.joda.time.DateTime;


/**
 * event
 * <p>
 * Обращение на лечение
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "setDate",
    "externalId"
})
public class Event implements Serializable
{

    /**
     * Идентифкатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private Integer id;
    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    @JsonPropertyDescription("")
    private DateTime setDate;
    /**
     * номер обращения
     * 
     */
    @JsonProperty("externalId")
    @JsonPropertyDescription("")
    private String externalId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6333767593354437584L;

    /**
     * Идентифкатор
     * (Required)
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * дата создания обращения
     * 
     * @return
     *     The setDate
     */
    @JsonProperty("setDate")
    public DateTime getSetDate() {
        return setDate;
    }

    /**
     * дата создания обращения
     * 
     * @param setDate
     *     The setDate
     */
    @JsonProperty("setDate")
    public void setSetDate(DateTime setDate) {
        this.setDate = setDate;
    }

    /**
     * номер обращения
     * 
     * @return
     *     The externalId
     */
    @JsonProperty("externalId")
    public String getExternalId() {
        return externalId;
    }

    /**
     * номер обращения
     * 
     * @param externalId
     *     The externalId
     */
    @JsonProperty("externalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
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
        return new HashCodeBuilder().append(id).append(setDate).append(externalId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().append(id, rhs.id).append(setDate, rhs.setDate).append(externalId, rhs.externalId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
