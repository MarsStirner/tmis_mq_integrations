
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="event" type="{http://schemas.xmlsoap.org/soap/envelope}Event"/>
 *         &lt;element name="prescription" type="{http://schemas.xmlsoap.org/soap/envelope}PrescriptionAction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "event",
    "prescription"
})
@XmlRootElement(name = "closeRequest")
public class CloseRequest {

    @XmlElement(required = true)
    protected Event event;
    @XmlElement(required = true)
    protected PrescriptionAction prescription;

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link Event }
     *     
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link Event }
     *     
     */
    public void setEvent(Event value) {
        this.event = value;
    }

    /**
     * Gets the value of the prescription property.
     * 
     * @return
     *     possible object is
     *     {@link PrescriptionAction }
     *     
     */
    public PrescriptionAction getPrescription() {
        return prescription;
    }

    /**
     * Sets the value of the prescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrescriptionAction }
     *     
     */
    public void setPrescription(PrescriptionAction value) {
        this.prescription = value;
    }

}
