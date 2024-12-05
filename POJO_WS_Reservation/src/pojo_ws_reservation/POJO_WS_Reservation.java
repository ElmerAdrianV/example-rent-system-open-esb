/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_reservation;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author manri
 */
public class POJO_WS_Reservation {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        
    java.util.List<wsreservation.Customers> listaCltes = new java.util.ArrayList<>();
    java.util.List<wsreservation.Vehicles>  listaVehic = new java.util.ArrayList<>();
    
    int num_cltes;
    int num_vehicles;    

    listaCltes    = listCustomers();
    listaVehic    = listVehicles();
    num_cltes     = listaCltes.size();
    num_vehicles  = listaVehic.size();
    
    if (num_cltes == 0 || num_vehicles == 0) {
        System.err.println("Las listas de clientes o vehículos están vacías.");
        return;
    }
    
    long t0, t1, dt;
    int vez = 5;

    // Marcar tiempo de inicio
    t0 = System.currentTimeMillis();
    
    for(int i=0;i<vez;i++){
        // Inicialización de variables
        int customerId, vehicleId;
        Date startDate, endDate;
        String report = "Reservation created via stress test";
        int queClte, queVeh = 0;

        // Seleccionar cliente aleatorio
        queClte = (int) (num_cltes * Math.random());
        customerId = listaCltes.get(queClte).getCustomerId();

        // Seleccionar vehículo aleatorio
        queVeh = (int) (num_vehicles * Math.random());
        vehicleId = listaVehic.get(queVeh).getVehicleId();

        // Generar fechas aleatorias para la reservación
        long currentTime = System.currentTimeMillis();
        startDate = new Date(currentTime + (long) (Math.random() * 1000000)); // Fecha de inicio aleatoria
        endDate = new Date(startDate.getTime() + (long) (Math.random() * 1000000)); // Fecha de fin aleatoria

        System.out.println("-----------------------------------------------");
        System.out.println("Vez: " + i + ", Clte: " + customerId + ", Vehículo: " + vehicleId);
        System.out.println("-----------------------------------------------");



        // Crear la reservación
        XMLGregorianCalendar xmlStartDate = toXMLGregorianCalendar(startDate);
        XMLGregorianCalendar xmlEndDate = toXMLGregorianCalendar(endDate);
        int reservationId = createReservation(customerId, vehicleId, xmlStartDate, xmlEndDate, report);
        if(reservationId != 0){
            System.out.println("El número de reservación es: " + reservationId);
        }else{
            System.out.println("Hubo un error en la reservación");
        }
        System.out.println("===============================================");
    };
    // Marcar tiempo de fin y calcular el delta
    t1 = System.currentTimeMillis();
    dt = t1 - t0;
        System.out.println("Tiempo de respuesta: " + dt);

    }

    private static int createReservation(int customerId, int vehicleId, javax.xml.datatype.XMLGregorianCalendar startDate, javax.xml.datatype.XMLGregorianCalendar endDate, java.lang.String report) {
        wsreservation.WSReservation_Service service = new wsreservation.WSReservation_Service();
        wsreservation.WSReservation port = service.getWSReservationPort();
        return port.createReservation(customerId, vehicleId, startDate, endDate, report);
    }
    

    private static java.util.List<wsreservation.Customers> listCustomers() {
        wsreservation.WSReservation_Service service = new wsreservation.WSReservation_Service();
        wsreservation.WSReservation port = service.getWSReservationPort();
        return port.listCustomers();
    }

    private static java.util.List<wsreservation.Vehicles> listVehicles() {
        wsreservation.WSReservation_Service service = new wsreservation.WSReservation_Service();
        wsreservation.WSReservation port = service.getWSReservationPort();
        return port.listVehicles();
    }
    
    
    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }    
    
}
