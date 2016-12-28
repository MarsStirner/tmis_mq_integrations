
package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for StationaryMoving complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationaryMoving">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://schemas.xmlsoap.org/soap/envelope}Status"/>
 *         &lt;element name="begDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="orgStructReceived" type="{http://schemas.xmlsoap.org/soap/envelope}OrgStructure"/>
 *         &lt;element name="orgStructStay" type="{http://schemas.xmlsoap.org/soap/envelope}OrgStructure"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationaryMoving", propOrder = {
    "id",
    "status",
    "begDate",
    "endDate",
    "orgStructReceived",
    "orgStructStay"
})
public class StationaryMoving {

    protected int id;
    @XmlElement(required = true)
    protected Status status;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar begDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true)
    protected OrgStructure orgStructReceived;
    @XmlElement(required = true)
    protected OrgStructure orgStructStay;

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
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the orgStructReceived property.
     * 
     * @return
     *     possible object is
     *     {@link OrgStructure }
     *     
     */
    public OrgStructure getOrgStructReceived() {
        return orgStructReceived;
    }

    /**
     * Sets the value of the orgStructReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgStructure }
     *     
     */
    public void setOrgStructReceived(OrgStructure value) {
        this.orgStructReceived = value;
    }

    /**
     * Gets the value of the orgStructStay property.
     * 
     * @return
     *     possible object is
     *     {@link OrgStructure }
     *     
     */
    public OrgStructure getOrgStructStay() {
        return orgStructStay;
    }

    /**
     * Sets the value of the orgStructStay property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgStructure }
     *     
     */
    public void setOrgStructStay(OrgStructure value) {
        this.orgStructStay = value;
    }

}
