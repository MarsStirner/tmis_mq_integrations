
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
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTest;


/**
 * Тест (экшен проперти и ФСЛИ-шный тест к нему)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "test"
})
public class Test implements Serializable
{

    /**
     * Идентификатор ActionProperty
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 ActionProperty")
    private Integer id;
    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("test")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0442\u0435\u0441\u0442\u043e\u0432 \u0422\u041c\u0418\u0421")
    private RbTest test;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7114608439427847158L;

    /**
     * Идентификатор ActionProperty
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентификатор ActionProperty
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("test")
    public RbTest getTest() {
        return test;
    }

    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("test")
    public void setTest(RbTest test) {
        this.test = test;
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
        return new HashCodeBuilder().append(id).append(test).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Test) == false) {
            return false;
        }
        Test rhs = ((Test) other);
        return new EqualsBuilder().append(id, rhs.id).append(test, rhs.test).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
