
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
 * OrgStructure
 * <p>
 * Отделение больницы
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name"
})
public class OrgStructure implements Serializable
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
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("")
    private String code;
    /**
     * Наименование записи справочника
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1898801060313283049L;

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
     * Код записи справочника
     * (Required)
     * 
     * @return
     *     The code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * Код записи справочника
     * (Required)
     * 
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Наименование записи справочника
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Наименование записи справочника
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
        return new HashCodeBuilder().append(id).append(code).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrgStructure) == false) {
            return false;
        }
        OrgStructure rhs = ((OrgStructure) other);
        return new EqualsBuilder().append(id, rhs.id).append(code, rhs.code).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
