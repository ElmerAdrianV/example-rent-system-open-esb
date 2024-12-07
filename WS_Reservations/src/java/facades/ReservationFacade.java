/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Reservation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elmer
 */
@Stateless
public class ReservationFacade extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "WS_ReservationsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationFacade() {
        super(Reservation.class);
    }
     public boolean checkAvailability(int vehicleId, String startDate, String endDate) {
        String jpql = "SELECT COUNT(r) FROM Reservation r " +
                      "WHERE r.vehicleId = :vehicleId " +
                      "AND r.active = true " +
                      "AND (r.startDate BETWEEN :startDate AND :endDate OR " +
                      "     r.endDate BETWEEN :startDate AND :endDate OR " +
                      "     :startDate BETWEEN r.startDate AND r.endDate)";

        Query query = em.createQuery(jpql);
        query.setParameter("vehicleId", vehicleId);
        query.setParameter("startDate", java.sql.Date.valueOf(startDate));
        query.setParameter("endDate", java.sql.Date.valueOf(endDate));

        Long count = (Long) query.getSingleResult();
        return count == 0; // Disponible si no hay intersecciones
    }
     
     public List<Reservation> findReservationsByCustomerId(int customerId) {
    return em.createQuery("SELECT r FROM Reservation r WHERE r.customerId = :customerId", Reservation.class)
             .setParameter("customerId", customerId)
             .getResultList();
    }
    
    public List<Reservation> findReservationsByVehicleId(int vehicleId) {
    return em.createQuery("SELECT r FROM Reservation r WHERE r.vehicleId = :customerId", Reservation.class)
             .setParameter("customerId", vehicleId)
             .getResultList();
    }
}
