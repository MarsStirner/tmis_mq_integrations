
package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="moves" type="{http://schemas.xmlsoap.org/soap/envelope}StationaryMoving" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlRootElement(name = "closeHospitalizationRequest")
public class CloseHospitalizationRequest {

    @XmlElement(required = true)
    protected Event event;
    @XmlElement(required = true)
    protected StationaryLeaved leaved;
    protected List<StationaryMoving> moves;

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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the moves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StationaryMoving }
     * 
     * 
     */
    public List<StationaryMoving> getMoves() {
        if (moves == null) {
            moves = new ArrayList<StationaryMoving>();
        }
        return this.moves;
    }

}
