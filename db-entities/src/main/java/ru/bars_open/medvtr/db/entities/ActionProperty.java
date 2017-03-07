package ru.bars_open.medvtr.db.entities;


import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 15:26 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "ActionProperty")
@NamedQueries({@NamedQuery(name = "ActionProperty.getAssignedByAction", query = "SELECT a FROM ActionProperty a WHERE a.deleted = 0 AND a.isAssigned = 1 AND a.action.id = :actionId")})
public class ActionProperty extends DeletableEntity {

    @Column(name = "modifyDatetime")
    private LocalDateTime modifyDatetime;

    /**
     * Действие к которому относится это свойство {Action}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private ActionPropertyType type;

    @Column(name = "isAssigned", nullable = false)
    private boolean isAssigned = false;

    @Version
    @Column(name = "version")
    private int version;

    public ActionProperty() {
    }

    public LocalDateTime getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(final LocalDateTime modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
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

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(final boolean assigned) {
        isAssigned = assigned;
    }

    public String toLogString() {
        final StringBuilder sb = new StringBuilder("AP[").append(id).append(']');
        sb.append('{').append(type.toLogString()).append('}');
        return sb.toString();
    }
}
