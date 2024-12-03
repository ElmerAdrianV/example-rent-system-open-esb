/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachades;

import entities.Credit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manri
 */
@Stateless
public class CreditFacade extends AbstractFacade<Credit> {

    @PersistenceContext(unitName = "WS_CreditoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CreditFacade() {
        super(Credit.class);
    }
    
}
