package ru.bars_open.medvtr.mq.util;

import org.apache.commons.lang3.StringUtils;
import ru.bars_open.medvtr.mq.entities.action.PrescriptionAction;
import ru.bars_open.medvtr.mq.entities.action.StationaryLeaved;
import ru.bars_open.medvtr.mq.entities.action.StationaryMoving;
import ru.bars_open.medvtr.mq.entities.action.StationaryReceived;
import ru.bars_open.medvtr.mq.entities.base.*;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbTreatment;
import ru.bars_open.medvtr.mq.entities.base.refbook.RbUnit;
import ru.bars_open.medvtr.mq.entities.base.refbook.RlsNomen;
import ru.bars_open.medvtr.mq.entities.base.refbook.RlsTradeName;
import ru.bars_open.medvtr.mq.entities.base.util.ValueAndUnit;
import ru.bars_open.medvtr.mq.entities.message.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.2017, 18:12 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class ValidationFactory {

    private static boolean checkNotNull(final Object source, final String prefix, final String name, final Set<String> result) {
        if (source == null) {
            result.add(prefix + name + " is null");
            return false;
        }
        return true;
    }

    private static boolean checkNotEmpty(final String source, final String prefix, final String name, final Set<String> result) {
        if (StringUtils.isEmpty(source)) {
            result.add(prefix + name + " is null or empty");
            return false;
        }
        return true;
    }

    private static boolean checkNotEmpty(final Collection source, final String prefix, final String name, final Set<String> result) {
        if (source == null || source.isEmpty()) {
            result.add(prefix + name + " collection is null or empty");
            return false;
        }
        return true;
    }

    public static Set<String> getErrors(final HospitalizationCreateMessage source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        result.addAll(getErrors(source.getEvent(), prefix + ".Event"));
        result.addAll(getErrors(source.getReceived(), prefix + ".Received"));
        return result;
    }

    public static Set<String> getErrors(final HospitalizationMovingMessage source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        result.addAll(getErrors(source.getEvent(), prefix + ".Event"));
        result.addAll(getErrors(source.getMoving(), prefix + ".Moving"));
        return result;
    }

    public static Set<String> getErrors(final HospitalizationFinishMessage source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        result.addAll(getErrors(source.getEvent(), prefix + ".Event"));
        final List<StationaryMoving> movings = source.getMovings();
        if (movings != null && !movings.isEmpty()) {
            for (int i = 0; i < movings.size(); i++) {
                final StationaryMoving moving = movings.get(i);
                result.addAll(getErrors(moving, prefix+ ".Moving[" + i + "]"));
            }
        }
        result.addAll(getErrors(source.getLeaved(), prefix + ".Leaved"));
        return result;
    }

    public static Set<String> getErrors(final PrescriptionListMessage source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        result.addAll(getErrors(source.getEvent(), prefix + ".Event"));
        result.addAll(getErrors(source.getPrescription(), prefix + ".Prescription"));
        return result;
    }

    private static Set<String> getErrors(final PrescriptionAction source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        final List<MedicalPrescription> prescriptions = source.getPrescriptions();
        if (checkNotEmpty(source.getPrescriptions(), prefix, ".MedicalPrescription", result)) {
            for (int i = 0; i < prescriptions.size(); i++) {
                final MedicalPrescription item = prescriptions.get(i);
                result.addAll(getErrors(item, prefix+".MedicalPrescription[" + i + "]"));
            }
        }
        return result;
    }

    private static Set<String> getErrors(final MedicalPrescription source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        result.addAll(getErrors(source.getRls(), prefix + ".Rls"));
        result.addAll(getErrors(source.getDose(), prefix + ".Dose"));
        result.addAll(getErrors(source.getFrequency(), prefix + ".Frequency"));
        result.addAll(getErrors(source.getDuration(), prefix + ".Duration"));
        return result;
    }

    private static Set<String> getErrors(final ValueAndUnit source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getValue(), prefix, ".value", result);
        result.addAll(getErrors(source.getUnit(), prefix + ".Unit"));
        return result;
    }

    private static Set<String> getErrors(final RbUnit source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getCode(), prefix, ".code", result);
        return result;
    }

    private static Set<String> getErrors(final RlsNomen source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        result.addAll(getErrors(source.getTradeName(), prefix + ".TradeName"));
        return result;
    }

    private static Set<String> getErrors(final RlsTradeName source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        return result;
    }

    private static Set<String> getErrors(final StationaryLeaved source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        checkNotNull(source.getEndDate(), prefix, ".endDate", result);
        checkNotEmpty(source.getHospOutcome(), prefix, ".hospOutcome", result);
        return result;
    }

    private static Set<String> getErrors(final StationaryMoving source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        result.addAll(getErrors(source.getOrgStructStay(), prefix + ".OrgStructStay"));
        result.addAll(getErrors(source.getOrgStructReceived(), prefix + ".OrgStructReceived"));
        return result;
    }

    private static Set<String> getErrors(final StationaryReceived source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        result.addAll(getErrors(source.getOrgStructStay(), prefix + ".OrgStructStay"));
        if (source.getOrgStructTransfer() != null) {
            result.addAll(getErrors(source.getOrgStructTransfer(), prefix + ".[optional]OrgStructTransfer"));
        }
        return result;
    }


    private static Set<String> getErrors(final OrgStructure source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getCode(), prefix, ".code", result);
        checkNotEmpty(source.getName(), prefix, ".name", result);
        return result;
    }

    private static Set<String> getErrors(final Event source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getExternalId(), prefix, ".externalId", result);
        checkNotNull(source.getSetDate(), prefix, ".setDate", result);
        result.addAll(getErrors(source.getClient(), prefix + ".Client"));
        result.addAll(getErrors(source.getContract(), prefix + ".Contract"));
        if (source.getVmpTicket() != null) {
            result.addAll(getErrors(source.getVmpTicket(), prefix + ".[optional]VmpTicket"));
        }
        return result;
    }

    private static Set<String> getErrors(final VMPTicket source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getNumber(), prefix, ".number", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        if (source.getTreatment() != null) {
            result.addAll(getErrors(source.getTreatment(), prefix + ".[optional]RbTreatment"));
        }
        return result;
    }

    private static Set<String> getErrors(final RbTreatment source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getCode(), prefix, ".code", result);
        checkNotEmpty(source.getName(), prefix, ".name", result);
        return result;
    }

    private static Set<String> getErrors(final Contract source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getNumber(), prefix, ".number", result);
        checkNotNull(source.getBegDate(), prefix, ".begDate", result);
        result.addAll(getErrors(source.getPayer(), prefix+".Payer"));
        return result;
    }

    private static Set<String> getErrors(final Contragent source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        if (checkNotNull(source.getType(), prefix, ".type", result)) {
            switch (source.getType()) {
                case JURIDICAL:
                    result.addAll(getErrors(source.getOrganisation(), ".Organisation"));
                    break;
                case PHYSICAL:
                    result.addAll(getErrors(source.getPerson(), ".Person"));
                    break;
            }
        }
        return result;
    }

    private static Set<String> getErrors(final Organisation source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        checkNotEmpty(source.getUuid(), prefix, ".uuid", result);
        checkNotEmpty(source.getShortName(), prefix, ".shortName", result);
        return result;
    }

    private static Set<String> getErrors(final Person source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotNull(source.getId(), prefix, ".id", result);
        return result;
    }


    public static Set<String> getErrors(final InvoiceMessage source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        result.addAll(getErrors(source.getEvent(), prefix + ".Event"));
        result.addAll(getErrors(source.getInvoice(), prefix + ".Invoice"));
        return result;
    }

    private static Set<String> getErrors(final Invoice source, final String prefix) {
        final Set<String> result = new HashSet<>();
        if (!checkNotNull(source, prefix, "", result)) {
            return result;
        }
        checkNotEmpty(source.getNumber(), prefix, ".number", result);
        checkNotNull(source.getSum(), prefix, ".sum", result);
        result.addAll(getErrors(source.getContract(), prefix + ".Contract"));
        return result;
    }


}
