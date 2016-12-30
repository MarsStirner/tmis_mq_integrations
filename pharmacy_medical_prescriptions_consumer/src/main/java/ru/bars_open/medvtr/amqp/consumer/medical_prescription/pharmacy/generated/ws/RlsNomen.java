
package ru.bars_open.medvtr.amqp.consumer.medical_prescription.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RlsNomen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RlsNomen">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="actMatters" type="{http://schemas.xmlsoap.org/soap/envelope}RlsActMatters"/>
 *         &lt;element name="tradeName" type="{http://schemas.xmlsoap.org/soap/envelope}RlsTradeName"/>
 *         &lt;element name="form" type="{http://schemas.xmlsoap.org/soap/envelope}RlsForm"/>
 *         &lt;element name="packaging" type="{http://schemas.xmlsoap.org/soap/envelope}RlsPackaging"/>
 *         &lt;element name="filling" type="{http://schemas.xmlsoap.org/soap/envelope}RlsFilling"/>
 *         &lt;element name="unit" type="{http://schemas.xmlsoap.org/soap/envelope}RbUnit"/>
 *         &lt;element name="dose" type="{http://schemas.xmlsoap.org/soap/envelope}ValueAndUnit"/>
 *         &lt;element name="drugLifeTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RlsNomen", propOrder = {
    "id",
    "actMatters",
    "tradeName",
    "form",
    "packaging",
    "filling",
    "unit",
    "dose",
    "drugLifeTime"
})
public class RlsNomen {

    protected int id;
    @XmlElement(required = true)
    protected RlsActMatters actMatters;
    @XmlElement(required = true)
    protected RlsTradeName tradeName;
    @XmlElement(required = true)
    protected RlsForm form;
    @XmlElement(required = true)
    protected RlsPackaging packaging;
    @XmlElement(required = true)
    protected RlsFilling filling;
    @XmlElement(required = true)
    protected RbUnit unit;
    @XmlElement(required = true)
    protected ValueAndUnit dose;
    protected int drugLifeTime;

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
     * Gets the value of the actMatters property.
     * 
     * @return
     *     possible object is
     *     {@link RlsActMatters }
     *     
     */
    public RlsActMatters getActMatters() {
        return actMatters;
    }

    /**
     * Sets the value of the actMatters property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsActMatters }
     *     
     */
    public void setActMatters(RlsActMatters value) {
        this.actMatters = value;
    }

    /**
     * Gets the value of the tradeName property.
     * 
     * @return
     *     possible object is
     *     {@link RlsTradeName }
     *     
     */
    public RlsTradeName getTradeName() {
        return tradeName;
    }

    /**
     * Sets the value of the tradeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsTradeName }
     *     
     */
    public void setTradeName(RlsTradeName value) {
        this.tradeName = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link RlsForm }
     *     
     */
    public RlsForm getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsForm }
     *     
     */
    public void setForm(RlsForm value) {
        this.form = value;
    }

    /**
     * Gets the value of the packaging property.
     * 
     * @return
     *     possible object is
     *     {@link RlsPackaging }
     *     
     */
    public RlsPackaging getPackaging() {
        return packaging;
    }

    /**
     * Sets the value of the packaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsPackaging }
     *     
     */
    public void setPackaging(RlsPackaging value) {
        this.packaging = value;
    }

    /**
     * Gets the value of the filling property.
     * 
     * @return
     *     possible object is
     *     {@link RlsFilling }
     *     
     */
    public RlsFilling getFilling() {
        return filling;
    }

    /**
     * Sets the value of the filling property.
     * 
     * @param value
     *     allowed object is
     *     {@link RlsFilling }
     *     
     */
    public void setFilling(RlsFilling value) {
        this.filling = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link RbUnit }
     *     
     */
    public RbUnit getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbUnit }
     *     
     */
    public void setUnit(RbUnit value) {
        this.unit = value;
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
     * Gets the value of the drugLifeTime property.
     * 
     */
    public int getDrugLifeTime() {
        return drugLifeTime;
    }

    /**
     * Sets the value of the drugLifeTime property.
     * 
     */
    public void setDrugLifeTime(int value) {
        this.drugLifeTime = value;
    }

}
