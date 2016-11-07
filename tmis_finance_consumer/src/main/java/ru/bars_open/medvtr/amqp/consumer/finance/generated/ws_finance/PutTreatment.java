
package ru.bars_open.medvtr.amqp.consumer.finance.generated.ws_finance;

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
 *         &lt;element name="idTreatment" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dateTreatment" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="numTreatment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numInvoice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sumInvoice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codePatient" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://schemas.xmlsoap.org/soap/envelope}PersonName"/>
 *         &lt;element name="codePayer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payerName" type="{http://schemas.xmlsoap.org/soap/envelope}PersonName"/>
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
    "idTreatment",
    "dateTreatment",
    "numTreatment",
    "numInvoice",
    "sumInvoice",
    "codePatient",
    "patientName",
    "codePayer",
    "payerName",
    "remove"
})
@XmlRootElement(name = "putTreatment")
public class PutTreatment {

    protected int idTreatment;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateTreatment;
    @XmlElement(required = true)
    protected String numTreatment;
    @XmlElement(required = true)
    protected String numInvoice;
    protected int sumInvoice;
    @XmlElement(required = true)
    protected String codePatient;
    @XmlElement(required = true)
    protected PersonName patientName;
    @XmlElement(required = true)
    protected String codePayer;
    @XmlElement(required = true)
    protected PersonName payerName;
    protected int remove;

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
     * Gets the value of the dateTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTreatment() {
        return dateTreatment;
    }

    /**
     * Sets the value of the dateTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTreatment(XMLGregorianCalendar value) {
        this.dateTreatment = value;
    }

    /**
     * Gets the value of the numTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTreatment() {
        return numTreatment;
    }

    /**
     * Sets the value of the numTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTreatment(String value) {
        this.numTreatment = value;
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
     * Gets the value of the sumInvoice property.
     * 
     */
    public int getSumInvoice() {
        return sumInvoice;
    }

    /**
     * Sets the value of the sumInvoice property.
     * 
     */
    public void setSumInvoice(int value) {
        this.sumInvoice = value;
    }

    /**
     * Gets the value of the codePatient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePatient() {
        return codePatient;
    }

    /**
     * Sets the value of the codePatient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePatient(String value) {
        this.codePatient = value;
    }

    /**
     * Gets the value of the patientName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonName }
     *     
     */
    public PersonName getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonName }
     *     
     */
    public void setPatientName(PersonName value) {
        this.patientName = value;
    }

    /**
     * Gets the value of the codePayer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePayer() {
        return codePayer;
    }

    /**
     * Sets the value of the codePayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePayer(String value) {
        this.codePayer = value;
    }

    /**
     * Gets the value of the payerName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonName }
     *     
     */
    public PersonName getPayerName() {
        return payerName;
    }

    /**
     * Sets the value of the payerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonName }
     *     
     */
    public void setPayerName(PersonName value) {
        this.payerName = value;
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
