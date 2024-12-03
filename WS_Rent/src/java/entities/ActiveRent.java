/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manri
 */
@Entity
@Table(name = "ACTIVE_RENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActiveRent.findAll", query = "SELECT a FROM ActiveRent a")
    , @NamedQuery(name = "ActiveRent.findById", query = "SELECT a FROM ActiveRent a WHERE a.id = :id")
    , @NamedQuery(name = "ActiveRent.findByIdTda", query = "SELECT a FROM ActiveRent a WHERE a.idTda = :idTda")
    , @NamedQuery(name = "ActiveRent.findByIdReservation", query = "SELECT a FROM ActiveRent a WHERE a.idReservation = :idReservation")
    , @NamedQuery(name = "ActiveRent.findByName", query = "SELECT a FROM ActiveRent a WHERE a.name = :name")
    , @NamedQuery(name = "ActiveRent.findByEmail", query = "SELECT a FROM ActiveRent a WHERE a.email = :email")
    , @NamedQuery(name = "ActiveRent.findByPhone", query = "SELECT a FROM ActiveRent a WHERE a.phone = :phone")
    , @NamedQuery(name = "ActiveRent.findByAddress", query = "SELECT a FROM ActiveRent a WHERE a.address = :address")
    , @NamedQuery(name = "ActiveRent.findByCityRegion", query = "SELECT a FROM ActiveRent a WHERE a.cityRegion = :cityRegion")})
public class ActiveRent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TDA")
    private int idTda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RESERVATION")
    private int idReservation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "EMAIL")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PHONE")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CITY_REGION")
    private String cityRegion;

    public ActiveRent() {
    }

    public ActiveRent(Integer id) {
        this.id = id;
    }

    public ActiveRent(Integer id, int idTda, int idReservation, String name, String phone, String address, String cityRegion) {
        this.id = id;
        this.idTda = idTda;
        this.idReservation = idReservation;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.cityRegion = cityRegion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdTda() {
        return idTda;
    }

    public void setIdTda(int idTda) {
        this.idTda = idTda;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActiveRent)) {
            return false;
        }
        ActiveRent other = (ActiveRent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ActiveRent[ id=" + id + " ]";
    }
    
}
