/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_reservations;

import ws_customer_vehicles.CustomerExistanceException_Exception;
import ws_customer_vehicles.VehicleExistanceException_Exception;

/**
 *
 * @author manri
 */
public class POJO_WS_Reservations {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    private static boolean reservationRequest(int idTda, int vehicleId, int customerId, java.lang.String startDate, java.lang.String endDate, java.lang.String report) {
        ws_reservations.WSReservation_Service service = new ws_reservations.WSReservation_Service();
        ws_reservations.WSReservation port = service.getWSReservationPort();
        return port.reservationRequest(idTda, vehicleId, customerId, startDate, endDate, report);
    }

    private static boolean pickupReservation(int reservationId) {
        ws_reservations.WSReservation_Service service = new ws_reservations.WSReservation_Service();
        ws_reservations.WSReservation port = service.getWSReservationPort();
        return port.pickupReservation(reservationId);
    }

    private static int dropReservation(int reservationId, java.lang.String returnDate, java.lang.String finalReport) {
        ws_reservations.WSReservation_Service service = new ws_reservations.WSReservation_Service();
        ws_reservations.WSReservation port = service.getWSReservationPort();
        return port.dropReservation(reservationId, returnDate, finalReport);
    }
    
    
    
    
}
