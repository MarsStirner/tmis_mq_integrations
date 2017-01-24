
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contragent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contragent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://schemas.xmlsoap.org/soap/envelope}ContragentType"/>
 *         &lt;element name="person" type="{http://schemas.xmlsoap.org/soap/envelope}Person"/>
 *         &lt;element name="organisation" type="{http://schemas.xmlsoap.org/soap/envelope}Organisation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contragent", propOrder = {
    "id",
    "type",
    "person",
    "organisation"
})
public class Contragent {

    protected int id;
    @XmlElement(required = true)
    protected ContragentType type;
    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected Organisation organisation;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ContragentType }
     *     
     */
    public ContragentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContragentType }
     *     
     */
    public void setType(ContragentType value) {
        this.type = value;
    }

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setPerson(Person value) {
        this.person = value;
    }

    /**
     * Gets the value of the organisation property.
     * 
     * @return
     *     possible object is
     *     {@link Organisation }
     *     
     */
    public Organisation getOrganisation() {
        return organisation;
    }

    /**
     * Sets the value of the organisation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organisation }
     *     
     */
    public void setOrganisation(Organisation value) {
        this.organisation = value;
    }

}
