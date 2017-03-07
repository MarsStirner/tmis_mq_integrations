package ru.bars_open.medvtr.db.entities;


import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Транзакции с оплатами счетов
 */
@Entity
@Table(name = "FinanceTransaction")
public class FinanceTransaction extends IdentifiedEntity{

    /**
     * {Person} создатель транзакции
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createPerson_id", nullable = true)
    private Person createPerson;

    /**
     * Датавремя проведения транзакции
     */
    @Column(name="trxDatetime")
    private LocalDateTime trxDateTime;

    /**
     * Тип транзакции {RbFinanceTransactionType}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trxType_id", nullable = false)
    private RbFinanceTransactionType transactionType;

    /**
     * Тип финансовой операции {rbFinanceOperationType}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financeOperationType_id", nullable = false)
    private RbFinanceOperationType financeOperationType;

    /**
     * Плательщик-контрагент {@link ContractContragent}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contragent_id", nullable = false)
    private ContractContragent contragent;

    /**
     * Выставленный счет {@link Invoice}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = true)
    private Invoice invoice;

    /**
     * Тип оплаты {@link Invoice}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payType_id", nullable = true)
    private RbPayType payType;

    /**
     * Сумма оплаты
     */
    @Column(name="sum", nullable =false)
    private Double sum;

    public FinanceTransaction() {
    }

    public Person getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Person createPerson) {
        this.createPerson = createPerson;
    }

    public LocalDateTime getTrxDateTime() {
        return trxDateTime;
    }

    public void setTrxDateTime(LocalDateTime trxDateTime) {
        this.trxDateTime = trxDateTime;
    }

    public RbFinanceTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(RbFinanceTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public RbFinanceOperationType getFinanceOperationType() {
        return financeOperationType;
    }

    public void setFinanceOperationType(RbFinanceOperationType financeOperationType) {
        this.financeOperationType = financeOperationType;
    }

    public ContractContragent getContragent() {
        return contragent;
    }

    public void setContragent(ContractContragent contragent) {
        this.contragent = contragent;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public RbPayType getPayType() {
        return payType;
    }

    public void setPayType(RbPayType payType) {
        this.payType = payType;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FinanceTransaction[").append(id);
        sb.append("]{ createPerson=").append(createPerson);
        sb.append(", trxDateTime=").append(trxDateTime);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", financeOperationType=").append(financeOperationType);
        sb.append(", contragent=").append(contragent);
        sb.append(", invoice=").append(invoice);
        sb.append(", payType=").append(payType);
        sb.append(", sum=").append(sum);
        sb.append('}');
        return sb.toString();
    }
}
