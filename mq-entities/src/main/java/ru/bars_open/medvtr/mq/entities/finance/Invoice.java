
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
import ru.bars_open.medvtr.mq.entities.Event;
import ru.bars_open.medvtr.mq.entities.Person;


/**
 * invoice
 * <p>
 * Счет на оплату
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event",
    "invoice_data",
    "client",
    "payer",
    "parent"
})
public class Invoice implements Serializable
{

    /**
     * event
     * <p>
     * Обращение на лечение
     * 
     */
    @JsonProperty("event")
    @JsonPropertyDescription("")
    private Event event;
    /**
     * invoice_data
     * <p>
     * Данные счета
     * 
     */
    @JsonProperty("invoice_data")
    @JsonPropertyDescription("")
    private InvoiceData invoiceData;
    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     */
    @JsonProperty("client")
    @JsonPropertyDescription("")
    private Person client;
    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     */
    @JsonProperty("payer")
    @JsonPropertyDescription("")
    private Person payer;
    /**
     * invoice
     * <p>
     * Счет на оплату
     * 
     */
    @JsonProperty("parent")
    @JsonPropertyDescription("")
    private Invoice parent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1629993400540786475L;

    /**
     * event
     * <p>
     * Обращение на лечение
     * 
     * @return
     *     The event
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     * event
     * <p>
     * Обращение на лечение
     * 
     * @param event
     *     The event
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * invoice_data
     * <p>
     * Данные счета
     * 
     * @return
     *     The invoiceData
     */
    @JsonProperty("invoice_data")
    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    /**
     * invoice_data
     * <p>
     * Данные счета
     * 
     * @param invoiceData
     *     The invoice_data
     */
    @JsonProperty("invoice_data")
    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     * @return
     *     The client
     */
    @JsonProperty("client")
    public Person getClient() {
        return client;
    }

    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     * @param client
     *     The client
     */
    @JsonProperty("client")
    public void setClient(Person client) {
        this.client = client;
    }

    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     * @return
     *     The payer
     */
    @JsonProperty("payer")
    public Person getPayer() {
        return payer;
    }

    /**
     * person
     * <p>
     * Описание пользователя МИС
     * 
     * @param payer
     *     The payer
     */
    @JsonProperty("payer")
    public void setPayer(Person payer) {
        this.payer = payer;
    }

    /**
     * invoice
     * <p>
     * Счет на оплату
     * 
     * @return
     *     The parent
     */
    @JsonProperty("parent")
    public Invoice getParent() {
        return parent;
    }

    /**
     * invoice
     * <p>
     * Счет на оплату
     * 
     * @param parent
     *     The parent
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
        return new HashCodeBuilder().append(event).append(invoiceData).append(client).append(payer).append(parent).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(event, rhs.event).append(invoiceData, rhs.invoiceData).append(client, rhs.client).append(payer, rhs.payer).append(parent, rhs.parent).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
