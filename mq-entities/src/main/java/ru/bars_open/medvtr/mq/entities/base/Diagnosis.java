
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import ru.bars_open.medvtr.mq.entities.base.refbook.MKB;


/**
 * Диагноз
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "typeDescription",
    "MKB",
    "date"
})
public class Diagnosis implements Serializable
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
     * Тип диагноза
     * (Required)
     * 
     */
    @JsonProperty("typeDescription")
    @JsonPropertyDescription("\u0422\u0438\u043f \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u0430")
    private String typeDescription;
    /**
     * Справочник МКБ-10 (Международный классификатор болезней) 
     * (Required)
     * 
     */
    @JsonProperty("MKB")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u041c\u041a\u0411-10 (\u041c\u0435\u0436\u0434\u0443\u043d\u0430\u0440\u043e\u0434\u043d\u044b\u0439 \u043a\u043b\u0430\u0441\u0441\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u0431\u043e\u043b\u0435\u0437\u043d\u0435\u0439) ")
    private MKB mKB;
    /**
     * Дата установления диагноза
     * 
     */
    @JsonProperty("date")
    @JsonPropertyDescription("\u0414\u0430\u0442\u0430 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u044f \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u0430")
    private LocalDateTime date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5478875727844566403L;

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
     * Тип диагноза
     * (Required)
     * 
     */
    @JsonProperty("typeDescription")
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * Тип диагноза
     * (Required)
     * 
     */
    @JsonProperty("typeDescription")
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    /**
     * Справочник МКБ-10 (Международный классификатор болезней) 
     * (Required)
     * 
     */
    @JsonProperty("MKB")
    public MKB getMKB() {
        return mKB;
    }

    /**
     * Справочник МКБ-10 (Международный классификатор болезней) 
     * (Required)
     * 
     */
    @JsonProperty("MKB")
    public void setMKB(MKB mKB) {
        this.mKB = mKB;
    }

    /**
     * Дата установления диагноза
     * 
     */
    @JsonProperty("date")
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Дата установления диагноза
     * 
     */
    @JsonProperty("date")
    public void setDate(LocalDateTime date) {
        this.date = date;
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
        return new HashCodeBuilder().append(id).append(typeDescription).append(mKB).append(date).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Diagnosis) == false) {
            return false;
        }
        Diagnosis rhs = ((Diagnosis) other);
        return new EqualsBuilder().append(id, rhs.id).append(typeDescription, rhs.typeDescription).append(mKB, rhs.mKB).append(date, rhs.date).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
