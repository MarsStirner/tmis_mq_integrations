package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.2017, 14:47 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Исследование
 */
@Entity
@Table(name = "Research")
public class Research extends IdentifiedEntityWithExternal {

    /**
     * Биоматериал, используемый в ходе исследования {Biomaterial}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biomaterial_id", nullable = false)
    private Biomaterial biomaterial;

    /**
     * Сообщение в котором запрошено это исследование {Message}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    /**
     * флаг отмены этого исследования
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;

    /**
     * Тип исследования {rbResearchType}
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "researchType_id", nullable = true)
     * private RbResearchType researchType;
     */
    @Column(name = "researchType", nullable = true)
    private String researchType;

    /**
     * Признак срочности
     */
    @Column(name = "urgent", nullable = false)
    private boolean urgent = false;

    /**
     * Ответственный  за исследование врач {Person}
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "assigner_id", nullable = false)
     * private Person assigner;
     */
    @Column(name = "assigner", nullable = true)
    private String assigner;


    /**
     * Дата начала исследования
     */
    @Column(name = "begDate")
    private LocalDateTime begDate;

    /**
     * Дата окончания исследования
     */
    @Column(name = "endDate")
    private LocalDateTime endDate;

    /**
     * Примечание
     */
    @Column(name = "note")
    private String note;

    public Research() {
    }

    public Biomaterial getBiomaterial() {
        return biomaterial;
    }

    public void setBiomaterial(final Biomaterial biomaterial) {
        this.biomaterial = biomaterial;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(final Message message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getResearchType() {
        return researchType;
    }

    public void setResearchType(final String researchType) {
        this.researchType = researchType;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(final boolean urgent) {
        this.urgent = urgent;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(final String assigner) {
        this.assigner = assigner;
    }

    public LocalDateTime getBegDate() {
        return begDate;
    }

    public void setBegDate(final LocalDateTime begDate) {
        this.begDate = begDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Research[").append(id);
        sb.append("]{ #").append(externalId).append(", biomaterial[").append(biomaterial != null ? biomaterial.getId(): "null");
        sb.append("], message[").append(message != null ? message.getId(): "null");
        sb.append("], cancelled=").append(cancelled);
        sb.append(", researchType=").append(researchType);
        sb.append(", urgent=").append(urgent);
        sb.append(", assigner[").append(assigner);
        sb.append("], begDate=").append(begDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", note='").append(note).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
