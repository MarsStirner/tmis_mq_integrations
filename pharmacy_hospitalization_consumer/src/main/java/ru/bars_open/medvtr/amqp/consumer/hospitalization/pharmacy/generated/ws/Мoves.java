
package ru.bars_open.medvtr.amqp.consumer.hospitalization.pharmacy.generated.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Мoves complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Мoves">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Move" type="{http://schemas.xmlsoap.org/soap/envelope}StationaryMoving" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "\u041coves", propOrder = {
    "move"
})
public class Мoves {

    @XmlElement(name = "Move")
    protected List<StationaryMoving> move;

    /**
     * Gets the value of the move property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the move property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMove().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StationaryMoving }
     * 
     * 
     */
    public List<StationaryMoving> getMove() {
        if (move == null) {
            move = new ArrayList<StationaryMoving>();
        }
        return this.move;
    }

}
