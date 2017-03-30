
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
 * Invoice
 * <p>
 * Счет на оплату
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "number",
    "deleted",
    "contract",
    "author",
    "sum",
    "parent"
})
public class Invoice implements Serializable
{

    /**
     * Идентифкатор
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u043a\u0430\u0442\u043e\u0440")
    private Integer id;
    /**
     * номер счета
     * (Required)
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("\u043d\u043e\u043c\u0435\u0440 \u0441\u0447\u0435\u0442\u0430")
    private String number;
    /**
     * признак удаления счета
     * (Required)
     * 
     */
    @JsonProperty("deleted")
    @JsonPropertyDescription("\u043f\u0440\u0438\u0437\u043d\u0430\u043a \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f \u0441\u0447\u0435\u0442\u0430")
    private Boolean deleted;
    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    @JsonPropertyDescription("\u0414\u043e\u0433\u043e\u0432\u043e\u0440 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435 (\u043a\u043e\u043d\u0442\u0440\u0430\u043a\u0442)")
    private Contract contract;
    /**
     * Описание сотрудника МИС ()
     * 
     */
    @JsonProperty("author")
    @JsonPropertyDescription("\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u0441\u043e\u0442\u0440\u0443\u0434\u043d\u0438\u043a\u0430 \u041c\u0418\u0421 ()")
    private Employee author;
    /**
     * сумма счета
     * 
     */
    @JsonProperty("sum")
    @JsonPropertyDescription("\u0441\u0443\u043c\u043c\u0430 \u0441\u0447\u0435\u0442\u0430")
    private Double sum;
    /**
     * Invoice
     * <p>
     * Счет на оплату
     * 
     */
    @JsonProperty("parent")
    @JsonPropertyDescription("\u0421\u0447\u0435\u0442 \u043d\u0430 \u043e\u043f\u043b\u0430\u0442\u0443")
    private Invoice parent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6725687489561864197L;

    /**
     * Идентифкатор
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Идентифкатор
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * номер счета
     * (Required)
     * 
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * номер счета
     * (Required)
     * 
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * признак удаления счета
     * (Required)
     * 
     */
    @JsonProperty("deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * признак удаления счета
     * (Required)
     * 
     */
    @JsonProperty("deleted")
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    public Contract getContract() {
        return contract;
    }

    /**
     * Contract
     * <p>
     * Договор на лечение (контракт)
     * 
     */
    @JsonProperty("contract")
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * Описание сотрудника МИС ()
     * 
     */
    @JsonProperty("author")
    public Employee getAuthor() {
        return author;
    }

    /**
     * Описание сотрудника МИС ()
     * 
     */
    @JsonProperty("author")
    public void setAuthor(Employee author) {
        this.author = author;
    }

    /**
     * сумма счета
     * 
     */
    @JsonProperty("sum")
    public Double getSum() {
        return sum;
    }

    /**
     * сумма счета
     * 
     */
    @JsonProperty("sum")
    public void setSum(Double sum) {
        this.sum = sum;
    }

    /**
     * Invoice
     * <p>
     * Счет на оплату
     * 
     */
    @JsonProperty("parent")
    public Invoice getParent() {
        return parent;
    }

    /**
     * Invoice
     * <p>
     * Счет на оплату
     * 
     */
    @JsonProperty("parent")
    public void setParent(Invoice parent) {
        this.parent = parent;
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
        return new HashCodeBuilder().append(id).append(number).append(deleted).append(contract).append(author).append(sum).append(parent).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Invoice) == false) {
            return false;
        }
        Invoice rhs = ((Invoice) other);
        return new EqualsBuilder().append(id, rhs.id).append(number, rhs.number).append(deleted, rhs.deleted).append(contract, rhs.contract).append(author, rhs.author).append(sum, rhs.sum).append(parent, rhs.parent).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
