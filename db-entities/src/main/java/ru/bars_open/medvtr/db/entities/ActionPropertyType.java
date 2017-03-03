package ru.bars_open.medvtr.db.entities;


import ru.bars_open.medvtr.db.entities.mapped.IdentifiedEntity;
import ru.bars_open.medvtr.db.entities.util.TypeName;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 15:34 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "ActionPropertyType")
public class ActionPropertyType extends IdentifiedEntity {

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descr", nullable = false)
    private String descr;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeName", nullable = false)
    private TypeName typeName;

    public ActionPropertyType() {
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(final String descr) {
        this.descr = descr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(final TypeName typeName) {
        this.typeName = typeName;
    }

    public String toLogString() {
        return "APT[" + id + "]{code='" + code + "', name='" + name + "'}";
    }
}
