
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
import ru.bars_open.medvtr.mq.entities.base.refbook.RbBiomaterialType;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTestTubeType;
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;


/**
 * Забор тканей/биоматериала
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "event",
    "biomaterialType",
    "testTubeType",
    "amount",
    "datetimePlanned",
    "datetimeTaken",
    "status",
    "barcode",
    "note",
    "person"
})
public class Biomaterial implements Serializable
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
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    @JsonPropertyDescription("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435")
    private Event event;
    /**
     * Справочник типов ткани (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("biomaterialType")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0442\u0438\u043f\u043e\u0432 \u0442\u043a\u0430\u043d\u0438 (\u0434\u043b\u044f \u0431\u0438\u043e\u0437\u0430\u0431\u043e\u0440\u043e\u0432)")
    private RbBiomaterialType biomaterialType;
    /**
     * Справочник типов прорбирок и ёмкостей (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("testTubeType")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0442\u0438\u043f\u043e\u0432 \u043f\u0440\u043e\u0440\u0431\u0438\u0440\u043e\u043a \u0438 \u0451\u043c\u043a\u043e\u0441\u0442\u0435\u0439 (\u0434\u043b\u044f \u0431\u0438\u043e\u0437\u0430\u0431\u043e\u0440\u043e\u0432)")
    private RbTestTubeType testTubeType;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("amount")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit amount;
    /**
     * дата и время планируемого взятия тканей
     * (Required)
     * 
     */
    @JsonProperty("datetimePlanned")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043f\u043b\u0430\u043d\u0438\u0440\u0443\u0435\u043c\u043e\u0433\u043e \u0432\u0437\u044f\u0442\u0438\u044f \u0442\u043a\u0430\u043d\u0435\u0439")
    private LocalDateTime datetimePlanned;
    /**
     * дата и время фактического взятия тканей
     * 
     */
    @JsonProperty("datetimeTaken")
    @JsonPropertyDescription("\u0434\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u0444\u0430\u043a\u0442\u0438\u0447\u0435\u0441\u043a\u043e\u0433\u043e \u0432\u0437\u044f\u0442\u0438\u044f \u0442\u043a\u0430\u043d\u0435\u0439")
    private LocalDateTime datetimeTaken;
    /**
     * Статус биозабора
     * (Required)
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("\u0421\u0442\u0430\u0442\u0443\u0441 \u0431\u0438\u043e\u0437\u0430\u0431\u043e\u0440\u0430")
    private Biomaterial.Status status;
    /**
     * Баркод для этого биозабора (Штрихкод)
     * (Required)
     * 
     */
    @JsonProperty("barcode")
    @JsonPropertyDescription("\u0411\u0430\u0440\u043a\u043e\u0434 \u0434\u043b\u044f \u044d\u0442\u043e\u0433\u043e \u0431\u0438\u043e\u0437\u0430\u0431\u043e\u0440\u0430 (\u0428\u0442\u0440\u0438\u0445\u043a\u043e\u0434)")
    private Barcode barcode;
    /**
     * примечание
     * 
     */
    @JsonProperty("note")
    @JsonPropertyDescription("\u043f\u0440\u0438\u043c\u0435\u0447\u0430\u043d\u0438\u0435")
    private String note;
    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0430 \u041c\u0418\u0421 ()")
    private Person person;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 8367537359990838607L;

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
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Справочник типов ткани (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("biomaterialType")
    public RbBiomaterialType getBiomaterialType() {
        return biomaterialType;
    }

    /**
     * Справочник типов ткани (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("biomaterialType")
    public void setBiomaterialType(RbBiomaterialType biomaterialType) {
        this.biomaterialType = biomaterialType;
    }

    /**
     * Справочник типов прорбирок и ёмкостей (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("testTubeType")
    public RbTestTubeType getTestTubeType() {
        return testTubeType;
    }

    /**
     * Справочник типов прорбирок и ёмкостей (для биозаборов)
     * (Required)
     * 
     */
    @JsonProperty("testTubeType")
    public void setTestTubeType(RbTestTubeType testTubeType) {
        this.testTubeType = testTubeType;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public ValueAndUnit getAmount() {
        return amount;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public void setAmount(ValueAndUnit amount) {
        this.amount = amount;
    }

    /**
     * дата и время планируемого взятия тканей
     * (Required)
     * 
     */
    @JsonProperty("datetimePlanned")
    public LocalDateTime getDatetimePlanned() {
        return datetimePlanned;
    }

    /**
     * дата и время планируемого взятия тканей
     * (Required)
     * 
     */
    @JsonProperty("datetimePlanned")
    public void setDatetimePlanned(LocalDateTime datetimePlanned) {
        this.datetimePlanned = datetimePlanned;
    }

    /**
     * дата и время фактического взятия тканей
     * 
     */
    @JsonProperty("datetimeTaken")
    public LocalDateTime getDatetimeTaken() {
        return datetimeTaken;
    }

    /**
     * дата и время фактического взятия тканей
     * 
     */
    @JsonProperty("datetimeTaken")
    public void setDatetimeTaken(LocalDateTime datetimeTaken) {
        this.datetimeTaken = datetimeTaken;
    }

    /**
     * Статус биозабора
     * (Required)
     * 
     */
    @JsonProperty("status")
    public Biomaterial.Status getStatus() {
        return status;
    }

    /**
     * Статус биозабора
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(Biomaterial.Status status) {
        this.status = status;
    }

    /**
     * Баркод для этого биозабора (Штрихкод)
     * (Required)
     * 
     */
    @JsonProperty("barcode")
    public Barcode getBarcode() {
        return barcode;
    }

    /**
     * Баркод для этого биозабора (Штрихкод)
     * (Required)
     * 
     */
    @JsonProperty("barcode")
    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    /**
     * примечание
     * 
     */
    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    /**
     * примечание
     * 
     */
    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    /**
     * Описание пациента МИС ()
     * 
     */
    @JsonProperty("person")
    public void setPerson(Person person) {
        this.person = person;
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
        return new HashCodeBuilder().append(id).append(event).append(biomaterialType).append(testTubeType).append(amount).append(datetimePlanned).append(datetimeTaken).append(status).append(barcode).append(note).append(person).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Biomaterial) == false) {
            return false;
        }
        Biomaterial rhs = ((Biomaterial) other);
        return new EqualsBuilder().append(id, rhs.id).append(event, rhs.event).append(biomaterialType, rhs.biomaterialType).append(testTubeType, rhs.testTubeType).append(amount, rhs.amount).append(datetimePlanned, rhs.datetimePlanned).append(datetimeTaken, rhs.datetimeTaken).append(status, rhs.status).append(barcode, rhs.barcode).append(note, rhs.note).append(person, rhs.person).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public enum Status {

        WAIT("WAIT"),
        IN_PROGRESS("IN_PROGRESS"),
        FINISH("FINISH"),
        SENT("SENT"),
        FAILED("FAILED");
        private final String value;
        private final static Map<String, Biomaterial.Status> CONSTANTS = new HashMap<String, Biomaterial.Status>();

        static {
            for (Biomaterial.Status c: values()) {
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
        public static Biomaterial.Status fromValue(String value) {
            Biomaterial.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
