package ru.bars_open.medvtr.amqp.biomaterial.dao.impl;

import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.impl.mapped.AbstractDaoWithExternalImpl;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.BiomaterialDao;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Biomaterial;
import ru.bars_open.medvtr.mq.entities.base.Person;

import javax.transaction.Transactional;

/**
 * Author: Upatov Egor <br>
 * Date: 06.12.2016, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@SuppressWarnings("unchecked")
@Repository("biomaterialDao")
@Transactional
public class BiomaterialDaoImpl extends AbstractDaoWithExternalImpl<Biomaterial> implements BiomaterialDao {

    @Override
    public Class<Biomaterial> getEntityClass() {
        return Biomaterial.class;
    }

    @Override
    public Biomaterial findOrCreate(final ru.bars_open.medvtr.mq.entities.base.Biomaterial source) {
        final Biomaterial result = getByExternalId(String.valueOf(source.getId()));
        return result != null ? result : create(source);
    }


    @Override
    public Biomaterial create(final ru.bars_open.medvtr.mq.entities.base.Biomaterial source) {
        log.debug("Biomaterial-create new entity");
        final Biomaterial result = new Biomaterial();
        result.setExternalId(String.valueOf(source.getId()));
        result.setBarcodePrefix(String.valueOf(source.getBarcode().getPeriod()));
        result.setBarcodeNumber(String.valueOf(source.getBarcode().getCode()));
        result.setPlannedDateTime(source.getDatetimePlanned());
        result.setFacticalDateTime(source.getDatetimeTaken());
        //TODO Event
        if (source.getAmount().getUnit() != null) {
            result.setAmount(source.getAmount().getValue() + " " + source.getAmount().getUnit().getName());
        } else {
            result.setAmount(source.getAmount().getValue() + " " + "<Единица измерения не указана>");
            log.warn("Biomaterial.amount.unit is NULL");
        }
        result.setTestTubeType(source.getTestTubeType().getCode());
        result.setBiomaterialType(source.getBiomaterialType().getCode());
        final Person person = source.getPerson();
        result.setPerson("[" + person.getId() + "] " + person.getLastName() + " " + person.getFirstName() + " " + person.getPatrName());
        result.setNote(source.getNote());
        save(result);
        return result;
    }
}
