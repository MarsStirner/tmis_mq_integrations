package ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces;

import ru.bars_open.medvtr.amqp.biomaterial.hepa.dao.interfaces.mapped.AbstractDao;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.*;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 19:01 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public interface RequestDao extends AbstractDao<Request> {

    Request createRequest(Client client, Soi soi, Operator operator, Analysis analysisType, Material material, String feedback);

    List<Request> getNotSent();

    Request setSent(Request request);
}
