
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
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ContactPointSystem;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ContactPointUse;


/**
 * Details for all kinds of technology-mediated contact points for a person or organization, including telephone, email, etc.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "system",
    "value",
    "use"
})
public class ContactPoint implements Serializable
{

    /**
     * Идентификатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440")
    private Integer id;
    /**
     * https://www.hl7.org/fhir/valueset-contact-point-system.html
     * (Required)
     * 
     */
    @JsonProperty("system")
    @JsonPropertyDescription("https://www.hl7.org/fhir/valueset-contact-point-system.html")
    private ContactPointSystem system;
    /**
     * The actual contact point details
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The actual contact point details")
    private String value;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("use")
    private ContactPointUse use;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5289784740729967010L;

    /**
     * Идентификатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентификатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * https://www.hl7.org/fhir/valueset-contact-point-system.html
     * (Required)
     * 
     */
    @JsonProperty("system")
    public ContactPointSystem getSystem() {
        return system;
    }

    /**
     * https://www.hl7.org/fhir/valueset-contact-point-system.html
     * (Required)
     * 
     */
    @JsonProperty("system")
    public void setSystem(ContactPointSystem system) {
        this.system = system;
    }

    /**
     * The actual contact point details
     * 
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * The actual contact point details
     * 
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("use")
    public ContactPointUse getUse() {
        return use;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("use")
    public void setUse(ContactPointUse use) {
        this.use = use;
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
        return new HashCodeBuilder().append(id).append(system).append(value).append(use).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactPoint) == false) {
            return false;
        }
        ContactPoint rhs = ((ContactPoint) other);
        return new EqualsBuilder().append(id, rhs.id).append(system, rhs.system).append(value, rhs.value).append(use, rhs.use).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
