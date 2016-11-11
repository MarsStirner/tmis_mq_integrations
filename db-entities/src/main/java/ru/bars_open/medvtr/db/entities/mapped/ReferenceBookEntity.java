package ru.bars_open.medvtr.db.entities.mapped;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 */
@MappedSuperclass
public abstract class ReferenceBookEntity extends IdentifiedEntity{

    /**
     * Код записи справочника
     */
    @Column(name ="code")
    protected String code;

    /**
     * Наименование записи справочника
     */
    @Column(name ="name")
    protected String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
