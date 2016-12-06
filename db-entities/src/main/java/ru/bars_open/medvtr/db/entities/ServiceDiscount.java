package ru.bars_open.medvtr.db.entities;

import ru.bars_open.medvtr.db.entities.mapped.DeletableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:12 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Каталог действующих скидок и акций по услугам
 */
//TODO
@Entity
@Table(name = "ServiceDiscount")
public class ServiceDiscount extends DeletableEntity{}
