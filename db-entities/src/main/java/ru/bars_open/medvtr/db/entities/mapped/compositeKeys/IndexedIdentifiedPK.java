package ru.bars_open.medvtr.db.entities.mapped.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 20:54 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Embeddable
public class IndexedIdentifiedPK implements Serializable{
    @Column(name="id")
    private Integer id;

    @Column(name= "\"index\"")
    private Integer index;

    public IndexedIdentifiedPK() {
    }

    public IndexedIdentifiedPK(final Integer id, final Integer index) {
        this.id = id;
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final IndexedIdentifiedPK that = (IndexedIdentifiedPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) { return false; }
        return index != null ? index.equals(that.index) : that.index == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }
}
