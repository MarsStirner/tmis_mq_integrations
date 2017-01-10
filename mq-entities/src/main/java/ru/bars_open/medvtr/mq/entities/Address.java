
package ru.bars_open.medvtr.mq.entities;

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
 * person
 * <p>
 * Описание пациента МИС ()
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "addressType",
    "value",
    "required"
})
public class Address implements Serializable
{

    /**
     * Идентифкатор МИС
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private Integer id;
    /**
     * Тип адреса
     * 
     */
    @JsonProperty("addressType")
    @JsonPropertyDescription("")
    private Address.AddressType addressType;
    /**
     * Текстовое представление адреса
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("")
    private String value;
    @JsonProperty("required")
    private Object required;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6276742705824834451L;

    /**
     * Идентифкатор МИС
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор МИС
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Тип адреса
     * 
     * @return
     *     The addressType
     */
    @JsonProperty("addressType")
    public Address.AddressType getAddressType() {
        return addressType;
    }

    /**
     * Тип адреса
     * 
     * @param addressType
     *     The addressType
     */
    @JsonProperty("addressType")
    public void setAddressType(Address.AddressType addressType) {
        this.addressType = addressType;
    }

    /**
     * Текстовое представление адреса
     * 
     * @return
     *     The value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Текстовое представление адреса
     * 
     * @param value
     *     The value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The required
     */
    @JsonProperty("required")
    public Object getRequired() {
        return required;
    }

    /**
     * 
     * @param required
     *     The required
     */
    @JsonProperty("required")
    public void setRequired(Object required) {
        this.required = required;
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
        return new HashCodeBuilder().append(id).append(addressType).append(value).append(required).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(addressType, rhs.addressType).append(value, rhs.value).append(required, rhs.required).append(additionalProperties, rhs.additionalProperties).isEquals();
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
