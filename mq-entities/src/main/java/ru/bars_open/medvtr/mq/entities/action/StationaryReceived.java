
package ru.bars_open.medvtr.mq.entities.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import ru.bars_open.medvtr.mq.entities.base.OrgStructure;


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
    /**
     * Статус экшена
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("\u0421\u0442\u0430\u0442\u0443\u0441 \u044d\u043a\u0448\u0435\u043d\u0430")
    private StationaryReceived.Status status;
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
    private final static long serialVersionUID = 253381931772335154L;

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

    /**
     * Статус экшена
     * 
     */
    @JsonProperty("status")
    public StationaryReceived.Status getStatus() {
        return status;
    }

    /**
     * Статус экшена
     * 
     */
    @JsonProperty("status")
    public void setStatus(StationaryReceived.Status status) {
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

    public enum Status {

        STARTED("STARTED"),
        WAIT("WAIT"),
        FINISHED("FINISHED");
        private final String value;
        private final static Map<String, StationaryReceived.Status> CONSTANTS = new HashMap<String, StationaryReceived.Status>();

        static {
            for (StationaryReceived.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static StationaryReceived.Status fromValue(String value) {
            StationaryReceived.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}