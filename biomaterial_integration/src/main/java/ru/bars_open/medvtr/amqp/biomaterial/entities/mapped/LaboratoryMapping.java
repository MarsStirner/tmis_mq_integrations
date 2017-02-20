package ru.bars_open.medvtr.amqp.biomaterial.entities.mapped;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 14:12 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@MappedSuperclass
public abstract class LaboratoryMapping {

    @EmbeddedId
    private LaboratoryMappingPK pk;

    @Column(name = "replaceCode")
    private String replaceCode;

    @Column(name = "replaceName")
    private String replaceName;


    public LaboratoryMappingPK getPk() {
        return pk;
    }

    public void setPk(final LaboratoryMappingPK pk) {
        this.pk = pk;
    }

    public String getReplaceCode() {
        return replaceCode;
    }

    public void setReplaceCode(final String replaceCode) {
        this.replaceCode = replaceCode;
    }

    public String getReplaceName() {
        return replaceName;
    }

    public void setReplaceName(final String replaceName) {
        this.replaceName = replaceName;
    }


}
