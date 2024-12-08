/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_carrent;

import java.util.Random;
import carrent.OutputComplexType;
import carrent.InputComplexType;
import carrent.ReservationFault;
/**
 *
 * @author manri
 *//**
 *
 * @author manri
 */
public class POJO_CarRent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        carrent.CarRentCAService1 service = new carrent.CarRentCAService1();
        carrent.CarRentWSDLPortType port = service.getCasaPort1();
        
        javax.xml.ws.Holder<java.lang.String> holder = new javax.xml.ws.Holder<>();
        int VECES  = 20;
        VECES = args.length > 0 ? Integer.parseInt(args[0]): VECES;
        Random random = new Random();
        InputComplexType input = new InputComplexType();
        OutputComplexType output = new OutputComplexType();
        long t0 = System.currentTimeMillis();

        for(int i=0; i<VECES; i++){
            int idCustomer    = random.nextInt(10);
            int idVehicle     = random.nextInt(10);
            int idStore       = random.nextInt(5);
            String report     = "Prueba " + i + " con BPEL";
            String startDate  = "2024-" + random.nextInt(12) + "-" + random.nextInt(28);
            String finishDate = "2024-" + random.nextInt(12) + "-" + random.nextInt(28);
            
            input.setIdCustomer(idCustomer);
            input.setIdVehicle(idVehicle);
            input.setIdStore(idStore);
            input.setReport(report);
            input.setStartDateS(startDate);
            input.setFinishDateS(finishDate);
            
            try {
                output = port.reservation(input);
                System.out.println("The reservation was made successfully." + "\n");
            } catch (ReservationFault ex) {
                System.out.println("Reservation could not be made." + "\n");
            }
        long t1 = System.currentTimeMillis();
        double deltaT = 1e-3 * (t1-t0);
        System.out.println("Veces: " + VECES + " deltaT: " + deltaT + "segundos."); 
        }
   
    }

    private static OutputComplexType reservation(carrent.InputComplexType reservationInput) throws ReservationFault {
        carrent.CarRentCAService1 service = new carrent.CarRentCAService1();
        carrent.CarRentWSDLPortType port = service.getCasaPort1();
        return port.reservation(reservationInput);
    }
    
}
