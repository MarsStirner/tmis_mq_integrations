
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


/**
 * person
 * <p>
 * Описание пользователя МИС
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "lastName",
    "firstName",
    "patrName"
})
public class Person implements Serializable
{

    /**
     * Идентифкатор МИС
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private Integer id;
    /**
     * Фамилия
     * 
     */
    @JsonProperty("lastName")
    @JsonPropertyDescription("")
    private String lastName;
    /**
     * Имя
     * 
     */
    @JsonProperty("firstName")
    @JsonPropertyDescription("")
    private String firstName;
    /**
     * Отчество
     * 
     */
    @JsonProperty("patrName")
    @JsonPropertyDescription("")
    private String patrName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6840587850082472764L;

    /**
     * Идентифкатор МИС
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
     * Идентифкатор МИС
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
     * Фамилия
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * Фамилия
     * 
     * @param lastName
     *     The lastName
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Имя
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Имя
     * 
     * @param firstName
     *     The firstName
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Отчество
     * 
     * @return
     *     The patrName
     */
    @JsonProperty("patrName")
    public String getPatrName() {
        return patrName;
    }

    /**
     * Отчество
     * 
     * @param patrName
     *     The patrName
     */
    @JsonProperty("patrName")
    public void setPatrName(String patrName) {
        this.patrName = patrName;
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
        return new HashCodeBuilder().append(id).append(lastName).append(firstName).append(patrName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Person) == false) {
            return false;
        }
        Person rhs = ((Person) other);
        return new EqualsBuilder().append(id, rhs.id).append(lastName, rhs.lastName).append(firstName, rhs.firstName).append(patrName, rhs.patrName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
