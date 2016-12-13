
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
 *         &lt;element name="parentNumInvoice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numInvoice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sumReturn" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="remove" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "parentNumInvoice",
    "numInvoice",
    "sumReturn",
    "remove"
})
@XmlRootElement(name = "putReturn")
public class PutReturn {

    @XmlElement(required = true)
    protected String parentNumInvoice;
    @XmlElement(required = true)
    protected String numInvoice;
    protected double sumReturn;
    protected int remove;

    /**
     * Gets the value of the parentNumInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentNumInvoice() {
        return parentNumInvoice;
    }

    /**
     * Sets the value of the parentNumInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentNumInvoice(String value) {
        this.parentNumInvoice = value;
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
    public double getSumReturn() {
        return sumReturn;
    }

    /**
     * Sets the value of the sumReturn property.
     * 
     */
    public void setSumReturn(double value) {
        this.sumReturn = value;
    }

    /**
     * Gets the value of the remove property.
     * 
     */
    public int getRemove() {
        return remove;
    }

    /**
     * Sets the value of the remove property.
     * 
     */
    public void setRemove(int value) {
        this.remove = value;
    }

}
