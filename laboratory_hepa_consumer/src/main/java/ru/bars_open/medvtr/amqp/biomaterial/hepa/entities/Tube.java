package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.2017, 13:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "tube")
public class Tube extends IdentifiedEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", nullable = true)
    private Client client;

    @Column(name = "adate")
    @Temporal(TemporalType.DATE)
    private Date adate;

    public Tube() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(final Date adate) {
        this.adate = adate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tube[").append(id);
        sb.append("]{ client=").append(client != null ? client.getId() : null);
        sb.append(", adate=").append(adate);
        sb.append('}');
        return sb.toString();
    }
}
