package ru.bars_open.medvtr.amqp.consumer.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance.Services;
import ru.bars_open.medvtr.mq.entities.base.Event;
import ru.bars_open.medvtr.mq.entities.base.Invoice;
import ru.bars_open.medvtr.mq.entities.base.Person;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ContactPointSystem;
import ru.bars_open.medvtr.mq.entities.base.refbook.enumerator.ContactPointUse;
import ru.bars_open.medvtr.mq.entities.base.util.ContactPoint;
import ru.bars_open.medvtr.mq.entities.message.InvoiceMessage;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Author: Upatov Egor <br>
 * Date: 05.12.2016, 18:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Component
public class FinanceSender {

    private static final Logger log = LoggerFactory.getLogger(ConsumerCreated.class);

    @Autowired
    private WSFactory wsFactory;

    public int sendInvoice(final long messageTag, final InvoiceMessage message, boolean deleted) {
        final Event event = message.getEvent();
        final Invoice invoice = message.getInvoice();
        final Person client = message.getEvent().getClient();
        final Person payer = invoice.getContract().getPayer().getPerson();
        final Optional<ContactPoint> emailStruct = payer.getTelecom().stream().filter(x -> ContactPointSystem.EMAIL == x.getSystem()).findFirst();
        final Optional<ContactPoint> phoneStruct = payer.getTelecom().stream().filter(x -> ContactPointSystem.PHONE == x.getSystem() && ContactPointUse.MOBILE == x.getUse()).findFirst();
        final Services services = wsFactory.getServices(invoice.getItems());
        final BigInteger result = wsFactory.getWebService().putTreatment(
                event.getId(),
                wsFactory.wrapDate(event.getSetDate()),
                event.getExternalId(),
                invoice.getNumber(),
                invoice.getSum(),
                String.valueOf(client.getId()),
                wsFactory.createPersonName(client),
                String.valueOf(payer.getId()),
                wsFactory.createPersonName(payer),
                deleted ? 1 : 0,
                invoice.getId(),
                invoice.getAuthor() != null ? invoice.getAuthor().getId() : 0,
                emailStruct.map(ContactPoint::getValue).orElse(""),
                phoneStruct.map(ContactPoint::getValue).orElse(""),
                services
        );
        log.info("#{} WebService answer - {}", messageTag, result);
        return result.intValue();
    }

    public String sendRefund(final long messageTag, final InvoiceMessage message) {
        final Invoice invoice = message.getInvoice();
        final String parentNumber = invoice.getParent() != null ? invoice.getParent().getNumber() : "";
        final int parentId = invoice.getParent() != null ? invoice.getParent().getId() : -1;
        final Person payer = invoice.getContract().getPayer().getPerson();
        final Optional<ContactPoint> emailStruct = payer.getTelecom().stream().filter(x -> ContactPointSystem.EMAIL == x.getSystem()).findFirst();
        final Optional<ContactPoint> phoneStruct = payer.getTelecom().stream().filter(x -> ContactPointSystem.PHONE == x.getSystem() && ContactPointUse.MOBILE == x.getUse()).findFirst();
        final Services services = wsFactory.getServices(invoice.getItems());
        final String result = wsFactory.getWebService().putReturn(
                parentNumber,
                invoice.getNumber(),
                invoice.getSum(),
                invoice.getDeleted() ? 1 : 0,
                invoice.getId(),
                parentId,
                invoice.getAuthor() != null ? invoice.getAuthor().getId() : 0,
                emailStruct.map(ContactPoint::getValue).orElse(""),
                phoneStruct.map(ContactPoint::getValue).orElse(""),
                services
        );
        log.info("#{} WebService answer - {}", messageTag, result);
        return result;
    }
}
