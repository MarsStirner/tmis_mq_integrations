
package ru.bars_open.medvtr.mq.entities.action;

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
import ru.bars_open.medvtr.mq.entities.base.OrgStructure;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ActionStatus;


/**
 * StationaryMoving
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
    "orgStructReceived",
    "orgStructStay"
})
public class StationaryMoving implements Serializable
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
    private LocalDateTime begDate;
    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    @JsonPropertyDescription("\u0414\u0430\u0442\u0430 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0440\u0430\u0431\u043e\u0442\u044b")
    private LocalDateTime endDate;
    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructReceived")
    @JsonPropertyDescription("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435 \u0431\u043e\u043b\u044c\u043d\u0438\u0446\u044b")
    private OrgStructure orgStructReceived;
    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructStay")
    @JsonPropertyDescription("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435 \u0431\u043e\u043b\u044c\u043d\u0438\u0446\u044b")
    private OrgStructure orgStructStay;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -9002709540997437455L;

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
    public LocalDateTime getBegDate() {
        return begDate;
    }

    /**
     * Дата начала работы
     * 
     */
    @JsonProperty("begDate")
    public void setBegDate(LocalDateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Дата окончания работы
     * 
     */
    @JsonProperty("endDate")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructReceived")
    public OrgStructure getOrgStructReceived() {
        return orgStructReceived;
    }

    /**
     * OrgStructure
     * <p>
     * Отделение больницы
     * 
     */
    @JsonProperty("orgStructReceived")
    public void setOrgStructReceived(OrgStructure orgStructReceived) {
        this.orgStructReceived = orgStructReceived;
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
        return new HashCodeBuilder().append(id).append(status).append(begDate).append(endDate).append(orgStructReceived).append(orgStructStay).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StationaryMoving) == false) {
            return false;
        }
        StationaryMoving rhs = ((StationaryMoving) other);
        return new EqualsBuilder().append(id, rhs.id).append(status, rhs.status).append(begDate, rhs.begDate).append(endDate, rhs.endDate).append(orgStructReceived, rhs.orgStructReceived).append(orgStructStay, rhs.orgStructStay).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
