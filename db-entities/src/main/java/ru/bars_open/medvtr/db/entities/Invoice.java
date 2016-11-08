package ru.bars_open.medvtr.db.entities;

import org.joda.time.LocalDate;
import ru.bars_open.medvtr.db.entities.mapped.EditableByPersonEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 18:03 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Выставленный счет по услугам
 */
@Entity
@Table(name = "Invoice")
public class Invoice extends EditableByPersonEntity{

    /**
     * {Contract}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    /**
     * {Invoice} - родительский счёт (для возвратов)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Invoice parent;

    /**
     * Дата формирования
     */
    @Column(name = "setDate", nullable = false)
    private LocalDate setDate;

    /**
     * Дата погашения
     */
    @Column(name = "settleDate")
    private LocalDate settleDate;


    /**
     * Номер счета
     */
    @Column(name = "number", nullable = false)
    private String number;


    /**
     * Номер акта
     */
    @Column(name = "deedNumber")
    private String deedNumber;


    /**
     * Примечание
     */
    @Column(name = "note")
    private String note;

    /**
     * Черновик ли это
     */
    @Column(name= "draft", nullable = false)
    private boolean draft;

    public Invoice() {
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public Invoice getParent() {
        return parent;
    }

    public void setParent(final Invoice parent) {
        this.parent = parent;
    }

    public LocalDate getSetDate() {
        return setDate;
    }

    public void setSetDate(final LocalDate setDate) {
        this.setDate = setDate;
    }

    public LocalDate getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(final LocalDate settleDate) {
        this.settleDate = settleDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getDeedNumber() {
        return deedNumber;
    }

    public void setDeedNumber(final String deedNumber) {
        this.deedNumber = deedNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(final boolean draft) {
        this.draft = draft;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice[").append(id);
        sb.append("]{ contract=").append(contract);
        sb.append(", parent=").append(parent);
        sb.append(", setDate=").append(setDate);
        sb.append(", settleDate=").append(settleDate);
        sb.append(", number='").append(number).append('\'');
        sb.append(", deedNumber='").append(deedNumber).append('\'');
        sb.append(", note='").append(note).append('\'');
        sb.append(", draft=").append(draft);
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
