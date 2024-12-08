
package carrent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para InputComplexType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InputComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCustomer" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idVehicle" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idStore" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="report" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startDateS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="finishDateS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputComplexType", propOrder = {
    "idCustomer",
    "idVehicle",
    "idStore",
    "report",
    "startDateS",
    "finishDateS"
})
public class InputComplexType {

    protected int idCustomer;
    protected int idVehicle;
    protected int idStore;
    @XmlElement(required = true)
    protected String report;
    @XmlElement(required = true)
    protected String startDateS;
    @XmlElement(required = true)
    protected String finishDateS;

    /**
     * Obtiene el valor de la propiedad idCustomer.
     * 
     */
    public int getIdCustomer() {
        return idCustomer;
    }

    /**
     * Define el valor de la propiedad idCustomer.
     * 
     */
    public void setIdCustomer(int value) {
        this.idCustomer = value;
    }

    /**
     * Obtiene el valor de la propiedad idVehicle.
     * 
     */
    public int getIdVehicle() {
        return idVehicle;
    }

    /**
     * Define el valor de la propiedad idVehicle.
     * 
     */
    public void setIdVehicle(int value) {
        this.idVehicle = value;
    }

    /**
     * Obtiene el valor de la propiedad idStore.
     * 
     */
    public int getIdStore() {
        return idStore;
    }

    /**
     * Define el valor de la propiedad idStore.
     * 
     */
    public void setIdStore(int value) {
        this.idStore = value;
    }

    /**
     * Obtiene el valor de la propiedad report.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReport() {
        return report;
    }

    /**
     * Define el valor de la propiedad report.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReport(String value) {
        this.report = value;
    }

    /**
     * Obtiene el valor de la propiedad startDateS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDateS() {
        return startDateS;
    }

    /**
     * Define el valor de la propiedad startDateS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDateS(String value) {
        this.startDateS = value;
    }

    /**
     * Obtiene el valor de la propiedad finishDateS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinishDateS() {
        return finishDateS;
    }

    /**
     * Define el valor de la propiedad finishDateS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinishDateS(String value) {
        this.finishDateS = value;
    }

}
