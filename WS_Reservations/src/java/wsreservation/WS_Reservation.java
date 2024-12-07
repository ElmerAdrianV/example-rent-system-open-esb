/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsreservation;

import entities.Reservation;
import facades.ReservationFacade;
import java.util.Date;
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
    private ReservationFacade reservationFacade;// Add business logic below. (Right-click in editor and choose
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
        reservationFacade.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Reservation entity) {
        reservationFacade.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Reservation entity) {
        reservationFacade.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Reservation find(@WebParam(name = "id") Object id) {
        return reservationFacade.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Reservation> findAll() {
        return reservationFacade.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Reservation> findRange(@WebParam(name = "range") int[] range) {
        return reservationFacade.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return reservationFacade.count();
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

    @WebMethod(operationName = "pickupReservation")
    public boolean pickupReservation(@WebParam(name = "reservation_id") int reservationId) {
        try {
            Reservation reservation = reservationFacade.find(reservationId);

            if (reservation == null) {
                throw new IllegalArgumentException("La reservación con ID " + reservationId + " no existe.");
            }

            if (reservation.getActive()) {
                throw new IllegalArgumentException("La reservación ya está activa.");
            }

            reservation.setActive(true);
            reservationFacade.edit(reservation);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Reservación con ID " + reservationId + " marcada como activa.");
            return true;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @WebMethod(operationName = "dropReservation")
    public int dropReservation(@WebParam(name = "reservation_id") int reservationId,
            @WebParam(name = "return_date") String returnDate,
            @WebParam(name = "final_report") String finalReport) throws IllegalArgumentException {
        int rentalDays = 0;

        try {
            // Buscar la reservación por ID
            Reservation reservation = reservationFacade.find(reservationId);

            if (reservation == null) {
                throw new IllegalArgumentException("La reservación con ID " + reservationId + " no existe.");
            }

            if (reservation.getFinished()) {
                throw new IllegalArgumentException("La reservación ya ha sido marcada como finalizada.");
            }

            // Validar y parsear la fecha de regreso
            Date returnDateParsed;
            try {
                returnDateParsed = java.sql.Date.valueOf(returnDate);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Formato de fecha inválido para la fecha de regreso. Use el formato yyyy-MM-dd.", ex);
            }

            // Verificar si la fecha de regreso está antes o después del final de la reservación
            Date endDate = reservation.getEndDate();
            Date effectiveDate = returnDateParsed.after(endDate) ? returnDateParsed : endDate;

            // Calcular días rentados como el mayor entre endDate y returnDate
            rentalDays = (int) ((effectiveDate.getTime() - reservation.getStartDate().getTime()) / (1000 * 60 * 60 * 24));

            // Actualizar la reservación
            reservation.setEndDate(returnDateParsed); // Actualizar a la fecha real de regreso
            reservation.setActive(false);
            reservation.setFinished(true);
            reservation.setReport(finalReport);
            reservationFacade.edit(reservation);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Reservación con ID " + reservationId + " finalizada. Total de días rentados: " + rentalDays);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al procesar la devolución de la reservación.", ex);
        }

        return rentalDays;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "mis_reservaciones")
    public String mis_reservaciones(@WebParam(name = "customer_id") int customer_id) {
        StringBuilder sb = new StringBuilder();
        try {
            // Realiza la consulta a la base de datos para obtener las reservaciones del cliente
            List<Reservation> reservaciones = reservationFacade.findReservationsByCustomerId(customer_id);

            // Verifica si el cliente tiene reservaciones
            if (reservaciones.isEmpty()) {
                return "El cliente con ID " + customer_id + " no ha hecho ninguna reservación.";
            }

            // Construye el mensaje con las reservaciones
            sb.append("Reservaciones del cliente con ID ").append(customer_id).append(":");
            for (Reservation r : reservaciones) {
                sb.append("\n\nID de reservación: ").append(r.getReservationId())
                  .append("\n   Fecha de inicio: ").append(r.getStartDate())
                  .append("\n   Fecha de fin: ").append(r.getEndDate())
                  .append("\n   ID del vehículo:  ").append(r.getVehicleId())
                  .append("\n   Estado: ").append(r.getActive() ? "Activa" : "Finalizada");
            }
        } catch (Exception e) {
            sb.append("Ocurrió un error al consultar las reservaciones: ").append(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "reportes_coche")
    public String reportes_coche(@WebParam(name = "vehicle_id") int vehicle_id) {
        StringBuilder sb = new StringBuilder();
        try {
            // Realiza la consulta a la base de datos para obtener las reservaciones del cliente
            List<Reservation> reservaciones = reservationFacade.findReservationsByVehicleId(vehicle_id);

            // Verifica si el cliente tiene reservaciones
            if (reservaciones.isEmpty()) {
                return "El coche con ID " + vehicle_id + " no ha tenido reportes.";
            }

            // Construye el mensaje con las reservaciones
            sb.append("Reservaciones del coche con ID ").append(vehicle_id).append(":");
            for (Reservation r : reservaciones) {
                sb.append("\n\nID de reservación: ").append(r.getReservationId())
                  .append("\n   Reporte: ").append(r.getReport());
            }
        } catch (Exception e) {
            sb.append("Ocurrió un error al consultar las reservaciones: ").append(e.getMessage());
        }
        return sb.toString();
    }

}
