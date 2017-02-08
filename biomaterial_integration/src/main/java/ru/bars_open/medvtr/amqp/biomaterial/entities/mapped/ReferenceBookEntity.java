package ru.bars_open.medvtr.amqp.biomaterial.entities.mapped;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }

        final ReferenceBookEntity that = (ReferenceBookEntity) o;
        return code != null ? !code.equals(that.code) : that.code != null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    public String toShortString() {
        return this.getClass().getSimpleName()+"[" + id + "][" + code + "]";
    }
}
