
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.Sex;


/**
 * Описание пациента МИС ()
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "lastName",
    "firstName",
    "patrName",
    "sex",
    "birthDate",
    "addresses"
})
public class Person implements Serializable
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
     * Фамилия
     * 
     */
    @JsonProperty("lastName")
    @JsonPropertyDescription("\u0424\u0430\u043c\u0438\u043b\u0438\u044f")
    private String lastName;
    /**
     * Имя
     * 
     */
    @JsonProperty("firstName")
    @JsonPropertyDescription("\u0418\u043c\u044f")
    private String firstName;
    /**
     * Отчество
     * 
     */
    @JsonProperty("patrName")
    @JsonPropertyDescription("\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e")
    private String patrName;
    @JsonProperty("sex")
    private Sex sex;
    /**
     * дата рождения пациента
     * 
     */
    @JsonProperty("birthDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430")
    private LocalDateTime birthDate;
    /**
     * Адреса пациента (тип адреса внутри элемента)
     * 
     */
    @JsonProperty("addresses")
    @JsonPropertyDescription("\u0410\u0434\u0440\u0435\u0441\u0430 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 (\u0442\u0438\u043f \u0430\u0434\u0440\u0435\u0441\u0430 \u0432\u043d\u0443\u0442\u0440\u0438 \u044d\u043b\u0435\u043c\u0435\u043d\u0442\u0430)")
    private List<Address> addresses = new ArrayList<Address>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5066195088784190865L;

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
     * Фамилия
     * 
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * Фамилия
     * 
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Имя
     * 
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Имя
     * 
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Отчество
     * 
     */
    @JsonProperty("patrName")
    public String getPatrName() {
        return patrName;
    }

    /**
     * Отчество
     * 
     */
    @JsonProperty("patrName")
    public void setPatrName(String patrName) {
        this.patrName = patrName;
    }

    @JsonProperty("sex")
    public Sex getSex() {
        return sex;
    }

    @JsonProperty("sex")
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * дата рождения пациента
     * 
     */
    @JsonProperty("birthDate")
    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    /**
     * дата рождения пациента
     * 
     */
    @JsonProperty("birthDate")
    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Адреса пациента (тип адреса внутри элемента)
     * 
     */
    @JsonProperty("addresses")
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Адреса пациента (тип адреса внутри элемента)
     * 
     */
    @JsonProperty("addresses")
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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
        return new HashCodeBuilder().append(id).append(lastName).append(firstName).append(patrName).append(sex).append(birthDate).append(addresses).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(lastName, rhs.lastName).append(firstName, rhs.firstName).append(patrName, rhs.patrName).append(sex, rhs.sex).append(birthDate, rhs.birthDate).append(addresses, rhs.addresses).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
