package ru.bars_open.medvtr.db.entities.util;

import ru.bars_open.medvtr.db.entities.actionProperty.*;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 15:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public enum TypeName {
ANALYSIS_STATUS("AnalysisStatus", APValueString.class),
CONSTRUCTOR("Constructor", APValueString.class),
DATE("Date", APValueDate.class),
DIAGNOSIS("Diagnosis", APValueString.class), //TODO  ActionProperty_Diagnosis
DOUBLE("Double", APValueDouble.class),
FLAT_DIRECTORY("FlatDirectory", APValueInteger.class),
HOSPITAL_BED("HospitalBed", APValueInteger.class),   //TODO  ActionProperty_HospitalBed
HOSPITAL_BED_PROFILE("HospitalBedProfile", APValueInteger.class),      //TODO  ActionProperty_HospitalBedProfile
HTML("Html", APValueString.class),
IMAGE("Image", APValueString.class),     //TODO ActionProperty_Image
INTEGER("Integer", APValueInteger.class),
JOB_TICKET("JobTicket", APValueInteger.class),  //TODO ActionProperty_Job_Ticket
MKB("MKB", APValueString.class),  //TODO ActionProperty_MKB
OPERATION_TYPE("OperationType", APValueInteger.class),
ORGANISATION("Organisation", APValueInteger.class), //TODO ActionProperty_Organisation
ORG_STRUCTURE("OrgStructure", APValueInteger.class), //TODO ActionProperty_OrgStructure
PERSON("Person", APValueInteger.class),  //TODO ActionProperty_Person
REFERENCE("Reference", APValueInteger.class),
REFERENCERB("ReferenceRb", APValueInteger.class),
RLS("RLS", APValueInteger.class),
STRING("String", APValueString.class),
TABLE("Table", APValueString.class),
TEXT("Text", APValueString.class),
TIME("Time", APValueTime.class),
URL("URL", APValueString.class),
CLAIM("Жалобы", APValueString.class),
OTHER_LPU_RECORD("Запись в др. ЛПУ", APValueString.class);

    private final String value;
    private final Class<? extends APValue> valueClass;

    TypeName(final String value, final Class<? extends APValue> valueClass) {
        this.value = value;
        this.valueClass = valueClass;
    }


    public String getValue() {
        return value;
    }

    public static TypeName fromString(String value) {
        for (TypeName item : values()) {
            if (item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return STRING;
    }


    public Class<? extends APValue> getValueClass() {
        return valueClass;
    }


}
