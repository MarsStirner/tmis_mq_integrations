package ru.bars_open.medvtr.amqp.biomaterial.entities;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 15:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="Biomaterial")
public class Biomaterial extends IdentifiedEntityWithExternal{

    /**
     * префикс ШК
     */
    @Column(name = "barcodePrefix", nullable = false)
    private String barcodePrefix;

    /**
     *номер ШК
     */
    @Column(name = "barcodeNumber", nullable = false)
    private String barcodeNumber;

    /**
     *Планируемая дата биозабора (на когда назначен)
     */
    @Column(name = "plannedDateTime", nullable = false)
    private LocalDateTime plannedDateTime;

    /**
     *Фактическая дата биозабора (когда материал забран у пациента)
     */
    @Column(name = "facticalDateTime", nullable = false)
    private LocalDateTime facticalDateTime;

    /**
     * Количество забранного материала {Measurement}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="amount_id", nullable = false)
    private Measurement amount;

    /**
     * Тип пробирки {rbTestTubeType}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="testTubeType_id", nullable = false)
    private RbTestTubeType testTybeType;


    /**
     * Тип биоматериала {rbBiomaterialType}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="biomaterialType_id", nullable = false)
    private RbBiomaterialType biomaterialType;


    /**
     * Врач, выполнивший биозабор {Person}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", nullable = false)
    private Person person;

    /**
     * Примечание к биозабору
     */
    @Column(name = "note", nullable = false)
    private String note;

    public Biomaterial() {
    }

    public String getBarcodePrefix() {
        return barcodePrefix;
    }

    public void setBarcodePrefix(final String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(final String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public LocalDateTime getPlannedDateTime() {
        return plannedDateTime;
    }

    public void setPlannedDateTime(final LocalDateTime plannedDateTime) {
        this.plannedDateTime = plannedDateTime;
    }

    public LocalDateTime getFacticalDateTime() {
        return facticalDateTime;
    }

    public void setFacticalDateTime(final LocalDateTime facticalDateTime) {
        this.facticalDateTime = facticalDateTime;
    }

    public Measurement getAmount() {
        return amount;
    }

    public void setAmount(final Measurement amount) {
        this.amount = amount;
    }

    public RbTestTubeType getTestTybeType() {
        return testTybeType;
    }

    public void setTestTybeType(final RbTestTubeType testTybeType) {
        this.testTybeType = testTybeType;
    }

    public RbBiomaterialType getBiomaterialType() {
        return biomaterialType;
    }

    public void setBiomaterialType(final RbBiomaterialType biomaterialType) {
        this.biomaterialType = biomaterialType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Biomaterial[").append(id);
        sb.append("]{ barcodePrefix='").append(barcodePrefix).append('\'');
        sb.append(", barcodeNumber='").append(barcodeNumber).append('\'');
        sb.append(", plannedDateTime=").append(plannedDateTime);
        sb.append(", facticalDateTime=").append(facticalDateTime);
        sb.append(", amount=").append(amount);
        sb.append(", testTybeType=").append(testTybeType);
        sb.append(", biomaterialType=").append(biomaterialType);
        sb.append(", person=").append(person);
        sb.append(", note='").append(note).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
