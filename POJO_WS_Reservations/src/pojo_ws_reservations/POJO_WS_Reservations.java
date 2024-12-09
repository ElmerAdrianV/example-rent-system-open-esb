/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_reservations;

/**
 *
 * @author manri
 */
public class POJO_WS_Reservations implements interfazservicioestres.InterfazServiciosEstres{

    /**
     * @param args the command line arguments
     */
    long quienSoy;
    String host = null;
    
    @Override
    public boolean inizializa(int quienSoy) {
    this.quienSoy = quienSoy;
    return true;
    }

    @Override
    public long invocaServicio(int vez) {
        int idTda, vehicleId, customerId, reservationId;
        String startDate, endDate, returnDate, report, finalReport;
        long t0, t1, dt;
            
        t0 = System.currentTimeMillis();

        idTda = (int) (10.0 * Math.random());
        vehicleId = (int) (10.0 * Math.random());
        customerId = (int) (10.0 * Math.random());
        reservationId = (int) (100.0 * Math.random()); // Suponiendo IDs de reserva entre 0 y 99
        startDate = "2024-12-" + (10 + (int) (15.0 * Math.random())); // Fechas entre 2024-12-10 y 2024-12-25
        endDate = "2024-12-" + (20 + (int) (10.0 * Math.random()));   // Fechas entre 2024-12-20 y 2024-12-30
        returnDate = "2024-12-31";
        report = "Generated test report for reservation.";
        finalReport = "Final report for return.";

        try {
            // Intentar realizar una solicitud de reserva
            if (reservationRequest(idTda, vehicleId, customerId, startDate, endDate, report)) {
                System.out.println("Reserva creada exitosamente para cliente " + customerId 
                    + ", vehículo " + vehicleId + ", tienda " + idTda 
                    + " del " + startDate + " al " + endDate);
            } else {
                System.out.println("Fallo al crear la reserva para cliente " + customerId);
            }
        } catch (Exception ex) {
            System.out.println("Error al solicitar la reserva: " + ex.toString());
        }

        try {
            // Intentar recoger una reserva
            if (pickupReservation(reservationId)) {
                System.out.println("Reserva " + reservationId + " recogida exitosamente.");
            } else {
                System.out.println("Fallo al recoger la reserva " + reservationId);
            }
        } catch (Exception ex) {
            System.out.println("Error al recoger la reserva: " + ex.toString());
        }

        try {
            // Intentar devolver una reserva
            int result = dropReservation(reservationId, returnDate, finalReport);
            if (result == 0) {
                System.out.println("Reserva " + reservationId + " devuelta exitosamente.");
            } else {
                System.out.println("Error al devolver la reserva " + reservationId 
                    + ". Código de resultado: " + result);
            }
        } catch (Exception ex) {
            System.out.println("Error al devolver la reserva: " + ex.toString());
        }
        t1 = System.currentTimeMillis();

        dt = t1 - t0;
        
        return dt;
    }

    @Override
    public void cierra() {
        System.out.println("El thread de stress " + this.quienSoy + " ha terminado su trabajo"); 
    }
 
    // =========================================================================
    //                    main para probar el pojo  
    // =========================================================================

    public static void main(String[] args) {
        POJO_WS_Reservations objServ = new POJO_WS_Reservations();
        
        objServ.inizializa(25);
        int n_veces = args.length > 0 ? Integer.parseInt(args[0]):5;
        for( int vez = 1; vez <= n_veces; vez++)
            objServ.invocaServicio(vez);
        objServ.cierra(); 
    }
    
    // =========================================================================
    //                    Utilerías del WS 
    // =========================================================================
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
