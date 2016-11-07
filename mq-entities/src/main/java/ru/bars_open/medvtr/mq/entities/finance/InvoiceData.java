package ru.bars_open.medvtr.mq.entities.finance;

import com.fasterxml.jackson.annotation.*;
/**
 * Author: Upatov Egor <br>
 * Date: 28.10.2016, 12:26 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceData {

    @JsonProperty("number")
    @JsonPropertyDescription("номер счета")
    private String number;

    @JsonProperty("deleted")
    @JsonPropertyDescription("признак удаления счета")
    private Boolean deleted;

    @JsonProperty("sum")
    @JsonPropertyDescription("сумма счета")
    private Double sum;

    public InvoiceData() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(final Boolean deleted) {
        this.deleted = deleted;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(final Double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceData{");
        sb.append("number='").append(number).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append(", sum=").append(sum);
        sb.append('}');
        return sb.toString();
    }
}
