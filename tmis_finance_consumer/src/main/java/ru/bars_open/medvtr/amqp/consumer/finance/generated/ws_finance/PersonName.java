
package ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PersonName complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="given" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BirthDay" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonName", propOrder = {
    "family",
    "given",
    "partName",
    "birthDay"
})
public class PersonName {

    @XmlElement(required = true)
    protected String family;
    @XmlElement(required = true)
    protected String given;
    @XmlElement(required = true)
    protected String partName;
    @XmlElement(name = "BirthDay", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDay;

    /**
     * Gets the value of the family property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamily(String value) {
        this.family = value;
    }

    /**
     * Gets the value of the given property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiven() {
        return given;
    }

    /**
     * Sets the value of the given property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiven(String value) {
        this.given = value;
    }

    /**
     * Gets the value of the partName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartName() {
        return partName;
    }

    /**
     * Sets the value of the partName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartName(String value) {
        this.partName = value;
    }

    /**
     * Gets the value of the birthDay property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDay() {
        return birthDay;
    }

    /**
     * Sets the value of the birthDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDay(XMLGregorianCalendar value) {
        this.birthDay = value;
    }

}
