package ru.bars_open.medvtr.mq.entities;


import com.fasterxml.jackson.annotation.*;
import org.joda.time.DateTime;


/**
 * Обращение на лечение
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @JsonProperty(value = "id", required = true)
    @JsonPropertyDescription("Идентифкатор")
    private Integer id;

    @JsonProperty("setDate")
    @JsonPropertyDescription("дата создания обращения  в формате 'YYYY-MM-ddT18:25:43.511Z'")
    private DateTime setDate;

    @JsonProperty("externalId")
    @JsonPropertyDescription("номер обращения")
    private String externalId;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public DateTime getSetDate() {
        return setDate;
    }

    public void setSetDate(final DateTime setDate) {
        this.setDate = setDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event[").append(id);
        sb.append("]{ setDate='").append(setDate).append('\'');
        sb.append(", externalId='").append(externalId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}