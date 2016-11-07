
package ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceCompleteInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCompleteInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="codeOfService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameOfService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codeOfStruct" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nameOfStruct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="isService" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCompleteInfo", propOrder = {
    "idAction",
    "endDate",
    "codeOfService",
    "nameOfService",
    "codeOfStruct",
    "nameOfStruct",
    "amount",
    "isService"
})
public class ServiceCompleteInfo {

    protected int idAction;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true)
    protected String codeOfService;
    @XmlElement(required = true)
    protected String nameOfService;
    protected int codeOfStruct;
    @XmlElement(required = true)
    protected String nameOfStruct;
    protected double amount;
    protected boolean isService;

    /**
     * Gets the value of the idAction property.
     * 
     */
    public int getIdAction() {
        return idAction;
    }

    /**
     * Sets the value of the idAction property.
     * 
     */
    public void setIdAction(int value) {
        this.idAction = value;
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
     * Gets the value of the codeOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeOfService() {
        return codeOfService;
    }

    /**
     * Sets the value of the codeOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeOfService(String value) {
        this.codeOfService = value;
    }

    /**
     * Gets the value of the nameOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfService() {
        return nameOfService;
    }

    /**
     * Sets the value of the nameOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfService(String value) {
        this.nameOfService = value;
    }

    /**
     * Gets the value of the codeOfStruct property.
     * 
     */
    public int getCodeOfStruct() {
        return codeOfStruct;
    }

    /**
     * Sets the value of the codeOfStruct property.
     * 
     */
    public void setCodeOfStruct(int value) {
        this.codeOfStruct = value;
    }

    /**
     * Gets the value of the nameOfStruct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfStruct() {
        return nameOfStruct;
    }

    /**
     * Sets the value of the nameOfStruct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfStruct(String value) {
        this.nameOfStruct = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the isService property.
     * 
     */
    public boolean isIsService() {
        return isService;
    }

    /**
     * Sets the value of the isService property.
     * 
     */
    public void setIsService(boolean value) {
        this.isService = value;
    }

}
