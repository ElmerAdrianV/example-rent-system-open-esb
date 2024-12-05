/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachades;

import entities.Reservations;
import static entities.Reservations_.vehicleId;
import entities.Vehicles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author manri
 */
@Stateless
public class ReservationsFacade extends AbstractFacade<Reservations> {

    @PersistenceContext(unitName = "WS_ReservationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationsFacade() {
        super(Reservations.class);
    }

    public void create(Reservations reservation) {
        em.persist(reservation);  // Guarda la entidad en la base de datos
        em.flush();  // Sincroniza con la base de datos
    }
      /**
     * Método para encontrar los reportes de los vehiculos
     *
     * @param vehicleId El ID del vehiculo.
     * @return Lista de productos ordenados asociados al pedido.
     */
    public java.util.List<Reservations> findByVehicle(Vehicles vehicle) {
        TypedQuery<Reservations> query = em.createNamedQuery("Reservations.findByVehicleId", Reservations.class);
        query.setParameter("vehicleId", vehicle);
        return query.getResultList();
    }
}
