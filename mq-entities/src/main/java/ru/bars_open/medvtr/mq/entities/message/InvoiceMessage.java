
package ru.bars_open.medvtr.mq.entities.message;

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
import ru.bars_open.medvtr.mq.entities.base.Event;
import ru.bars_open.medvtr.mq.entities.base.Invoice;


/**
 * InvoiceMessage
 * <p>
 * Сообщения содержащее даные о счете
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "invoice"
})
public class InvoiceMessage implements Serializable
{

    /**
     * Обращение на лечение
     * (Required)
     * 
     */
    @JsonProperty("event")
    @JsonPropertyDescription("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043d\u0430 \u043b\u0435\u0447\u0435\u043d\u0438\u0435")
    private Event event;
    /**
     * Invoice
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("invoice")
    @JsonPropertyDescription("\u0421\u0447\u0435\u0442 \u043d\u0430 \u043e\u043f\u043b\u0430\u0442\u0443")
    private Invoice invoice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2791392774789909973L;

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
     * Invoice
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("invoice")
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Invoice
     * <p>
     * Счет на оплату
     * (Required)
     * 
     */
    @JsonProperty("invoice")
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
        return new HashCodeBuilder().append(event).append(invoice).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvoiceMessage) == false) {
            return false;
        }
        InvoiceMessage rhs = ((InvoiceMessage) other);
        return new EqualsBuilder().append(event, rhs.event).append(invoice, rhs.invoice).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
