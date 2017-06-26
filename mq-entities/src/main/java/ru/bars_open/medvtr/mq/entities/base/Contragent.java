
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
 * Contract
 * <p>
 * Контрагент (физ или юр лицо)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "person",
    "organisation"
})
public class Contragent implements Serializable
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
     * Тип контрагента
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("\u0422\u0438\u043f \u043a\u043e\u043d\u0442\u0440\u0430\u0433\u0435\u043d\u0442\u0430")
    private Contragent.Type type;
    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person person;
    /**
     * Organisation
     * <p>
     * Организация
     * 
     */
    @JsonProperty("organisation")
    @JsonPropertyDescription("\u041e\u0440\u0433\u0430\u043d\u0438\u0437\u0430\u0446\u0438\u044f")
    private Organisation organisation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -835229873130467886L;

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
     * Тип контрагента
     * (Required)
     * 
     */
    @JsonProperty("type")
    public Contragent.Type getType() {
        return type;
    }

    /**
     * Тип контрагента
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(Contragent.Type type) {
        this.type = type;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Organisation
     * <p>
     * Организация
     * 
     */
    @JsonProperty("organisation")
    public Organisation getOrganisation() {
        return organisation;
    }

    /**
     * Organisation
     * <p>
     * Организация
     * 
     */
    @JsonProperty("organisation")
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
        return new HashCodeBuilder().append(id).append(type).append(person).append(organisation).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Contragent) == false) {
            return false;
        }
        Contragent rhs = ((Contragent) other);
        return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(person, rhs.person).append(organisation, rhs.organisation).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public enum Type {

        JURIDICAL("JURIDICAL"),
        PHYSICAL("PHYSICAL");
        private final String value;
        private final static Map<String, Contragent.Type> CONSTANTS = new HashMap<String, Contragent.Type>();

        static {
            for (Contragent.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Type(String value) {
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
        public static Contragent.Type fromValue(String value) {
            Contragent.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
