
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MedicalPrescription complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MedicalPrescription">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rls" type="{http://schemas.xmlsoap.org/soap/envelope}RlsNomen"/>
 *         &lt;element name="status" type="{http://schemas.xmlsoap.org/soap/envelope}Status"/>
 *         &lt;element name="dose" type="{http://schemas.xmlsoap.org/soap/envelope}ValueAndUnit"/>
 *         &lt;element name="frequency" type="{http://schemas.xmlsoap.org/soap/envelope}ValueAndUnit"/>
 *         &lt;element name="duration" type="{http://schemas.xmlsoap.org/soap/envelope}ValueAndUnit"/>
 *         &lt;element name="begDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reasonOfCancel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MedicalPrescription", propOrder = {
    "id",
    "rls",
    "status",
    "dose",
    "frequency",
    "duration",
    "begDate",
    "note",
    "reasonOfCancel"
})
public class MedicalPrescription {

    protected int id;
    @XmlElement(required = true)
    protected RlsNomen rls;
    @XmlElement(required = true)
    protected Status status;
    @XmlElement(required = true)
    protected ValueAndUnit dose;
    @XmlElement(required = true)
    protected ValueAndUnit frequency;
    @XmlElement(required = true)
    protected ValueAndUnit duration;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar begDate;
    @XmlElement(required = true)
    protected String note;
    @XmlElement(required = true)
    protected String reasonOfCancel;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the rls property.
     * 
     * @return
     *     possible object is
     *     {@link RlsNomen }
     *     
     */
    public RlsNomen getRls() {
        return rls;
    }

    /**
     * Sets the value of the rls property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsNomen }
     *     
     */
    public void setRls(RlsNomen value) {
        this.rls = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the dose property.
     * 
     * @return
     *     possible object is
     *     {@link ValueAndUnit }
     *     
     */
    public ValueAndUnit getDose() {
        return dose;
    }

    /**
     * Sets the value of the dose property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueAndUnit }
     *     
     */
    public void setDose(ValueAndUnit value) {
        this.dose = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link ValueAndUnit }
     *     
     */
    public ValueAndUnit getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueAndUnit }
     *     
     */
    public void setFrequency(ValueAndUnit value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link ValueAndUnit }
     *     
     */
    public ValueAndUnit getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueAndUnit }
     *     
     */
    public void setDuration(ValueAndUnit value) {
        this.duration = value;
    }

    /**
     * Gets the value of the begDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBegDate() {
        return begDate;
    }

    /**
     * Sets the value of the begDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBegDate(XMLGregorianCalendar value) {
        this.begDate = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the reasonOfCancel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonOfCancel() {
        return reasonOfCancel;
    }

    /**
     * Sets the value of the reasonOfCancel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonOfCancel(String value) {
        this.reasonOfCancel = value;
    }

}
