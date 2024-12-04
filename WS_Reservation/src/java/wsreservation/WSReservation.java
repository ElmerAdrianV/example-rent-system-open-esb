package wsreservation;

import fachades.*;
import entities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService(serviceName = "WSReservation")
public class WSReservation {

    @EJB
    private CustomersFacade customerFacade;
    @EJB
    private VehiclesFacade vehicleFacade;
    @EJB
    private ReservationsFacade reservationFacade;

    @WebMethod(operationName = "createReservation")
    public int createReservation(
            @WebParam(name = "customerId") int customerId,
            @WebParam(name = "vehicleId") int vehicleId,
            @WebParam(name = "startDate") Date startDate,
            @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "report") String report) {

        try {
            Customers customer = customerFacade.find(customerId);
            Vehicles vehicle = vehicleFacade.find(vehicleId);

            if (customer == null || vehicle == null) {
                Logger.getLogger(this.getClass().getName())
                        .log(Level.WARNING, "Customer or Vehicle not found.");
                return 0; // Indicar error
            }

            if (!"available".equalsIgnoreCase(vehicle.getStatus())) {
                Logger.getLogger(this.getClass().getName())
                        .log(Level.WARNING, "Vehicle not available for reservation.");
                return 0; // Vehículo no disponible
            }

            // Crear la reservación
            Reservations reservation = new Reservations();
            reservation.setCustomerId(customer);
            reservation.setVehicleId(vehicle);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setReport(report);

            reservationFacade.create(reservation);

            // Actualizar el estado del vehículo a "rented"
            vehicle.setStatus("rented");
            vehicleFacade.edit(vehicle);

            Logger.getLogger(this.getClass().getName())
                    .log(Level.INFO, "Reservation created successfully with ID: " + reservation.getReservationId());

            return reservation.getReservationId();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.SEVERE, "Error creating reservation: " + e.getMessage(), e);
            return 0; // Indicar error
        }
    }

    @WebMethod(operationName = "cancelReservation")
    public boolean cancelReservation(@WebParam(name = "reservationId") int reservationId) {
        try {
            Reservations reservation = reservationFacade.find(reservationId);
            if (reservation == null) {
                Logger.getLogger(this.getClass().getName())
                        .log(Level.WARNING, "Reservation not found.");
                return false;
            }

            Vehicles vehicle = reservation.getVehicleId();
            if (vehicle != null) {
                vehicle.setStatus("available");
                vehicleFacade.edit(vehicle);
            }

            reservationFacade.remove(reservation);
            Logger.getLogger(this.getClass().getName())
                    .log(Level.INFO, "Reservation cancelled successfully.");
            return true;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.SEVERE, "Error cancelling reservation: " + e.getMessage(), e);
            return false;
        }
    }
    
    @WebMethod(operationName = "listCustomers")
    public List<Customers> listCustomers() {
        return customerFacade.findAll();
    }

    @WebMethod(operationName = "listVehicles")
    public List<Vehicles> listVehicles() {
        return vehicleFacade.findAll();
    }

    @WebMethod(operationName = "listReservations")
    public List<Reservations> listReservations() {
        return reservationFacade.findAll();
    }
    
    /**
     * Web service operation
     * @param vehicleId
     * @return 
     */
    @WebMethod(operationName = "getReportsByCar")
    public java.util.List<String> getReportsByCar(@WebParam(name = "vehicleId") int vehicleId) {
        //TODO write your implementation code here:
        Vehicles vehicle = vehicleFacade.find(vehicleId);
        java.util.List<Reservations> reservations = reservationFacade.findByVehicle(vehicle);
        List<String> reportsListByVehicle = new ArrayList<String>();
        for(Reservations res : reservations){
            reportsListByVehicle.add(res.getReport());
        }

        return reportsListByVehicle;
    }
}
