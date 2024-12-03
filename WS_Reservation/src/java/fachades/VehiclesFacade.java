/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachades;

import entities.Vehicles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manri
 */
@Stateless
public class VehiclesFacade extends AbstractFacade<Vehicles> {

    @PersistenceContext(unitName = "WS_ReservationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiclesFacade() {
        super(Vehicles.class);
    }
    
}
