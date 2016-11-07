package ru.bars_open.medvtr.mq.entities.finance;

import com.fasterxml.jackson.annotation.*;
import ru.bars_open.medvtr.mq.entities.Event;
import ru.bars_open.medvtr.mq.entities.Person;

import java.util.HashMap;
import java.util.Map;

/**
 * invoice
 * <p>
 * Счет на оплату
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    @JsonProperty(value = "event", required = true)
    @JsonPropertyDescription("Обращение")
    private Event event;

    @JsonProperty(value = "invoice_data", required = true)
    @JsonPropertyDescription("инофрмация о счете")
    private InvoiceData invoiceData;

    @JsonProperty(value = "client")
    @JsonPropertyDescription("Пациент")
    private Person client;

    @JsonProperty(value = "payer", required = true)
    @JsonPropertyDescription("плательщик")
    private Person payer;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Invoice() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(final InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    public Person getClient() {
        return client;
    }

    public void setClient(final Person client) {
        this.client = client;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(final Person payer) {
        this.payer = payer;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(final Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("event=").append(event);
        sb.append(", invoiceData=").append(invoiceData);
        sb.append(", client=").append(client);
        sb.append(", payer=").append(payer);
        sb.append('}');
        return sb.toString();
    }
}
