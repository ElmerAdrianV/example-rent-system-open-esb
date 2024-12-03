/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author manri
 */
@JMSDestinationDefinition(name = "java:app/jms/SolicitudReservation", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "SolicitudReservation")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/SolicitudReservation")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewReservation implements MessageListener {
    
    public NewReservation() {
    }
    
    @Override
    public void onMessage(Message message) {
    }
    
}
