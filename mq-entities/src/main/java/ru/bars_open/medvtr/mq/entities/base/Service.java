
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
import ru.bars_open.medvtr.mq.entities.base.refbook.RbService;


/**
 * Service
 * <p>
 * Счет на оплату
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "isAccumulativePrice",
    "service"
})
public class Service implements Serializable
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
     * номенклатурный номер услуги
     * (Required)
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u043d\u043a\u043b\u0430\u0442\u0443\u0440\u043d\u044b\u0439 \u043d\u043e\u043c\u0435\u0440 \u0443\u0441\u043b\u0443\u0433\u0438")
    private String code;
    /**
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("\u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0443\u0441\u043b\u0443\u0433\u0438")
    private String name;
    /**
     * стоимость услуги является накопительной; для комплексных услуг итоговая стоимость может рассчитываться по стоимостям услуг, входящих в эту группу услуг, по соответствующим позициям прайс-листа
     * (Required)
     * 
     */
    @JsonProperty("isAccumulativePrice")
    @JsonPropertyDescription("\u0441\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c \u0443\u0441\u043b\u0443\u0433\u0438 \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u043d\u0430\u043a\u043e\u043f\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0439; \u0434\u043b\u044f \u043a\u043e\u043c\u043f\u043b\u0435\u043a\u0441\u043d\u044b\u0445 \u0443\u0441\u043b\u0443\u0433 \u0438\u0442\u043e\u0433\u043e\u0432\u0430\u044f \u0441\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c \u043c\u043e\u0436\u0435\u0442 \u0440\u0430\u0441\u0441\u0447\u0438\u0442\u044b\u0432\u0430\u0442\u044c\u0441\u044f \u043f\u043e \u0441\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044f\u043c \u0443\u0441\u043b\u0443\u0433, \u0432\u0445\u043e\u0434\u044f\u0449\u0438\u0445 \u0432 \u044d\u0442\u0443 \u0433\u0440\u0443\u043f\u043f\u0443 \u0443\u0441\u043b\u0443\u0433, \u043f\u043e \u0441\u043e\u043e\u0442\u0432\u0435\u0442\u0441\u0442\u0432\u0443\u044e\u0449\u0438\u043c \u043f\u043e\u0437\u0438\u0446\u0438\u044f\u043c \u043f\u0440\u0430\u0439\u0441-\u043b\u0438\u0441\u0442\u0430")
    private Boolean isAccumulativePrice;
    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("service")
    @JsonPropertyDescription("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a \u0442\u0435\u0441\u0442\u043e\u0432 \u0422\u041c\u0418\u0421")
    private RbService service;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1745018299913066224L;

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
     * номенклатурный номер услуги
     * (Required)
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * номенклатурный номер услуги
     * (Required)
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * стоимость услуги является накопительной; для комплексных услуг итоговая стоимость может рассчитываться по стоимостям услуг, входящих в эту группу услуг, по соответствующим позициям прайс-листа
     * (Required)
     * 
     */
    @JsonProperty("isAccumulativePrice")
    public Boolean getIsAccumulativePrice() {
        return isAccumulativePrice;
    }

    /**
     * стоимость услуги является накопительной; для комплексных услуг итоговая стоимость может рассчитываться по стоимостям услуг, входящих в эту группу услуг, по соответствующим позициям прайс-листа
     * (Required)
     * 
     */
    @JsonProperty("isAccumulativePrice")
    public void setIsAccumulativePrice(Boolean isAccumulativePrice) {
        this.isAccumulativePrice = isAccumulativePrice;
    }

    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("service")
    public RbService getService() {
        return service;
    }

    /**
     * Справочник тестов ТМИС
     * (Required)
     * 
     */
    @JsonProperty("service")
    public void setService(RbService service) {
        this.service = service;
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
        return new HashCodeBuilder().append(id).append(code).append(name).append(isAccumulativePrice).append(service).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Service) == false) {
            return false;
        }
        Service rhs = ((Service) other);
        return new EqualsBuilder().append(id, rhs.id).append(code, rhs.code).append(name, rhs.name).append(isAccumulativePrice, rhs.isAccumulativePrice).append(service, rhs.service).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
