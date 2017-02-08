package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:04 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Справочник единиц измерения
 */
@Entity
@Table(name="rbUnit")
public class RbUnit extends ReferenceBookEntity {

}
