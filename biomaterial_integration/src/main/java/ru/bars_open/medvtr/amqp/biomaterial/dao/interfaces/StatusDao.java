package ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.entities.LaboratoryStatus;
import ru.bars_open.medvtr.amqp.biomaterial.entities.RbLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.ResearchToLaboratory;

/**
 * Author: Upatov Egor <br>
 * Date: 06.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface StatusDao {
    ResearchToLaboratory setLaboratoryStatus(Research research, RbLaboratory laboratory, LaboratoryStatus status);
}
