/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachades;

import entities.Credit;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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
    // ====================================================================================================

    public boolean updateCredit(int id_clte,BigDecimal bd_monto_requerido) throws entities.ExcepNoCredit,entities.ExcepNoClient
    {
       boolean blnAutorizada = false;
       
       BigDecimal bd_monto_disponible;
        
       Credit credito   = em.find(Credit.class, new Integer(id_clte), LockModeType.PESSIMISTIC_WRITE);
       if(credito == null)
       {
         throw new entities.ExcepNoClient(id_clte);
       }
       else
       {  
         bd_monto_disponible = credito.getCredito();
         /*
         Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "PRE: Credito_id:" + credito.getId()  + ", monto disponible:"
                    + bd_monto_disponible.toString() + ", monto requerido:"
                    + bd_monto_requerido.toString());
         */
         if(bd_monto_disponible.compareTo(bd_monto_requerido) >= 0)
         {  
           bd_monto_disponible = bd_monto_disponible.subtract(bd_monto_requerido);
           credito.setCredito(bd_monto_disponible);
           this.edit(credito);
           em.flush();
           /*
           Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "POST:Credito_id:" + credito.getId()  + ", monto disponible:"
                    + bd_monto_disponible.toString() + ", monto requerido:"
                    + bd_monto_requerido.toString());
           */
           credito = null;
           blnAutorizada = true;
         }
         else
         {
           credito = null;  
           throw new entities.ExcepNoCredit(id_clte);
         }
       }
       
       return blnAutorizada;
    }
   
    // ====================================================================================================
}
