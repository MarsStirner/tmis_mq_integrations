package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;
import ru.bars_open.medvtr.db.entities.util.ActionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.2017, 16:41 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="Action")
public class Action extends DeletableEntity {
    @Enumerated(EnumType.ORDINAL)
    @Column(name="status", nullable = false)
    private ActionStatus status;

    @Column(name="note")
    private String note;

    @Column(name="endDate")
    private LocalDateTime endDate;

    public Action() {
    }

    public ActionStatus getStatus() {
        return status;
    }

    public void setStatus(final ActionStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
