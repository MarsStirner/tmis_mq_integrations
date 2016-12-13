package ru.bars_open.medvtr.amqp.consumer.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bars_open.medvtr.mq.entities.Event;
import ru.bars_open.medvtr.mq.entities.Person;
import ru.bars_open.medvtr.mq.entities.finance.Invoice;
import ru.bars_open.medvtr.mq.entities.finance.InvoiceData;

import java.math.BigInteger;

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
    private FinanceWSFactory wsFactory;

    public int sendInvoice(final long messageTag, final Invoice invoice, boolean deleted) {
        final Event event = invoice.getEvent();
        final InvoiceData invoiceData = invoice.getInvoiceData();
        final Person client = invoice.getClient();
        final Person payer = invoice.getPayer();
        final Invoice parent = invoice.getParent();
        final String parentInvoiceNumber = (parent != null && parent.getInvoiceData() != null) ? parent.getInvoiceData().getNumber() : null;
        final BigInteger result = wsFactory.getWebService().putTreatment(
                event.getId(),
                wsFactory.wrapDate(event.getSetDate()),
                event.getExternalId(),
                invoiceData.getNumber(),
                invoiceData.getSum(),
                String.valueOf(client.getId()),
                wsFactory.createPersonName(client),
                String.valueOf(payer.getId()),
                wsFactory.createPersonName(payer),
                deleted ? 1 : 0
        );
        log.info("#{} WebService answer - {}", messageTag, result);
        return result.intValue();
    }

    public String sendRefund(final long messageTag, final Invoice invoice) {
        final InvoiceData invoiceData = invoice.getInvoiceData();
        final String parentNumber = invoice.getParent() != null && invoice.getParent().getInvoiceData() != null
                        ? invoice.getParent().getInvoiceData().getNumber()
                        : "";
        final String result =  wsFactory.getWebService().putReturn(
                parentNumber,
                invoiceData.getNumber(),
                invoiceData.getSum(),
                invoiceData.getDeleted() ? 1 : 0
        );
        log.info("#{} WebService answer - {}", messageTag, result);
        return result;
    }
}
