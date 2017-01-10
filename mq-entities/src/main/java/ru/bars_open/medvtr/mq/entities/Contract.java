
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
import org.joda.time.DateTime;
import ru.bars_open.medvtr.mq.entities.refbook.RbFinance;


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
     * Идентифкатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private Integer id;
    /**
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("payer")
    @JsonPropertyDescription("")
    private Person payer;
    /**
     * номер договора
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("")
    private String number;
    /**
     * дата+врем подписания договора
     * 
     */
    @JsonProperty("signDate")
    @JsonPropertyDescription("")
    private DateTime signDate;
    /**
     * дата+время начала действия договора
     * 
     */
    @JsonProperty("begDate")
    @JsonPropertyDescription("")
    private DateTime begDate;
    /**
     * дата+время окончания действия договора
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("")
    private DateTime endDate;
    /**
     * rbFinance
     * <p>
     * Справочник источников финансирования
     * 
     */
    @JsonProperty("finance")
    @JsonPropertyDescription("")
    private RbFinance finance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5881266353657333286L;

    /**
     * Идентифкатор
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
     * Идентифкатор
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
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     * @return
     *     The payer
     */
    @JsonProperty("payer")
    public Person getPayer() {
        return payer;
    }

    /**
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     * @param payer
     *     The payer
     */
    @JsonProperty("payer")
    public void setPayer(Person payer) {
        this.payer = payer;
    }

    /**
     * номер договора
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * номер договора
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * дата+врем подписания договора
     * 
     * @return
     *     The signDate
     */
    @JsonProperty("signDate")
    public DateTime getSignDate() {
        return signDate;
    }

    /**
     * дата+врем подписания договора
     * 
     * @param signDate
     *     The signDate
     */
    @JsonProperty("signDate")
    public void setSignDate(DateTime signDate) {
        this.signDate = signDate;
    }

    /**
     * дата+время начала действия договора
     * 
     * @return
     *     The begDate
     */
    @JsonProperty("begDate")
    public DateTime getBegDate() {
        return begDate;
    }

    /**
     * дата+время начала действия договора
     * 
     * @param begDate
     *     The begDate
     */
    @JsonProperty("begDate")
    public void setBegDate(DateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * дата+время окончания действия договора
     * 
     * @return
     *     The endDate
     */
    @JsonProperty("endDate")
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * дата+время окончания действия договора
     * 
     * @param endDate
     *     The endDate
     */
    @JsonProperty("endDate")
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * rbFinance
     * <p>
     * Справочник источников финансирования
     * 
     * @return
     *     The finance
     */
    @JsonProperty("finance")
    public RbFinance getFinance() {
        return finance;
    }

    /**
     * rbFinance
     * <p>
     * Справочник источников финансирования
     * 
     * @param finance
     *     The finance
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
