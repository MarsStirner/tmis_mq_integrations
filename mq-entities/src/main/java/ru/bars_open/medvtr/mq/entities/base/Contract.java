
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
import ru.bars_open.medvtr.mq.entities.base.refbook.RbFinance;


/**
 * Contract
 * <p>
 * Договор на лечение (контракт)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "payer",
    "number",
    "signDate",
    "begDate",
    "endDate",
    "finance"
})
public class Contract implements Serializable
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
     * Contract
     * <p>
     * Контрагент (физ или юр лицо)
     * (Required)
     * 
     */
    @JsonProperty("payer")
    @JsonPropertyDescription("\u041a\u043e\u043d\u0442\u0440\u0430\u0433\u0435\u043d\u0442 (\u0444\u0438\u0437 \u0438\u043b\u0438 \u044e\u0440 \u043b\u0438\u0446\u043e)")
    private Contragent payer;
    /**
     * номер договора
     * (Required)
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u0440 \u0434\u043e\u0433\u043e\u0432\u043e\u0440\u0430")
    private String number;
    /**
     * дата+врем подписания договора
     * 
     */
    @JsonProperty("signDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430+\u0432\u0440\u0435\u043c \u043f\u043e\u0434\u043f\u0438\u0441\u0430\u043d\u0438\u044f \u0434\u043e\u0433\u043e\u0432\u043e\u0440\u0430")
    private LocalDateTime signDate;
    /**
     * дата+время начала действия договора
     * 
     */
    @JsonProperty("begDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430+\u0432\u0440\u0435\u043c\u044f \u043d\u0430\u0447\u0430\u043b\u0430 \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0434\u043e\u0433\u043e\u0432\u043e\u0440\u0430")
    private LocalDateTime begDate;
    /**
     * дата+время окончания действия договора
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430+\u0432\u0440\u0435\u043c\u044f \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0434\u043e\u0433\u043e\u0432\u043e\u0440\u0430")
    private LocalDateTime endDate;
    /**
     * RbFinance
     * <p>
     * Справочник источников финансирования
     * 
     */
    @JsonProperty("finance")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0438\u0441\u0442\u043e\u0447\u043d\u0438\u043a\u043e\u0432 \u0444\u0438\u043d\u0430\u043d\u0441\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f")
    private RbFinance finance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5405440341103564892L;

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
     * Contract
     * <p>
     * Контрагент (физ или юр лицо)
     * (Required)
     * 
     */
    @JsonProperty("payer")
    public Contragent getPayer() {
        return payer;
    }

    /**
     * Contract
     * <p>
     * Контрагент (физ или юр лицо)
     * (Required)
     * 
     */
    @JsonProperty("payer")
    public void setPayer(Contragent payer) {
        this.payer = payer;
    }

    /**
     * номер договора
     * (Required)
     * 
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * номер договора
     * (Required)
     * 
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * дата+врем подписания договора
     * 
     */
    @JsonProperty("signDate")
    public LocalDateTime getSignDate() {
        return signDate;
    }

    /**
     * дата+врем подписания договора
     * 
     */
    @JsonProperty("signDate")
    public void setSignDate(LocalDateTime signDate) {
        this.signDate = signDate;
    }

    /**
     * дата+время начала действия договора
     * 
     */
    @JsonProperty("begDate")
    public LocalDateTime getBegDate() {
        return begDate;
    }

    /**
     * дата+время начала действия договора
     * 
     */
    @JsonProperty("begDate")
    public void setBegDate(LocalDateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * дата+время окончания действия договора
     * 
     */
    @JsonProperty("endDate")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * дата+время окончания действия договора
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * RbFinance
     * <p>
     * Справочник источников финансирования
     * 
     */
    @JsonProperty("finance")
    public RbFinance getFinance() {
        return finance;
    }

    /**
     * RbFinance
     * <p>
     * Справочник источников финансирования
     * 
     */
    @JsonProperty("finance")
    public void setFinance(RbFinance finance) {
        this.finance = finance;
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
        return new HashCodeBuilder().append(id).append(payer).append(number).append(signDate).append(begDate).append(endDate).append(finance).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Contract) == false) {
            return false;
        }
        Contract rhs = ((Contract) other);
        return new EqualsBuilder().append(id, rhs.id).append(payer, rhs.payer).append(number, rhs.number).append(signDate, rhs.signDate).append(begDate, rhs.begDate).append(endDate, rhs.endDate).append(finance, rhs.finance).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
