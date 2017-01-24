
package ru.bars_open.medvtr.mq.entities.base;

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


/**
 * Organisation
 * <p>
 * Организация
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "uuid",
    "shortName"
})
public class Organisation implements Serializable
{

    /**
     * Идентифкатор МИС
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u043a\u0430\u0442\u043e\u0440 \u041c\u0418\u0421")
    private Integer id;
    /**
     * dashed string uuid
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("dashed string uuid")
    private String uuid;
    /**
     * Краткое наименование организации
     * (Required)
     * 
     */
    @JsonProperty("shortName")
    @JsonPropertyDescription("\u041a\u0440\u0430\u0442\u043a\u043e\u0435 \u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u043e\u0440\u0433\u0430\u043d\u0438\u0437\u0430\u0446\u0438\u0438")
    private String shortName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4242833205244612889L;

    /**
     * Идентифкатор МИС
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор МИС
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * dashed string uuid
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    /**
     * dashed string uuid
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Краткое наименование организации
     * (Required)
     * 
     */
    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    /**
     * Краткое наименование организации
     * (Required)
     * 
     */
    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
        return new HashCodeBuilder().append(id).append(uuid).append(shortName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Organisation) == false) {
            return false;
        }
        Organisation rhs = ((Organisation) other);
        return new EqualsBuilder().append(id, rhs.id).append(uuid, rhs.uuid).append(shortName, rhs.shortName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
