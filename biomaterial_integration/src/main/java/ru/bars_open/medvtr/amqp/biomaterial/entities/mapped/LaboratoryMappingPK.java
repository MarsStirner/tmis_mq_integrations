package ru.bars_open.medvtr.amqp.biomaterial.entities.mapped;

import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 14:29 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Embeddable
public class LaboratoryMappingPK implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laboratory_id")
    private RbLaboratory laboratory;

    @Column(name = "code")
    private String code;

    public LaboratoryMappingPK(final RbLaboratory laboratory, final String code) {
        this.laboratory = laboratory;
        this.code = code;
    }

    public LaboratoryMappingPK() {
    }

    public RbLaboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(final RbLaboratory laboratory) {
        this.laboratory = laboratory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final LaboratoryMappingPK that = (LaboratoryMappingPK) o;

        if (laboratory != null ? !laboratory.equals(that.laboratory) : that.laboratory != null) { return false; }
        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        int result = laboratory != null ? laboratory.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
