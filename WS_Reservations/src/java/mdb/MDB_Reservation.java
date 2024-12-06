/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.Reservation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
/**
 *
 * @author RGAMBOAH
 */

@JMSDestinationDefinition(name = "java:app/jms/ReservationRequest", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "SolicitudEnvios")

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/ReservationRequest"),
    @ActivationConfigProperty(propertyName = "destinationType",   propertyValue = "javax.jms.Queue")
})
public class MDB_Reservation implements MessageListener {
    
    @Resource
    private MessageDrivenContext mdc;
    
    @PersistenceContext(unitName = "WS_ReservationsPU")
    private EntityManager em;
    
    public MDB_Reservation() {
    }
    
    @Override
    public void onMessage(Message message) 
    {
      ObjectMessage msg = null;
       
      try 
      {
        if (message instanceof ObjectMessage) {
            msg = (ObjectMessage) message;
            Reservation reservation = (Reservation) msg.getObject();
            
//            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
//                    "Recibiendo solicitud de envio para:" + envio.getName() + ", para el pedido:" + envio.getIdCustomerOrder());

            
            save(reservation);
        }
      } 
      catch (JMSException e) 
      {
        e.printStackTrace();
        mdc.setRollbackOnly();
      } 
      catch (Throwable te) 
      {
        te.printStackTrace();
      }      
    }

    public void save(Object object) {
        em.persist(object);
        em.flush();
    }
    
}
