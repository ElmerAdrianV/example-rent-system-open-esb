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
import facades.ReservationFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import wsreservation.VehicleNotAvailableException;

/**
 *
 * @author RGAMBOAH
 */
@JMSDestinationDefinition(name = "java:app/jms/ReservationRequest", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "ReservationRequest")

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/ReservationRequest")
    ,
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MDB_Reservation implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    @EJB
    private ReservationFacade reservationFacade;

    @PersistenceContext(unitName = "WS_ReservationsPU")
    private EntityManager em;

    public MDB_Reservation() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMessage = (ObjectMessage) message;
                Reservation reservation = (Reservation) objMessage.getObject();

                // Verificar disponibilidad
                boolean isAvailable = reservationFacade.checkAvailability(
                        reservation.getVehicleId(),
                        reservation.getStartDate().toString(),
                        reservation.getEndDate().toString()
                );

                if (!isAvailable) {
                    throw new VehicleNotAvailableException(
                            "El vehículo no está disponible para las fechas solicitadas: "
                            + reservation.getStartDate() + " a " + reservation.getEndDate());
                }

                // Persistir la nueva reservación
                reservationFacade.create(reservation);

                Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                        "Reservación creada: " + reservation.getCustomerId());
            }
        } catch (VehicleNotAvailableException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
        } catch (JMSException | RuntimeException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(Object object) {
        em.persist(object);
        em.flush();
    }

}
