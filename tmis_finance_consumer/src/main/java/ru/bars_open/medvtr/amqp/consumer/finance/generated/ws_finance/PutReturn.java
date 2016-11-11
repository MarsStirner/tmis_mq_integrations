
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
 *         &lt;element name="numInvoice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sumReturn" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "numInvoice",
    "sumReturn"
})
@XmlRootElement(name = "putReturn")
public class PutReturn {

    protected int idTreatment;
    @XmlElement(required = true)
    protected String numInvoice;
    protected int sumReturn;

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
     * Gets the value of the numInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumInvoice() {
        return numInvoice;
    }

    /**
     * Sets the value of the numInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumInvoice(String value) {
        this.numInvoice = value;
    }

    /**
     * Gets the value of the sumReturn property.
     * 
     */
    public int getSumReturn() {
        return sumReturn;
    }

    /**
     * Sets the value of the sumReturn property.
     * 
     */
    public void setSumReturn(int value) {
        this.sumReturn = value;
    }

}
