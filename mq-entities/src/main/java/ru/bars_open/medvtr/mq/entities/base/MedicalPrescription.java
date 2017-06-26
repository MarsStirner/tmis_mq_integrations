
package ru.bars_open.medvtr.mq.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import ru.bars_open.medvtr.mq.entities.base.refbook.RlsNomen;
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;


/**
 * MedicalPrescription
 * <p>
 * Лекарственное назначение (ЛН)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "rls",
    "status",
    "dose",
    "frequency",
    "duration",
    "begDate",
    "note",
    "reasonOfCancel"
})
public class MedicalPrescription implements Serializable
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
     * RlsNomen
     * <p>
     * Справочник РЛС
     * (Required)
     * 
     */
    @JsonProperty("rls")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0420\u041b\u0421")
    private RlsNomen rls;
    /**
     * Статус назначения
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("\u0421\u0442\u0430\u0442\u0443\u0441 \u043d\u0430\u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f")
    private MedicalPrescription.Status status;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("dose")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit dose;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("frequency")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit frequency;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("duration")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit duration;
    /**
     * Дата начала ЛН
     * 
     */
    @JsonProperty("begDate")
    @JsonPropertyDescription("\u0414\u0430\u0442\u0430 \u043d\u0430\u0447\u0430\u043b\u0430 \u041b\u041d")
    private LocalDateTime begDate;
    /**
     * Примечание
     * 
     */
    @JsonProperty("note")
    @JsonPropertyDescription("\u041f\u0440\u0438\u043c\u0435\u0447\u0430\u043d\u0438\u0435")
    private String note;
    /**
     * Причина отмены
     * 
     */
    @JsonProperty("reasonOfCancel")
    @JsonPropertyDescription("\u041f\u0440\u0438\u0447\u0438\u043d\u0430 \u043e\u0442\u043c\u0435\u043d\u044b")
    private String reasonOfCancel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -663812241813056588L;

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
     * RlsNomen
     * <p>
     * Справочник РЛС
     * (Required)
     * 
     */
    @JsonProperty("rls")
    public RlsNomen getRls() {
        return rls;
    }

    /**
     * RlsNomen
     * <p>
     * Справочник РЛС
     * (Required)
     * 
     */
    @JsonProperty("rls")
    public void setRls(RlsNomen rls) {
        this.rls = rls;
    }

    /**
     * Статус назначения
     * 
     */
    @JsonProperty("status")
    public MedicalPrescription.Status getStatus() {
        return status;
    }

    /**
     * Статус назначения
     * 
     */
    @JsonProperty("status")
    public void setStatus(MedicalPrescription.Status status) {
        this.status = status;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("dose")
    public ValueAndUnit getDose() {
        return dose;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("dose")
    public void setDose(ValueAndUnit dose) {
        this.dose = dose;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("frequency")
    public ValueAndUnit getFrequency() {
        return frequency;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("frequency")
    public void setFrequency(ValueAndUnit frequency) {
        this.frequency = frequency;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("duration")
    public ValueAndUnit getDuration() {
        return duration;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("duration")
    public void setDuration(ValueAndUnit duration) {
        this.duration = duration;
    }

    /**
     * Дата начала ЛН
     * 
     */
    @JsonProperty("begDate")
    public LocalDateTime getBegDate() {
        return begDate;
    }

    /**
     * Дата начала ЛН
     * 
     */
    @JsonProperty("begDate")
    public void setBegDate(LocalDateTime begDate) {
        this.begDate = begDate;
    }

    /**
     * Примечание
     * 
     */
    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    /**
     * Примечание
     * 
     */
    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Причина отмены
     * 
     */
    @JsonProperty("reasonOfCancel")
    public String getReasonOfCancel() {
        return reasonOfCancel;
    }

    /**
     * Причина отмены
     * 
     */
    @JsonProperty("reasonOfCancel")
    public void setReasonOfCancel(String reasonOfCancel) {
        this.reasonOfCancel = reasonOfCancel;
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
        return new HashCodeBuilder().append(id).append(rls).append(status).append(dose).append(frequency).append(duration).append(begDate).append(note).append(reasonOfCancel).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MedicalPrescription) == false) {
            return false;
        }
        MedicalPrescription rhs = ((MedicalPrescription) other);
        return new EqualsBuilder().append(id, rhs.id).append(rls, rhs.rls).append(status, rhs.status).append(dose, rhs.dose).append(frequency, rhs.frequency).append(duration, rhs.duration).append(begDate, rhs.begDate).append(note, rhs.note).append(reasonOfCancel, rhs.reasonOfCancel).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public enum Status {

        STARTED("STARTED"),
        WAIT("WAIT"),
        FINISHED("FINISHED");
        private final String value;
        private final static Map<String, MedicalPrescription.Status> CONSTANTS = new HashMap<String, MedicalPrescription.Status>();

        static {
            for (MedicalPrescription.Status c: values()) {
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
        public static MedicalPrescription.Status fromValue(String value) {
            MedicalPrescription.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
