package ru.bars_open.medvtr.amqp.biomaterial.entities.laboratoryMappings;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.LaboratoryMapping;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 14:10 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:  Список переопределений кодов и наименований типов БМ {rbBiomaterialType} при их отправке в ЛИС. Если нет привязки к ЛИС, отправляется без изменений
 */
@Entity
@Table(name ="mapBiomaterialTypeToLaboratory")
public class MapBiomaterialTypeToLaboratory extends LaboratoryMapping {}
