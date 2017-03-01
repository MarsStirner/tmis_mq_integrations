package ru.bars_open.medvtr.db.entities.actionProperty;

import ru.bars_open.medvtr.db.entities.ActionProperty;
import ru.bars_open.medvtr.db.entities.mapped.compositeKeys.IndexedIdentifiedPK;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 16:34 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@MappedSuperclass
public abstract class APValue<T> {
    @EmbeddedId
    protected IndexedIdentifiedPK pk = new IndexedIdentifiedPK();

    @Column(name = "value")
    protected T value;

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getId(){
        return pk.getId();
    }

    public void setId(Integer id){
        pk.setId(id);
    }

    public void setId(Integer id, Integer index){
        pk.setId(id);
        pk.setIndex(index);
    }

    public Integer getIndex(){
        return pk.getIndex();
    }

    public void setIndex(Integer id){
        pk.setIndex(id);
    }


    public T getValue() {
        return value;
    }

    public String getValueAsString() {
        return value != null ? value.toString() : null;
    }

    public abstract boolean setValue(Object value);


    public void linkToActionProperty(ActionProperty ap, int index){
        setId(ap.getId(), index);
    }
}
