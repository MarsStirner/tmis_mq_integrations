package ru.bars_open.medvtr.amqp.biomaterial.entities.mapped;

import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Embeddable
public class ResearchToLaboratoryPK implements Serializable{

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "research_id")
    private Research research;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "laboratory_id")
    private RbLaboratory laboratory;

    public ResearchToLaboratoryPK() {
    }

    public ResearchToLaboratoryPK(final Research research, final RbLaboratory laboratory) {
        this.research = research;
        this.laboratory = laboratory;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(final Research research) {
        this.research = research;
    }

    public RbLaboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(final RbLaboratory laboratory) {
        this.laboratory = laboratory;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final ResearchToLaboratoryPK that = (ResearchToLaboratoryPK) o;

        if (!research.equals(that.research)) { return false; }
        return laboratory.equals(that.laboratory);
    }

    @Override
    public int hashCode() {
        int result = research.hashCode();
        result = 31 * result + laboratory.hashCode();
        return result;
    }
}
