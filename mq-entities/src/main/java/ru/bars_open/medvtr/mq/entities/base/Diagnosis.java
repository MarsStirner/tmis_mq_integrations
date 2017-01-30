
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
import ru.bars_open.medvtr.mq.entities.base.refbook.MKB;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbDiagnosisType;


/**
 * Диагноз
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "MKB"
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
     * Справочник источников финансирования
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0438\u0441\u0442\u043e\u0447\u043d\u0438\u043a\u043e\u0432 \u0444\u0438\u043d\u0430\u043d\u0441\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f")
    private RbDiagnosisType type;
    /**
     * Справочник МКБ-10 (Международный классификатор болезней) 
     * (Required)
     * 
     */
    @JsonProperty("MKB")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u041c\u041a\u0411-10 (\u041c\u0435\u0436\u0434\u0443\u043d\u0430\u0440\u043e\u0434\u043d\u044b\u0439 \u043a\u043b\u0430\u0441\u0441\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u0431\u043e\u043b\u0435\u0437\u043d\u0435\u0439) ")
    private MKB mKB;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5156472794421706606L;

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
     * Справочник источников финансирования
     * (Required)
     * 
     */
    @JsonProperty("type")
    public RbDiagnosisType getType() {
        return type;
    }

    /**
     * Справочник источников финансирования
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(RbDiagnosisType type) {
        this.type = type;
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
        return new HashCodeBuilder().append(id).append(type).append(mKB).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(mKB, rhs.mKB).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
