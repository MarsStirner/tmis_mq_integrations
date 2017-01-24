
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContragentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContragentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JURIDICAL"/>
 *     &lt;enumeration value="PHYSICAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContragentType")
@XmlEnum
public enum ContragentType {

    JURIDICAL,
    PHYSICAL;

    public String value() {
        return name();
    }

    public static ContragentType fromValue(String v) {
        return valueOf(v);
    }

}
