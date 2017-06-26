
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


/**
 * InvoiceItem
 * <p>
 * Позиция счета
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "parent_id",
    "service",
    "price",
    "amount",
    "sum_discount",
    "sum_total"
})
public class InvoiceItem implements Serializable
{

    /**
     * Идентификатор позиции счета
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u043f\u043e\u0437\u0438\u0446\u0438\u0438 \u0441\u0447\u0435\u0442\u0430")
    private Integer id;
    /**
     * идентификатор родительской позиции счета
     * 
     */
    @JsonProperty("parent_id")
    @JsonPropertyDescription("\u0438\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440 \u0440\u043e\u0434\u0438\u0442\u0435\u043b\u044c\u0441\u043a\u043e\u0439 \u043f\u043e\u0437\u0438\u0446\u0438\u0438 \u0441\u0447\u0435\u0442\u0430")
    private Integer parentId;
    /**
     * Service
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("service")
    @JsonPropertyDescription("\u0421\u0447\u0435\u0442 \u043d\u0430 \u043e\u043f\u043b\u0430\u0442\u0443")
    private Service service;
    /**
     * цена оказанной позиции счета (за штуку, без учета скидок)
     * (Required)
     * 
     */
    @JsonProperty("price")
    @JsonPropertyDescription("\u0446\u0435\u043d\u0430 \u043e\u043a\u0430\u0437\u0430\u043d\u043d\u043e\u0439 \u043f\u043e\u0437\u0438\u0446\u0438\u0438 \u0441\u0447\u0435\u0442\u0430 (\u0437\u0430 \u0448\u0442\u0443\u043a\u0443, \u0431\u0435\u0437 \u0443\u0447\u0435\u0442\u0430 \u0441\u043a\u0438\u0434\u043e\u043a)")
    private Double price;
    /**
     * количество оказанных услуг (целое)
     * (Required)
     * 
     */
    @JsonProperty("amount")
    @JsonPropertyDescription("\u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u043e\u043a\u0430\u0437\u0430\u043d\u043d\u044b\u0445 \u0443\u0441\u043b\u0443\u0433 (\u0446\u0435\u043b\u043e\u0435)")
    private Integer amount;
    /**
     * общая сумма скидок на позицию счета
     * 
     */
    @JsonProperty("sum_discount")
    @JsonPropertyDescription("\u043e\u0431\u0449\u0430\u044f \u0441\u0443\u043c\u043c\u0430 \u0441\u043a\u0438\u0434\u043e\u043a \u043d\u0430 \u043f\u043e\u0437\u0438\u0446\u0438\u044e \u0441\u0447\u0435\u0442\u0430")
    private Double sumDiscount;
    /**
     * общая стоимость позиции счета
     * 
     */
    @JsonProperty("sum_total")
    @JsonPropertyDescription("\u043e\u0431\u0449\u0430\u044f \u0441\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c \u043f\u043e\u0437\u0438\u0446\u0438\u0438 \u0441\u0447\u0435\u0442\u0430")
    private Double sumTotal;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -7357851256213947479L;

    /**
     * Идентификатор позиции счета
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентификатор позиции счета
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * идентификатор родительской позиции счета
     * 
     */
    @JsonProperty("parent_id")
    public Integer getParentId() {
        return parentId;
    }

    /**
     * идентификатор родительской позиции счета
     * 
     */
    @JsonProperty("parent_id")
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * Service
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("service")
    public Service getService() {
        return service;
    }

    /**
     * Service
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("service")
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * цена оказанной позиции счета (за штуку, без учета скидок)
     * (Required)
     * 
     */
    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    /**
     * цена оказанной позиции счета (за штуку, без учета скидок)
     * (Required)
     * 
     */
    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * количество оказанных услуг (целое)
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    /**
     * количество оказанных услуг (целое)
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * общая сумма скидок на позицию счета
     * 
     */
    @JsonProperty("sum_discount")
    public Double getSumDiscount() {
        return sumDiscount;
    }

    /**
     * общая сумма скидок на позицию счета
     * 
     */
    @JsonProperty("sum_discount")
    public void setSumDiscount(Double sumDiscount) {
        this.sumDiscount = sumDiscount;
    }

    /**
     * общая стоимость позиции счета
     * 
     */
    @JsonProperty("sum_total")
    public Double getSumTotal() {
        return sumTotal;
    }

    /**
     * общая стоимость позиции счета
     * 
     */
    @JsonProperty("sum_total")
    public void setSumTotal(Double sumTotal) {
        this.sumTotal = sumTotal;
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
        return new HashCodeBuilder().append(id).append(parentId).append(service).append(price).append(amount).append(sumDiscount).append(sumTotal).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvoiceItem) == false) {
            return false;
        }
        InvoiceItem rhs = ((InvoiceItem) other);
        return new EqualsBuilder().append(id, rhs.id).append(parentId, rhs.parentId).append(service, rhs.service).append(price, rhs.price).append(amount, rhs.amount).append(sumDiscount, rhs.sumDiscount).append(sumTotal, rhs.sumTotal).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
