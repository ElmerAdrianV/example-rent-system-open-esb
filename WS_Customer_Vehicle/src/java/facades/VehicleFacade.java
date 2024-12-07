/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Vehicle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elmer
 */
@Stateless
public class VehicleFacade extends AbstractFacade<Vehicle> {

    @PersistenceContext(unitName = "WS_Customer_VehiclePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehicleFacade() {
        super(Vehicle.class);
    }
    
}
