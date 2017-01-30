
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
 * Справочник типов прорбирок и ёмкостей (для биозаборов)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "volume",
    "color",
    "image"
})
public class RbTubeType implements Serializable
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
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("\u041a\u043e\u0434 \u0437\u0430\u043f\u0438\u0441\u0438 \u0441\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0430")
    private String code;
    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u041d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0437\u0430\u043f\u0438\u0441\u0438 \u0441\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0430")
    private String name;
    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("volume")
    @JsonPropertyDescription("\u0421\u0442\u0440\u0443\u043a\u0442\u0443\u0440\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u0449\u0430\u044f \u0435\u0434\u0438\u043d\u0438\u0446\u0443 \u0438\u0437\u043c\u0435\u0440\u0435\u043d\u0438\u044f \u0438 \u0441\u0430\u043c\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435")
    private ValueAndUnit volume;
    /**
     * Цветовое обозначение ёмкости
     * 
     */
    @JsonProperty("color")
    @JsonPropertyDescription("\u0426\u0432\u0435\u0442\u043e\u0432\u043e\u0435 \u043e\u0431\u043e\u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0451\u043c\u043a\u043e\u0441\u0442\u0438")
    private String color;
    /**
     * Изображение пробирки/ссылка на изображение
     * 
     */
    @JsonProperty("image")
    @JsonPropertyDescription("\u0418\u0437\u043e\u0431\u0440\u0430\u0436\u0435\u043d\u0438\u0435 \u043f\u0440\u043e\u0431\u0438\u0440\u043a\u0438/\u0441\u0441\u044b\u043b\u043a\u0430 \u043d\u0430 \u0438\u0437\u043e\u0431\u0440\u0430\u0436\u0435\u043d\u0438\u0435")
    private String image;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -903577990995275130L;

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
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * Код записи справочника
     * (Required)
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Наименование записи справочника
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("volume")
    public ValueAndUnit getVolume() {
        return volume;
    }

    /**
     * ValueAndUnit
     * <p>
     * Структура содержащая единицу измерения и само значение
     * (Required)
     * 
     */
    @JsonProperty("volume")
    public void setVolume(ValueAndUnit volume) {
        this.volume = volume;
    }

    /**
     * Цветовое обозначение ёмкости
     * 
     */
    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    /**
     * Цветовое обозначение ёмкости
     * 
     */
    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Изображение пробирки/ссылка на изображение
     * 
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     * Изображение пробирки/ссылка на изображение
     * 
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
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
        return new HashCodeBuilder().append(id).append(code).append(name).append(volume).append(color).append(image).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RbTubeType) == false) {
            return false;
        }
        RbTubeType rhs = ((RbTubeType) other);
        return new EqualsBuilder().append(id, rhs.id).append(code, rhs.code).append(name, rhs.name).append(volume, rhs.volume).append(color, rhs.color).append(image, rhs.image).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
