
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
 * Справочник МКБ-10 (Международный классификатор болезней) 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "diagId",
    "diagName",
    "blockName",
    "className"
})
public class MKB implements Serializable
{

    /**
     * Идентификатор диагноза
     * 
     */
    @JsonProperty("diagId")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u0430")
    private String diagId;
    /**
     * Название диагноза
     * 
     */
    @JsonProperty("diagName")
    @JsonPropertyDescription("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u0430")
    private String diagName;
    /**
     * Наименование блока диагнозов
     * 
     */
    @JsonProperty("blockName")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0431\u043b\u043e\u043a\u0430 \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u043e\u0432")
    private String blockName;
    /**
     * Наименование класса диагнозов
     * 
     */
    @JsonProperty("className")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043b\u0430\u0441\u0441\u0430 \u0434\u0438\u0430\u0433\u043d\u043e\u0437\u043e\u0432")
    private String className;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3170809385989235342L;

    /**
     * Идентификатор диагноза
     * 
     */
    @JsonProperty("diagId")
    public String getDiagId() {
        return diagId;
    }

    /**
     * Идентификатор диагноза
     * 
     */
    @JsonProperty("diagId")
    public void setDiagId(String diagId) {
        this.diagId = diagId;
    }

    /**
     * Название диагноза
     * 
     */
    @JsonProperty("diagName")
    public String getDiagName() {
        return diagName;
    }

    /**
     * Название диагноза
     * 
     */
    @JsonProperty("diagName")
    public void setDiagName(String diagName) {
        this.diagName = diagName;
    }

    /**
     * Наименование блока диагнозов
     * 
     */
    @JsonProperty("blockName")
    public String getBlockName() {
        return blockName;
    }

    /**
     * Наименование блока диагнозов
     * 
     */
    @JsonProperty("blockName")
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    /**
     * Наименование класса диагнозов
     * 
     */
    @JsonProperty("className")
    public String getClassName() {
        return className;
    }

    /**
     * Наименование класса диагнозов
     * 
     */
    @JsonProperty("className")
    public void setClassName(String className) {
        this.className = className;
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
        return new HashCodeBuilder().append(diagId).append(diagName).append(blockName).append(className).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MKB) == false) {
            return false;
        }
        MKB rhs = ((MKB) other);
        return new EqualsBuilder().append(diagId, rhs.diagId).append(diagName, rhs.diagName).append(blockName, rhs.blockName).append(className, rhs.className).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
