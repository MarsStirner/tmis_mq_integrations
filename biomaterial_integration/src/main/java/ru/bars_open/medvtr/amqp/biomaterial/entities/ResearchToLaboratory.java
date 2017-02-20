package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ResearchToLaboratoryPK;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 16:24 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="mmResearchToLaboratory")
public class ResearchToLaboratory {

    @EmbeddedId
    private ResearchToLaboratoryPK pk;


    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private LaboratoryStatus status;


    public ResearchToLaboratoryPK getPk() {
        return pk;
    }

    public void setPk(final ResearchToLaboratoryPK pk) {
        this.pk = pk;
    }

    public LaboratoryStatus getStatus() {
        return status;
    }

    public void setStatus(final LaboratoryStatus status) {
        this.status = status;
    }
}
