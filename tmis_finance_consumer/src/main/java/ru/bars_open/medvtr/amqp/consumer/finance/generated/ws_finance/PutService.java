
package ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance;

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
 *         &lt;element name="idTreatment" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paidName" type="{http://schemas.xmlsoap.org/soap/envelope}PersonName"/>
 *         &lt;element name="listServiceComplete" type="{http://schemas.xmlsoap.org/soap/envelope}ServiceCompleteInfo"/>
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
    "idTreatment",
    "paidName",
    "listServiceComplete"
})
@XmlRootElement(name = "putService")
public class PutService {

    protected int idTreatment;
    @XmlElement(required = true)
    protected PersonName paidName;
    @XmlElement(required = true)
    protected ServiceCompleteInfo listServiceComplete;

    /**
     * Gets the value of the idTreatment property.
     * 
     */
    public int getIdTreatment() {
        return idTreatment;
    }

    /**
     * Sets the value of the idTreatment property.
     * 
     */
    public void setIdTreatment(int value) {
        this.idTreatment = value;
    }

    /**
     * Gets the value of the paidName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonName }
     *     
     */
    public PersonName getPaidName() {
        return paidName;
    }

    /**
     * Sets the value of the paidName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonName }
     *     
     */
    public void setPaidName(PersonName value) {
        this.paidName = value;
    }

    /**
     * Gets the value of the listServiceComplete property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCompleteInfo }
     *     
     */
    public ServiceCompleteInfo getListServiceComplete() {
        return listServiceComplete;
    }

    /**
     * Sets the value of the listServiceComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCompleteInfo }
     *     
     */
    public void setListServiceComplete(ServiceCompleteInfo value) {
        this.listServiceComplete = value;
    }

}
