
package ru.bars_open.medvtr.mq.entities.finance;

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
 * invoice_data
 * <p>
 * Данные счета
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "number",
    "deleted",
    "sum"
})
public class InvoiceData implements Serializable
{

    /**
     * номер счета
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("")
    private String number;
    /**
     * признак удаления счета
     * 
     */
    @JsonProperty("deleted")
    @JsonPropertyDescription("")
    private Boolean deleted;
    /**
     * сумма счета
     * 
     */
    @JsonProperty("sum")
    @JsonPropertyDescription("")
    private Double sum;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5259556197277165239L;

    /**
     * номер счета
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * номер счета
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * признак удаления счета
     * 
     * @return
     *     The deleted
     */
    @JsonProperty("deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * признак удаления счета
     * 
     * @param deleted
     *     The deleted
     */
    @JsonProperty("deleted")
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * сумма счета
     * 
     * @return
     *     The sum
     */
    @JsonProperty("sum")
    public Double getSum() {
        return sum;
    }

    /**
     * сумма счета
     * 
     * @param sum
     *     The sum
     */
    @JsonProperty("sum")
    public void setSum(Double sum) {
        this.sum = sum;
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
        return new HashCodeBuilder().append(number).append(deleted).append(sum).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvoiceData) == false) {
            return false;
        }
        InvoiceData rhs = ((InvoiceData) other);
        return new EqualsBuilder().append(number, rhs.number).append(deleted, rhs.deleted).append(sum, rhs.sum).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
