
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
import org.joda.time.DateTime;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTreatment;


/**
 * VMPTicket
 * <p>
 * Талон ВМП
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "number",
    "begDate",
    "endDate",
    "treatment"
})
public class VMPTicket implements Serializable
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
     * номер талона
     * (Required)
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u0440 \u0442\u0430\u043b\u043e\u043d\u0430")
    private String number;
    /**
     * дата и время начала действия талона
     * 
     */
    @JsonProperty("begDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043d\u0430\u0447\u0430\u043b\u0430 \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0442\u0430\u043b\u043e\u043d\u0430")
    private DateTime begDate;
    /**
     * дата и время окончания действия талона
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0442\u0430\u043b\u043e\u043d\u0430")
    private DateTime endDate;
    /**
     * RbTreatment
     * <p>
     * Справочник Методы лечения (ВМП)
     * 
     */
    @JsonProperty("treatment")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u041c\u0435\u0442\u043e\u0434\u044b \u043b\u0435\u0447\u0435\u043d\u0438\u044f (\u0412\u041c\u041f)")
    private RbTreatment treatment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 8363359112178761410L;

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
     * номер талона
     * (Required)
     * 
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * номер талона
     * (Required)
     * 
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * дата и время начала действия талона
     * 
     */
    @JsonProperty("begDate")
    public DateTime getBegDate() {
        return begDate;
    }

    /**
     * дата и время начала действия талона
     * 
     */
    @JsonProperty("begDate")
    public void setBegDate(DateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * дата и время окончания действия талона
     * 
     */
    @JsonProperty("endDate")
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * дата и время окончания действия талона
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * RbTreatment
     * <p>
     * Справочник Методы лечения (ВМП)
     * 
     */
    @JsonProperty("treatment")
    public RbTreatment getTreatment() {
        return treatment;
    }

    /**
     * RbTreatment
     * <p>
     * Справочник Методы лечения (ВМП)
     * 
     */
    @JsonProperty("treatment")
    public void setTreatment(RbTreatment treatment) {
        this.treatment = treatment;
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
        return new HashCodeBuilder().append(id).append(number).append(begDate).append(endDate).append(treatment).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VMPTicket) == false) {
            return false;
        }
        VMPTicket rhs = ((VMPTicket) other);
        return new EqualsBuilder().append(id, rhs.id).append(number, rhs.number).append(begDate, rhs.begDate).append(endDate, rhs.endDate).append(treatment, rhs.treatment).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
