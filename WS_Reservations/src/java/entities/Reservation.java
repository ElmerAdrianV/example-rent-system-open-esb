/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elmer
 */
@Entity
@Table(name = "RESERVATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
    , @NamedQuery(name = "Reservation.findByReservationId", query = "SELECT r FROM Reservation r WHERE r.reservationId = :reservationId")
    , @NamedQuery(name = "Reservation.findByCustomerId", query = "SELECT r FROM Reservation r WHERE r.customerId = :customerId")
    , @NamedQuery(name = "Reservation.findByVehicleId", query = "SELECT r FROM Reservation r WHERE r.vehicleId = :vehicleId")
    , @NamedQuery(name = "Reservation.findByStartDate", query = "SELECT r FROM Reservation r WHERE r.startDate = :startDate")
    , @NamedQuery(name = "Reservation.findByEndDate", query = "SELECT r FROM Reservation r WHERE r.endDate = :endDate")
    , @NamedQuery(name = "Reservation.findByReport", query = "SELECT r FROM Reservation r WHERE r.report = :report")
    , @NamedQuery(name = "Reservation.findByActive", query = "SELECT r FROM Reservation r WHERE r.active = :active")
    , @NamedQuery(name = "Reservation.findByFinished", query = "SELECT r FROM Reservation r WHERE r.finished = :finished")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMER_ID")
    private int customerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VEHICLE_ID")
    private int vehicleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "REPORT")
    private String report;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Column(name = "FINISHED")
    private Boolean finished;

    public Reservation() {
    }

    public Reservation(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Reservation(Integer reservationId, int customerId, int vehicleId, Date startDate, Date endDate, String report) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.report = report;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reservation[ reservationId=" + reservationId + " ]";
    }
    
}
