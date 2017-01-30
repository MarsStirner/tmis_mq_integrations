
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


/**
 * Тип экшена
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "flatCode",
    "mnemonic"
})
public class ActionType implements Serializable
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
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("\u041a\u043e\u0434 \u0437\u0430\u043f\u0438\u0441\u0438 \u0441\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0430")
    private String code;
    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0437\u0430\u043f\u0438\u0441\u0438 \u0441\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0430")
    private String name;
    /**
     * Плоский код
     * (Required)
     * 
     */
    @JsonProperty("flatCode")
    @JsonPropertyDescription("\u041f\u043b\u043e\u0441\u043a\u0438\u0439 \u043a\u043e\u0434")
    private String flatCode;
    /**
     * Мнемоника
     * 
     */
    @JsonProperty("mnemonic")
    @JsonPropertyDescription("\u041c\u043d\u0435\u043c\u043e\u043d\u0438\u043a\u0430")
    private String mnemonic;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 875228229479005496L;

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
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Плоский код
     * (Required)
     * 
     */
    @JsonProperty("flatCode")
    public String getFlatCode() {
        return flatCode;
    }

    /**
     * Плоский код
     * (Required)
     * 
     */
    @JsonProperty("flatCode")
    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    /**
     * Мнемоника
     * 
     */
    @JsonProperty("mnemonic")
    public String getMnemonic() {
        return mnemonic;
    }

    /**
     * Мнемоника
     * 
     */
    @JsonProperty("mnemonic")
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
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
        return new HashCodeBuilder().append(id).append(code).append(name).append(flatCode).append(mnemonic).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActionType) == false) {
            return false;
        }
        ActionType rhs = ((ActionType) other);
        return new EqualsBuilder().append(id, rhs.id).append(code, rhs.code).append(name, rhs.name).append(flatCode, rhs.flatCode).append(mnemonic, rhs.mnemonic).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
