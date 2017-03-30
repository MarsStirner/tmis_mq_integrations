//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.30 at 02:17:34 PM MSK 
//


package ru.bars_open.medvtr.soap.ws.finance.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="sum" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="trxDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payType" type="{ru.bars_open.medvtr.soap.ws.finance}PayType"/>
 *         &lt;element name="contragentId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="refund" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "sum",
    "trxDatetime",
    "invoiceId",
    "invoiceNumber",
    "payType",
    "contragentId",
    "refund"
})
@XmlRootElement(name = "applyPaymentRequest")
public class ApplyPaymentRequest {

    protected double sum;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar trxDatetime;
    protected int invoiceId;
    @XmlElement(required = true)
    protected String invoiceNumber;
    @XmlElement(required = true)
    protected PayType payType;
    protected int contragentId;
    protected boolean refund;

    /**
     * Gets the value of the sum property.
     * 
     */
    public double getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     */
    public void setSum(double value) {
        this.sum = value;
    }

    /**
     * Gets the value of the trxDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTrxDatetime() {
        return trxDatetime;
    }

    /**
     * Sets the value of the trxDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTrxDatetime(XMLGregorianCalendar value) {
        this.trxDatetime = value;
    }

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(int value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the invoiceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    /**
     * Gets the value of the payType property.
     * 
     * @return
     *     possible object is
     *     {@link PayType }
     *     
     */
    public PayType getPayType() {
        return payType;
    }

    /**
     * Sets the value of the payType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayType }
     *     
     */
    public void setPayType(PayType value) {
        this.payType = value;
    }

    /**
     * Gets the value of the contragentId property.
     * 
     */
    public int getContragentId() {
        return contragentId;
    }

    /**
     * Sets the value of the contragentId property.
     * 
     */
    public void setContragentId(int value) {
        this.contragentId = value;
    }

    /**
     * Gets the value of the refund property.
     * 
     */
    public boolean isRefund() {
        return refund;
    }

    /**
     * Sets the value of the refund property.
     * 
     */
    public void setRefund(boolean value) {
        this.refund = value;
    }

}
