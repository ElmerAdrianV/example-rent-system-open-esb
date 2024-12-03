/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manri
 */
@Entity
@Table(name = "VEHICLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicles.findAll", query = "SELECT v FROM Vehicles v")
    , @NamedQuery(name = "Vehicles.findByVehicleId", query = "SELECT v FROM Vehicles v WHERE v.vehicleId = :vehicleId")
    , @NamedQuery(name = "Vehicles.findByLicensePlate", query = "SELECT v FROM Vehicles v WHERE v.licensePlate = :licensePlate")
    , @NamedQuery(name = "Vehicles.findByBrand", query = "SELECT v FROM Vehicles v WHERE v.brand = :brand")
    , @NamedQuery(name = "Vehicles.findByModel", query = "SELECT v FROM Vehicles v WHERE v.model = :model")
    , @NamedQuery(name = "Vehicles.findByYears", query = "SELECT v FROM Vehicles v WHERE v.years = :years")
    , @NamedQuery(name = "Vehicles.findByCategory", query = "SELECT v FROM Vehicles v WHERE v.category = :category")
    , @NamedQuery(name = "Vehicles.findByFuelType", query = "SELECT v FROM Vehicles v WHERE v.fuelType = :fuelType")
    , @NamedQuery(name = "Vehicles.findByCapacity", query = "SELECT v FROM Vehicles v WHERE v.capacity = :capacity")
    , @NamedQuery(name = "Vehicles.findByDailyRate", query = "SELECT v FROM Vehicles v WHERE v.dailyRate = :dailyRate")
    , @NamedQuery(name = "Vehicles.findByStatus", query = "SELECT v FROM Vehicles v WHERE v.status = :status")
    , @NamedQuery(name = "Vehicles.findByLastServiceDate", query = "SELECT v FROM Vehicles v WHERE v.lastServiceDate = :lastServiceDate")})
public class Vehicles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VEHICLE_ID")
    private Integer vehicleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LICENSE_PLATE")
    private String licensePlate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BRAND")
    private String brand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MODEL")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEARS")
    private int years;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FUEL_TYPE")
    private String fuelType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPACITY")
    private int capacity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAILY_RATE")
    private BigDecimal dailyRate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "LAST_SERVICE_DATE")
    @Temporal(TemporalType.DATE)
    private Date lastServiceDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleId")
    private List<Reservations> reservationsList;

    public Vehicles() {
    }

    public Vehicles(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehicles(Integer vehicleId, String licensePlate, String brand, String model, int years, String category, String fuelType, int capacity, BigDecimal dailyRate, String status) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.years = years;
        this.category = category;
        this.fuelType = fuelType;
        this.capacity = capacity;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(Date lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    @XmlTransient
    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehicleId != null ? vehicleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicles)) {
            return false;
        }
        Vehicles other = (Vehicles) object;
        if ((this.vehicleId == null && other.vehicleId != null) || (this.vehicleId != null && !this.vehicleId.equals(other.vehicleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vehicles[ vehicleId=" + vehicleId + " ]";
    }
    
}
