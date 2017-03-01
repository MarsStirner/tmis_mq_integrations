package ru.bars_open.medvtr.db.entities;


import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 15:26 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="ActionProperty")
public class ActionProperty extends IdentifiedEntity {

    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private ActionPropertyType type;

    @Version
    @Column(name = "version")
    private int version;

    public ActionProperty() {
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(final Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    public ActionPropertyType getType() {
        return type;
    }

    public void setType(final ActionPropertyType type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }
}
