
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Address
 * <p>
 * Адрес
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "addressType",
    "value"
})
public class Address implements Serializable
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
     * Тип адреса
     * 
     */
    @JsonProperty("addressType")
    @JsonPropertyDescription("\u0422\u0438\u043f \u0430\u0434\u0440\u0435\u0441\u0430")
    private Address.AddressType addressType;
    /**
     * Текстовое представление адреса
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("\u0422\u0435\u043a\u0441\u0442\u043e\u0432\u043e\u0435 \u043f\u0440\u0435\u0434\u0441\u0442\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u0430\u0434\u0440\u0435\u0441\u0430")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -112488984930825702L;

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
     * Тип адреса
     * 
     */
    @JsonProperty("addressType")
    public Address.AddressType getAddressType() {
        return addressType;
    }

    /**
     * Тип адреса
     * 
     */
    @JsonProperty("addressType")
    public void setAddressType(Address.AddressType addressType) {
        this.addressType = addressType;
    }

    /**
     * Текстовое представление адреса
     * 
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Текстовое представление адреса
     * 
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
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
        return new HashCodeBuilder().append(id).append(addressType).append(value).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(id, rhs.id).append(addressType, rhs.addressType).append(value, rhs.value).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public enum AddressType {

        REGISTRATION("REGISTRATION"),
        LIVING("LIVING"),
        UNKNOWN("UNKNOWN");
        private final String value;
        private final static Map<String, Address.AddressType> CONSTANTS = new HashMap<String, Address.AddressType>();

        static {
            for (Address.AddressType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private AddressType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Address.AddressType fromValue(String value) {
            Address.AddressType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
