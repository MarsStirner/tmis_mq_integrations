
package ru.bars_open.medvtr.mq.entities.base.refbook;

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
 * RlsActMatters
 * <p>
 * РЛС Наименование Действующих Средств
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "localName"
})
public class RlsActMatters implements Serializable
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
     * Международное наименование
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u041c\u0435\u0436\u0434\u0443\u043d\u0430\u0440\u043e\u0434\u043d\u043e\u0435 \u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435")
    private String name;
    /**
     * Локальное наименование
     * 
     */
    @JsonProperty("localName")
    @JsonPropertyDescription("\u041b\u043e\u043a\u0430\u043b\u044c\u043d\u043e\u0435 \u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435")
    private String localName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -236488721031941973L;

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
     * Международное наименование
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Международное наименование
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Локальное наименование
     * 
     */
    @JsonProperty("localName")
    public String getLocalName() {
        return localName;
    }

    /**
     * Локальное наименование
     * 
     */
    @JsonProperty("localName")
    public void setLocalName(String localName) {
        this.localName = localName;
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
        return new HashCodeBuilder().append(id).append(name).append(localName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RlsActMatters) == false) {
            return false;
        }
        RlsActMatters rhs = ((RlsActMatters) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(localName, rhs.localName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
