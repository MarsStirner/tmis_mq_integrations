package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.EditableByPersonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 19:04 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:   //TODO
 */
@Entity
@Table(name="Person")
public class Person extends EditableByPersonEntity{

    /**
     * Код
     */
    @Column(name="code", nullable = false)
    private String code;

    public Person() {
    }


}
