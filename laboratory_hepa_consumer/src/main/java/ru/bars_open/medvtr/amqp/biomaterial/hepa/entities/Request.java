package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.*;
import java.time.LocalDate;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 19:22 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "request")
public class Request extends IdentifiedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client", nullable = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="soi", nullable = true)
    private Soi soi;

    @Column(name="cr_date")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="createdby")
    private Operator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="a_required")
    private Analysis analysis;

    @Column(name="co_date")
    private LocalDate completeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="completedBy")
    private Operator completedBy;

    @Column(name="phoresis")
    private String phoresis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_result")
    private Result result;      

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="material")
    private Material material;

    @Column(name="comment")
    private String comment;

    @Column(name="sent")
    private Integer sent;

    @Column(name="feedback")
    private String feedback;

    @Column(name="amount")
    private Double amount;

    @Column(name="amount2")
    private Double amount2;

    @Column(name="amount3")
    private Double amount3;

    public Request() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }

    public Soi getSoi() {
        return soi;
    }

    public void setSoi(final Soi soi) {
        this.soi = soi;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final LocalDate createDate) {
        this.createDate = createDate;
    }

    public Operator getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Operator createdBy) {
        this.createdBy = createdBy;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(final Analysis analysis) {
        this.analysis = analysis;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(final Material material) {
        this.material = material;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(final Integer sent) {
        this.sent = sent;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Double getAmount2() {
        return amount2;
    }

    public void setAmount2(final Double amount2) {
        this.amount2 = amount2;
    }

    public Double getAmount3() {
        return amount3;
    }

    public void setAmount3(final Double amount3) {
        this.amount3 = amount3;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public Operator getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(final Operator completedBy) {
        this.completedBy = completedBy;
    }

    public String getPhoresis() {
        return phoresis;
    }

    public void setPhoresis(final String phoresis) {
        this.phoresis = phoresis;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(final Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request[").append(id);
        sb.append("]{ client=").append(client);
        sb.append(", soi=").append(soi);
        sb.append(", createDate=").append(createDate);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", analysis=").append(analysis);
        sb.append(", completeDate=").append(completeDate);
        sb.append(", completeBy=").append(completedBy);
        sb.append(", phoresis='").append(convertFromDb(phoresis)).append('\'');
        sb.append(", result=").append(result);
        sb.append(", material=").append(material);
        sb.append(", comment='").append(convertFromDb(comment)).append('\'');
        sb.append(", sent=").append(sent);
        sb.append(", feedback='").append(convertFromDb(feedback)).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", amount2=").append(amount2);
        sb.append(", amount3=").append(amount3);
        sb.append('}');
        return sb.toString();
    }
}
