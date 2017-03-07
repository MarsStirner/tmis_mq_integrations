
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Обращение на лечение
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "externalId",
    "type",
    "setDate",
    "endDate",
    "client",
    "orgStructure",
    "doctor",
    "contract",
    "diagnosis",
    "vmpTicket"
})
public class Event implements Serializable
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
     * номер обращения
     * (Required)
     * 
     */
    @JsonProperty("externalId")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u0440 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private String externalId;
    /**
     * Тип обращения на лечение
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("\u0422\u0438\u043f \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435")
    private EventType type;
    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0441\u043e\u0437\u0434\u0430\u043d\u0438\u044f \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private LocalDateTime setDate;
    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u044f \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private LocalDateTime endDate;
    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person client;
    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructure")
    @JsonPropertyDescription("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435 \u0431\u043e\u043b\u044c\u043d\u0438\u0446\u044b")
    private OrgStructure orgStructure;
    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("doctor")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person doctor;
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
     * Диагнозы в рамках этого обращения
     * 
     */
    @JsonProperty("diagnosis")
    @JsonPropertyDescription("\u0414\u0438\u0430\u0433\u043d\u043e\u0437\u044b \u0432 \u0440\u0430\u043c\u043a\u0430\u0445 \u044d\u0442\u043e\u0433\u043e \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u044f")
    private List<Diagnosis> diagnosis = new ArrayList<Diagnosis>();
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
    private final static long serialVersionUID = 4203950882940039764L;

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
     * Тип обращения на лечение
     * (Required)
     * 
     */
    @JsonProperty("type")
    public EventType getType() {
        return type;
    }

    /**
     * Тип обращения на лечение
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(EventType type) {
        this.type = type;
    }

    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    public LocalDateTime getSetDate() {
        return setDate;
    }

    /**
     * дата создания обращения
     * 
     */
    @JsonProperty("setDate")
    public void setSetDate(LocalDateTime setDate) {
        this.setDate = setDate;
    }

    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * дата закрытия обращения
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    public Person getClient() {
        return client;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("client")
    public void setClient(Person client) {
        this.client = client;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructure")
    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructure")
    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("doctor")
    public Person getDoctor() {
        return doctor;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("doctor")
    public void setDoctor(Person doctor) {
        this.doctor = doctor;
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
     * Диагнозы в рамках этого обращения
     * 
     */
    @JsonProperty("diagnosis")
    public List<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    /**
     * Диагнозы в рамках этого обращения
     * 
     */
    @JsonProperty("diagnosis")
    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
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
        return new HashCodeBuilder().append(id).append(externalId).append(type).append(setDate).append(endDate).append(client).append(orgStructure).append(doctor).append(contract).append(diagnosis).append(vmpTicket).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(externalId, rhs.externalId).append(type, rhs.type).append(setDate, rhs.setDate).append(endDate, rhs.endDate).append(client, rhs.client).append(orgStructure, rhs.orgStructure).append(doctor, rhs.doctor).append(contract, rhs.contract).append(diagnosis, rhs.diagnosis).append(vmpTicket, rhs.vmpTicket).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
