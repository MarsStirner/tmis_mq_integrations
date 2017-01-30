
package ru.bars_open.medvtr.mq.entities.action;

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
import ru.bars_open.medvtr.mq.entities.base.OrgStructure;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ActionStatus;


/**
 * StationaryReceived
 * <p>
 * Экшен-поступления в стационаре
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "begDate",
    "endDate",
    "orgStructStay",
    "orgStructDirection"
})
public class StationaryReceived implements Serializable
{

    /**
     * Идентификатор экшена
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u044d\u043a\u0448\u0435\u043d\u0430")
    private Integer id;
    @JsonProperty("status")
    private ActionStatus status;
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
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructStay")
    @JsonPropertyDescription("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435 \u0431\u043e\u043b\u044c\u043d\u0438\u0446\u044b")
    private OrgStructure orgStructStay;
    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructDirection")
    @JsonPropertyDescription("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435 \u0431\u043e\u043b\u044c\u043d\u0438\u0446\u044b")
    private OrgStructure orgStructDirection;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4113552155750196072L;

    /**
     * Идентификатор экшена
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентификатор экшена
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("status")
    public ActionStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(ActionStatus status) {
        this.status = status;
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
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructStay")
    public OrgStructure getOrgStructStay() {
        return orgStructStay;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructStay")
    public void setOrgStructStay(OrgStructure orgStructStay) {
        this.orgStructStay = orgStructStay;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructDirection")
    public OrgStructure getOrgStructDirection() {
        return orgStructDirection;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructDirection")
    public void setOrgStructDirection(OrgStructure orgStructDirection) {
        this.orgStructDirection = orgStructDirection;
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
        return new HashCodeBuilder().append(id).append(status).append(begDate).append(endDate).append(orgStructStay).append(orgStructDirection).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StationaryReceived) == false) {
            return false;
        }
        StationaryReceived rhs = ((StationaryReceived) other);
        return new EqualsBuilder().append(id, rhs.id).append(status, rhs.status).append(begDate, rhs.begDate).append(endDate, rhs.endDate).append(orgStructStay, rhs.orgStructStay).append(orgStructDirection, rhs.orgStructDirection).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
