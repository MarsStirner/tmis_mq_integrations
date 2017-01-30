
package ru.bars_open.medvtr.mq.entities.action;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import ru.bars_open.medvtr.mq.entities.base.ActionType;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTest;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ActionStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Исследование с перечнем лабораторных тестов для исполнения
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "type",
    "isUrgent",
    "assigner",
    "begDate",
    "endDate",
    "tests"
})
public class Analysis implements Serializable
{

    /**
     * Идентификатор экшена
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u044d\u043a\u0448\u0435\u043d\u0430")
    private Integer id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    private ActionStatus status;
    /**
     * Тип экшена
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("\u0422\u0438\u043f \u044d\u043a\u0448\u0435\u043d\u0430")
    private ActionType type;
    /**
     * Флаг срочности исследования
     * (Required)
     * 
     */
    @JsonProperty("isUrgent")
    @JsonPropertyDescription("\u0424\u043b\u0430\u0433 \u0441\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u0438 \u0438\u0441\u0441\u043b\u0435\u0434\u043e\u0432\u0430\u043d\u0438\u044f")
    private Boolean isUrgent;
    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("assigner")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person assigner;
    /**
     * Дата начала работы
     * 
     */
    @JsonProperty("begDate")
    @JsonPropertyDescription("\u0414\u0430\u0442\u0430 \u043d\u0430\u0447\u0430\u043b\u0430 \u0440\u0430\u0431\u043e\u0442\u044b")
    private DateTime begDate;
    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0414\u0430\u0442\u0430 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0440\u0430\u0431\u043e\u0442\u044b")
    private DateTime endDate;
    /**
     * список тестов в рамках исследования
     * (Required)
     * 
     */
    @JsonProperty("tests")
    @JsonPropertyDescription("\u0441\u043f\u0438\u0441\u043e\u043a \u0442\u0435\u0441\u0442\u043e\u0432 \u0432 \u0440\u0430\u043c\u043a\u0430\u0445 \u0438\u0441\u0441\u043b\u0435\u0434\u043e\u0432\u0430\u043d\u0438\u044f")
    private List<RbTest> tests = new ArrayList<RbTest>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 9196112133692812332L;

    /**
     * Идентификатор экшена
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентификатор экшена
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    public ActionStatus getStatus() {
        return status;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(ActionStatus status) {
        this.status = status;
    }

    /**
     * Тип экшена
     * (Required)
     * 
     */
    @JsonProperty("type")
    public ActionType getType() {
        return type;
    }

    /**
     * Тип экшена
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(ActionType type) {
        this.type = type;
    }

    /**
     * Флаг срочности исследования
     * (Required)
     * 
     */
    @JsonProperty("isUrgent")
    public Boolean getIsUrgent() {
        return isUrgent;
    }

    /**
     * Флаг срочности исследования
     * (Required)
     * 
     */
    @JsonProperty("isUrgent")
    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("assigner")
    public Person getAssigner() {
        return assigner;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("assigner")
    public void setAssigner(Person assigner) {
        this.assigner = assigner;
    }

    /**
     * Дата начала работы
     * 
     */
    @JsonProperty("begDate")
    public DateTime getBegDate() {
        return begDate;
    }

    /**
     * Дата начала работы
     * 
     */
    @JsonProperty("begDate")
    public void setBegDate(DateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * список тестов в рамках исследования
     * (Required)
     * 
     */
    @JsonProperty("tests")
    public List<RbTest> getTests() {
        return tests;
    }

    /**
     * список тестов в рамках исследования
     * (Required)
     * 
     */
    @JsonProperty("tests")
    public void setTests(List<RbTest> tests) {
        this.tests = tests;
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
        return new HashCodeBuilder().append(id).append(status).append(type).append(isUrgent).append(assigner).append(begDate).append(endDate).append(tests).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Analysis) == false) {
            return false;
        }
        Analysis rhs = ((Analysis) other);
        return new EqualsBuilder().append(id, rhs.id).append(status, rhs.status).append(type, rhs.type).append(isUrgent, rhs.isUrgent).append(assigner, rhs.assigner).append(begDate, rhs.begDate).append(endDate, rhs.endDate).append(tests, rhs.tests).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
