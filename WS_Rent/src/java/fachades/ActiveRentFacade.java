/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachades;

import entities.ActiveRent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manri
 */
@Stateless
public class ActiveRentFacade extends AbstractFacade<ActiveRent> {

    @PersistenceContext(unitName = "WS_RentaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiveRentFacade() {
        super(ActiveRent.class);
    }
    
}
