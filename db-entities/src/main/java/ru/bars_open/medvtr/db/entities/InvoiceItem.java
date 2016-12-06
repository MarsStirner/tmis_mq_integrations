package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 15:56 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Позиция в счете
 */
@Entity
@Table(name = "InvoiceItem")
public class InvoiceItem extends DeletableEntity{
    /**
     *  Счет в рамках которого была выставлена эта позиция
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    /**
     * {Invoice} - счёт, в котором производится возврат
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_id")
    private Invoice refund;


    /**
     *  Услуга{Service}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concreteService_id", nullable = false)
    private Service concreteService;

    /**
     * {InvoiceItem} родительская позиция прайса для случаев комплексных услуг'
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private InvoiceItem parent;

    /**
     *  Скидка на услугу или на счет в целом {ServiceDiscount}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private ServiceDiscount discount;

    /**
     *  Цена единицы услуги по тарифу
     */
    @Column(name="price", nullable = false)
    private Double price;

    /**
     *  'Количество для услуги в данной позиции счета'
     */
    @Column(name="amount", nullable = false)
    private Double amount;

    /**
     * Итоговая стоимость позиции
     */
    @Column(name="sum", nullable = false)
    private Double sum;

    public InvoiceItem() {
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getRefund() {
        return refund;
    }

    public void setRefund(final Invoice refund) {
        this.refund = refund;
    }

    public Service getConcreteService() {
        return concreteService;
    }

    public void setConcreteService(final Service concreteService) {
        this.concreteService = concreteService;
    }

    public InvoiceItem getParent() {
        return parent;
    }

    public void setParent(final InvoiceItem parent) {
        this.parent = parent;
    }

    public ServiceDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(final ServiceDiscount discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(final Double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceItem[").append(id);
        sb.append("]{ invoice=").append(invoice != null ? invoice.getId() : null);
        sb.append(", refund=").append(refund);
        sb.append(", concreteService=").append(concreteService!= null ? concreteService.getId() : null);
        sb.append(", parent=").append(parent!= null ? parent.getId() : null);
        sb.append(", discount=").append(discount!= null ? discount.getId() : null);
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", sum=").append(sum);
        sb.append('}');
        return sb.toString();
    }
}
