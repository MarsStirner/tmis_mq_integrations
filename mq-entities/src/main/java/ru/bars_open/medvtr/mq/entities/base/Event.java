
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


/**
 * Event
 * <p>
 * Обращение на лечение
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "setDate",
    "externalId",
    "endDate",
    "client",
    "contract",
    "vmpTicket"
})
public class Event implements Serializable
{

    /**
     * Идентифкатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u043a\u0430\u0442\u043e\u0440")
    private Integer id;
    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0441\u043e\u0437\u0434\u0430\u043d\u0438\u044f \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private DateTime setDate;
    /**
     * номер обращения
     * (Required)
     * 
     */
    @JsonProperty("externalId")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u0440 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private String externalId;
    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u044f \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private DateTime endDate;
    /**
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person client;
    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    @JsonPropertyDescription("\u0414\u043e\u0433\u043e\u0432\u043e\u0440 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435 (\u043a\u043e\u043d\u0442\u0440\u0430\u043a\u0442)")
    private Contract contract;
    /**
     * VMPTicket
     * <p>
     * Талон ВМП
     * 
     */
    @JsonProperty("vmpTicket")
    @JsonPropertyDescription("\u0422\u0430\u043b\u043e\u043d \u0412\u041c\u041f")
    private VMPTicket vmpTicket;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 8252230292572864404L;

    /**
     * Идентифкатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    public DateTime getSetDate() {
        return setDate;
    }

    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    public void setSetDate(DateTime setDate) {
        this.setDate = setDate;
    }

    /**
     * номер обращения
     * (Required)
     * 
     */
    @JsonProperty("externalId")
    public String getExternalId() {
        return externalId;
    }

    /**
     * номер обращения
     * (Required)
     * 
     */
    @JsonProperty("externalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    public Person getClient() {
        return client;
    }

    /**
     * person
     * <p>
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    public void setClient(Person client) {
        this.client = client;
    }

    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    public Contract getContract() {
        return contract;
    }

    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * VMPTicket
     * <p>
     * Талон ВМП
     * 
     */
    @JsonProperty("vmpTicket")
    public VMPTicket getVmpTicket() {
        return vmpTicket;
    }

    /**
     * VMPTicket
     * <p>
     * Талон ВМП
     * 
     */
    @JsonProperty("vmpTicket")
    public void setVmpTicket(VMPTicket vmpTicket) {
        this.vmpTicket = vmpTicket;
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
        return new HashCodeBuilder().append(id).append(setDate).append(externalId).append(endDate).append(client).append(contract).append(vmpTicket).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().append(id, rhs.id).append(setDate, rhs.setDate).append(externalId, rhs.externalId).append(endDate, rhs.endDate).append(client, rhs.client).append(contract, rhs.contract).append(vmpTicket, rhs.vmpTicket).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
