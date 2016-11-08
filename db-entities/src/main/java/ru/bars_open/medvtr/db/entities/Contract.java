package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.EditableByPersonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 19:16 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:  //TODO
 */
@Entity
@Table(name = "Contract")
public class Contract extends EditableByPersonEntity{

    /**
     * Номер договора
     */
    @Column(name = "number", nullable = false)
    private String number;
}
