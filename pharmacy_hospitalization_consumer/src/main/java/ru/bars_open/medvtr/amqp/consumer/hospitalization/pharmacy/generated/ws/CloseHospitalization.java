
package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws;

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
 *         &lt;element name="leaved" type="{http://schemas.xmlsoap.org/soap/envelope}StationaryLeaved"/>
 *         &lt;element name="moves" type="{http://schemas.xmlsoap.org/soap/envelope}Мoves"/>
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
    "leaved",
    "moves"
})
@XmlRootElement(name = "closeHospitalization")
public class CloseHospitalization {

    @XmlElement(required = true)
    protected Event event;
    @XmlElement(required = true)
    protected StationaryLeaved leaved;
    @XmlElement(required = true)
    protected Мoves moves;

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
     * Gets the value of the leaved property.
     * 
     * @return
     *     possible object is
     *     {@link StationaryLeaved }
     *     
     */
    public StationaryLeaved getLeaved() {
        return leaved;
    }

    /**
     * Sets the value of the leaved property.
     * 
     * @param value
     *     allowed object is
     *     {@link StationaryLeaved }
     *     
     */
    public void setLeaved(StationaryLeaved value) {
        this.leaved = value;
    }

    /**
     * Gets the value of the moves property.
     * 
     * @return
     *     possible object is
     *     {@link Мoves }
     *     
     */
    public Мoves getMoves() {
        return moves;
    }

    /**
     * Sets the value of the moves property.
     * 
     * @param value
     *     allowed object is
     *     {@link Мoves }
     *     
     */
    public void setMoves(Мoves value) {
        this.moves = value;
    }

}
