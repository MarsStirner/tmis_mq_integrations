package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:10 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Экземпляр конкретной услуги
 */
//TODO
@Entity
@Table(name = "Service")
public class Service extends DeletableEntity {}
