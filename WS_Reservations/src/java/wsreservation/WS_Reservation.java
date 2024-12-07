/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsreservation;

import entities.Reservation;
import facades.ReservationFacade;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "WS_Reservation")
public class WS_Reservation {

    @Resource(mappedName = "jms/ReservationReservationFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:app/jms/ReservationRequest")
    private Queue queue;

    @EJB
    private ReservationFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Reservation entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Reservation entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Reservation entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Reservation find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Reservation> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Reservation> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "ReservationRequest")
    public boolean reservationRequest(@WebParam(name = "id_Tda") int id_Tda,
            @WebParam(name = "vehicle_id") int vehicleId,
            @WebParam(name = "customer_id") int customerId,
            @WebParam(name = "start_date") String startDate,
            @WebParam(name = "end_date") String endDate,
            @WebParam(name = "report") String report) throws IllegalArgumentException {
        boolean isSuccess = false;
        // Validar formato de fechas
        Date startDateParsed;
        Date endDateParsed;

        try {
            startDateParsed = java.sql.Date.valueOf(startDate);
            endDateParsed = java.sql.Date.valueOf(endDate);
        } catch (IllegalArgumentException ex) {
            IllegalArgumentException exBadParse = new IllegalArgumentException("Formato de fecha inválido o las fechas no están en el orden correcto. Use el formato yyyy-MM-dd.", ex);
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, exBadParse.getMessage(), exBadParse);
            throw exBadParse;
        }

        if (!startDateParsed.before(endDateParsed)) {
            IllegalArgumentException ex = new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de finalización.");
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            throw ex;

        }

        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            // Crear productor de mensajes
            MessageProducer producer = session.createProducer(queue);

            // Crear objeto Reservation
            Reservation reservation = new Reservation();
            reservation.setCustomerId(customerId);
            reservation.setVehicleId(vehicleId);
            reservation.setStartDate(startDateParsed);
            reservation.setEndDate(endDateParsed);
            reservation.setReport(report != null ? report : "Reservation has not been started");
            reservation.setActive(true);
            reservation.setFinished(false);

            // Crear mensaje JMS
            ObjectMessage message = session.createObjectMessage();
            message.setObject(reservation);

            // Enviar mensaje a la cola
            producer.send(message);

            isSuccess = true;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return isSuccess;
    }

}
