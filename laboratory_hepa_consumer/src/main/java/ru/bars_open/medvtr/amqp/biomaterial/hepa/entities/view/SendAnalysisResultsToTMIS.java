package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.view;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;

import javax.persistence.*;
import java.util.Date;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.2017, 16:05 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="view_analysis_results_to_tmis")
@NamedQuery(name= "SendAnalysisResultsToTMIS.getAll", query = "SELECT a FROM SendAnalysisResultsToTMIS a")
public class SendAnalysisResultsToTMIS {
    @Id
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "result", insertable = false, updatable = false)
    private Result result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", insertable = false, updatable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "soi", insertable = false, updatable = false)
    private Soi soi;

    @Column(name="cr_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="createdby")
    private Operator createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="a_required")
    private Analysis analysis;

    @Column(name="co_date")
    @Temporal(TemporalType.DATE)
    private Date completeDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="completedBy")
    private Operator completedBy;

    @Column(name="link")
    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="material")
    private Material material;

    @Column(name="comment")
    private String comment;

    @Column(name="feedback")
    private String feedback;

    @Column(name="resultString")
    private String resultString;

    @Enumerated(EnumType.STRING)
    private AnalysisStatus status;

    public SendAnalysisResultsToTMIS() {
    }

    public Integer getId() {
        return id;
    }

    public Request getRequest() {
        return request;
    }

    public Result getResult() {
        return result;
    }

    public Client getClient() {
        return client;
    }

    public Soi getSoi() {
        return soi;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Operator getCreatedBy() {
        return createdBy;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public Operator getCompletedBy() {
        return completedBy;
    }

    public String getLink() {
        return link;
    }

    public Material getMaterial() {
        return material;
    }

    public String getComment() {
        return comment;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getResultString() {
        return resultString;
    }

    public AnalysisStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendAnalysisResultsToTMIS{");
        sb.append("id=").append(id);
        sb.append(", request=").append(request);
        sb.append(", result=").append(result);
        sb.append(", client=").append(client);
        sb.append(", soi=").append(soi);
        sb.append(", createDate=").append(createDate);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", analysis=").append(analysis);
        sb.append(", completeDate=").append(completeDate);
        sb.append(", completedBy=").append(completedBy);
        sb.append(", link='").append(convertFromDb(link)).append('\'');
        sb.append(", material=").append(material);
        sb.append(", comment='").append(convertFromDb(comment)).append('\'');
        sb.append(", feedback='").append(convertFromDb(feedback)).append('\'');
        sb.append(", resultString='").append(convertFromDb(resultString)).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
