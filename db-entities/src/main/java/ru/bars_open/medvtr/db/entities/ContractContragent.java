package ru.bars_open.medvtr.db.entities;

import org.joda.time.LocalDate;
import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

import javax.persistence.*;

@Entity
@Table(name = "Contract_Contragent")
public class ContractContragent extends DeletableEntity{

    /**
     * Контрагент физ. лицо {Client}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", nullable = true)
    private Client client;

    /**
     * Контрагент юр. лицо {Organisation}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organisation_id", nullable = true)
    private Organisation organisation;


    /**
     * Дата начала действия строки контрагента
     */
    @Column(name = "begDate")
    private LocalDate begDate;


    /**
     * Дата окончания действия строки контрагента
     */
    @Column(name = "endDate")
    private LocalDate endDate;

    /**
     * Расчетный счет плательщика {OrgAccount}. На данный момент не используем. Будем вводить, если потребуется вести все расчёты в МИС.
     */
    //TODO @ManyToOne
    @Column(name="payerAccount_id")
    private Integer payerAccount_id;

    public ContractContragent() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public LocalDate getBegDate() {
        return begDate;
    }

    public void setBegDate(LocalDate begDate) {
        this.begDate = begDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPayerAccount_id() {
        return payerAccount_id;
    }

    public void setPayerAccount_id(Integer payerAccount_id) {
        this.payerAccount_id = payerAccount_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContractContragent[").append(id);
        sb.append("]{ client=").append(client);
        sb.append(", organisation=").append(organisation);
        sb.append(", begDate=").append(begDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", payerAccount_id=").append(payerAccount_id);
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
