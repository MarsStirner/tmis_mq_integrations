
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
 * Счет на оплату
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "n_number",
    "price",
    "amount",
    "full_name"
})
public class InvoiceItem implements Serializable
{

    /**
     * Идентифкатор позиции счета
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u043a\u0430\u0442\u043e\u0440 \u043f\u043e\u0437\u0438\u0446\u0438\u0438 \u0441\u0447\u0435\u0442\u0430")
    private Integer id;
    /**
     * номенклатурный номер услуги
     * (Required)
     * 
     */
    @JsonProperty("n_number")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u043d\u043a\u043b\u0430\u0442\u0443\u0440\u043d\u044b\u0439 \u043d\u043e\u043c\u0435\u0440 \u0443\u0441\u043b\u0443\u0433\u0438")
    private String nNumber;
    /**
     * цена оказанной услуги (за штуку, без учета скидок)
     * (Required)
     * 
     */
    @JsonProperty("price")
    @JsonPropertyDescription("\u0446\u0435\u043d\u0430 \u043e\u043a\u0430\u0437\u0430\u043d\u043d\u043e\u0439 \u0443\u0441\u043b\u0443\u0433\u0438 (\u0437\u0430 \u0448\u0442\u0443\u043a\u0443, \u0431\u0435\u0437 \u0443\u0447\u0435\u0442\u0430 \u0441\u043a\u0438\u0434\u043e\u043a)")
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
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("full_name")
    @JsonPropertyDescription("\u043d\u0430\u0438\u043c\u0435\u043d\u043e\u0432\u0430\u043d\u0438\u0435 \u0443\u0441\u043b\u0443\u0433\u0438")
    private String fullName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6852728634288344815L;

    /**
     * Идентифкатор позиции счета
     * (Required)
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор позиции счета
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
    @JsonProperty("n_number")
    public String getNNumber() {
        return nNumber;
    }

    /**
     * номенклатурный номер услуги
     * (Required)
     * 
     */
    @JsonProperty("n_number")
    public void setNNumber(String nNumber) {
        this.nNumber = nNumber;
    }

    /**
     * цена оказанной услуги (за штуку, без учета скидок)
     * (Required)
     * 
     */
    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    /**
     * цена оказанной услуги (за штуку, без учета скидок)
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
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    /**
     * наименование услуги
     * (Required)
     * 
     */
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return new HashCodeBuilder().append(id).append(nNumber).append(price).append(amount).append(fullName).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(nNumber, rhs.nNumber).append(price, rhs.price).append(amount, rhs.amount).append(fullName, rhs.fullName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
