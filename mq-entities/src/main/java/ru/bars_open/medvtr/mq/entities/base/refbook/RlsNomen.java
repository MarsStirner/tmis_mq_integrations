
package ru.bars_open.medvtr.mq.entities.base.refbook;

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
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;


/**
 * RlsNomen
 * <p>
 * Справочник РЛС
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "actMatters",
    "tradeName",
    "form",
    "packaging",
    "filling",
    "unit",
    "dose",
    "drugLifeTime"
})
public class RlsNomen implements Serializable
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
     * RlsActMatters
     * <p>
     * РЛС Наименование Действующих Средств
     * 
     */
    @JsonProperty("actMatters")
    @JsonPropertyDescription("\u0420\u041b\u0421 \u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0414\u0435\u0439\u0441\u0442\u0432\u0443\u044e\u0449\u0438\u0445 \u0421\u0440\u0435\u0434\u0441\u0442\u0432")
    private RlsActMatters actMatters;
    /**
     * RlsTradeName
     * <p>
     * РЛС Торговые наименования препаратов
     * (Required)
     * 
     */
    @JsonProperty("tradeName")
    @JsonPropertyDescription("\u0420\u041b\u0421 \u0422\u043e\u0440\u0433\u043e\u0432\u044b\u0435 \u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u044f \u043f\u0440\u0435\u043f\u0430\u0440\u0430\u0442\u043e\u0432")
    private RlsTradeName tradeName;
    /**
     * RlsForm
     * <p>
     * РЛС Форма выпуска ЛС
     * 
     */
    @JsonProperty("form")
    @JsonPropertyDescription("\u0420\u041b\u0421 \u0424\u043e\u0440\u043c\u0430 \u0432\u044b\u043f\u0443\u0441\u043a\u0430 \u041b\u0421")
    private RlsForm form;
    /**
     * RlsPackaging
     * <p>
     * РЛС Форма упаковки
     * 
     */
    @JsonProperty("packaging")
    @JsonPropertyDescription("\u0420\u041b\u0421 \u0424\u043e\u0440\u043c\u0430 \u0443\u043f\u0430\u043a\u043e\u0432\u043a\u0438")
    private RlsPackaging packaging;
    /**
     * RlsFilling
     * <p>
     * РЛС Форма фасовки ЛС
     * 
     */
    @JsonProperty("filling")
    @JsonPropertyDescription("\u0420\u041b\u0421 \u0424\u043e\u0440\u043c\u0430 \u0444\u0430\u0441\u043e\u0432\u043a\u0438 \u041b\u0421")
    private RlsFilling filling;
    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * 
     */
    @JsonProperty("unit")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0415\u0434\u0438\u043d\u0438\u0446 \u0418\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f")
    private RbUnit unit;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * 
     */
    @JsonProperty("dose")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit dose;
    /**
     * Срок годности препарата в месяцах
     * 
     */
    @JsonProperty("drugLifeTime")
    @JsonPropertyDescription("\u0421\u0440\u043e\u043a \u0433\u043e\u0434\u043d\u043e\u0441\u0442\u0438 \u043f\u0440\u0435\u043f\u0430\u0440\u0430\u0442\u0430 \u0432 \u043c\u0435\u0441\u044f\u0446\u0430\u0445")
    private Integer drugLifeTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7975577162723082492L;

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
     * RlsActMatters
     * <p>
     * РЛС Наименование Действующих Средств
     * 
     */
    @JsonProperty("actMatters")
    public RlsActMatters getActMatters() {
        return actMatters;
    }

    /**
     * RlsActMatters
     * <p>
     * РЛС Наименование Действующих Средств
     * 
     */
    @JsonProperty("actMatters")
    public void setActMatters(RlsActMatters actMatters) {
        this.actMatters = actMatters;
    }

    /**
     * RlsTradeName
     * <p>
     * РЛС Торговые наименования препаратов
     * (Required)
     * 
     */
    @JsonProperty("tradeName")
    public RlsTradeName getTradeName() {
        return tradeName;
    }

    /**
     * RlsTradeName
     * <p>
     * РЛС Торговые наименования препаратов
     * (Required)
     * 
     */
    @JsonProperty("tradeName")
    public void setTradeName(RlsTradeName tradeName) {
        this.tradeName = tradeName;
    }

    /**
     * RlsForm
     * <p>
     * РЛС Форма выпуска ЛС
     * 
     */
    @JsonProperty("form")
    public RlsForm getForm() {
        return form;
    }

    /**
     * RlsForm
     * <p>
     * РЛС Форма выпуска ЛС
     * 
     */
    @JsonProperty("form")
    public void setForm(RlsForm form) {
        this.form = form;
    }

    /**
     * RlsPackaging
     * <p>
     * РЛС Форма упаковки
     * 
     */
    @JsonProperty("packaging")
    public RlsPackaging getPackaging() {
        return packaging;
    }

    /**
     * RlsPackaging
     * <p>
     * РЛС Форма упаковки
     * 
     */
    @JsonProperty("packaging")
    public void setPackaging(RlsPackaging packaging) {
        this.packaging = packaging;
    }

    /**
     * RlsFilling
     * <p>
     * РЛС Форма фасовки ЛС
     * 
     */
    @JsonProperty("filling")
    public RlsFilling getFilling() {
        return filling;
    }

    /**
     * RlsFilling
     * <p>
     * РЛС Форма фасовки ЛС
     * 
     */
    @JsonProperty("filling")
    public void setFilling(RlsFilling filling) {
        this.filling = filling;
    }

    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * 
     */
    @JsonProperty("unit")
    public RbUnit getUnit() {
        return unit;
    }

    /**
     * RbUnit
     * <p>
     * Справочник Единиц Измерения
     * 
     */
    @JsonProperty("unit")
    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
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
     * 
     */
    @JsonProperty("dose")
    public void setDose(ValueAndUnit dose) {
        this.dose = dose;
    }

    /**
     * Срок годности препарата в месяцах
     * 
     */
    @JsonProperty("drugLifeTime")
    public Integer getDrugLifeTime() {
        return drugLifeTime;
    }

    /**
     * Срок годности препарата в месяцах
     * 
     */
    @JsonProperty("drugLifeTime")
    public void setDrugLifeTime(Integer drugLifeTime) {
        this.drugLifeTime = drugLifeTime;
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
        return new HashCodeBuilder().append(id).append(actMatters).append(tradeName).append(form).append(packaging).append(filling).append(unit).append(dose).append(drugLifeTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RlsNomen) == false) {
            return false;
        }
        RlsNomen rhs = ((RlsNomen) other);
        return new EqualsBuilder().append(id, rhs.id).append(actMatters, rhs.actMatters).append(tradeName, rhs.tradeName).append(form, rhs.form).append(packaging, rhs.packaging).append(filling, rhs.filling).append(unit, rhs.unit).append(dose, rhs.dose).append(drugLifeTime, rhs.drugLifeTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
