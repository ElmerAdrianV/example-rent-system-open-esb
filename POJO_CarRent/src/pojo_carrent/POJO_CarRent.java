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
public class POJO_CarRent implements interfazservicioestres.InterfazServiciosEstres {

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
        
        carrent.CarRentCAService1 service = new carrent.CarRentCAService1();
        carrent.CarRentWSDLPortType port = service.getCasaPort1();
        
        long t0, t1, dt;
        Random random = new Random();
        InputComplexType input = new InputComplexType();
        OutputComplexType output = new OutputComplexType();
        

        int idCustomer    = random.nextInt(4);
        int idVehicle     = random.nextInt(4);
        int idStore       = random.nextInt(5);
        String report     = "Prueba " + vez + " con BPEL";
        String startDate  = "2024-" + (random.nextInt(6) + 1) + "-" + (random.nextInt(27)+1);
        String finishDate = "2024-" + (random.nextInt(4) + 8) + "-" + (random.nextInt(27)+1);
        System.out.println("Customer Id: "+ idCustomer +", fecha ini: "+ startDate+", fecha fin: "+ finishDate);

        input.setIdCustomer(idCustomer);
        input.setIdVehicle(idVehicle);
        input.setIdStore(idStore);
        input.setReport(report);
        input.setStartDateS(startDate);
        input.setFinishDateS(finishDate);

        System.out.println("-----------------------------------------------");
        System.out.println("Estresador:" + this.quienSoy + ", vez:" + vez + ", Customer Id:" + idCustomer);
        System.out.println("-----------------------------------------------");
        
        t0 = System.currentTimeMillis();
        
        try {
            output = port.reservation(input);
            System.out.println("The reservation was made successfully.");
            System.out.println("    Vehicle Id: " + idVehicle + " | Start date: " + startDate + " | Finish date: " + finishDate);
        } catch (ReservationFault ex) {
            System.out.println("Reservation could not be made." + "\n");
        }  
        
        System.out.println("===============================================");
        
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
        POJO_CarRent objServ = new POJO_CarRent();
        
        objServ.inizializa(25);
        int n_veces = args.length > 0 ? Integer.parseInt(args[0]):5;
        for( int vez = 1; vez <= n_veces; vez++)
            objServ.invocaServicio(vez);
        objServ.cierra(); 
    }


    // =========================================================================
    //                    Utilerías del WS 
    // =========================================================================
 
    private static OutputComplexType reservation(carrent.InputComplexType reservationInput) throws ReservationFault {
        carrent.CarRentCAService1 service = new carrent.CarRentCAService1();
        carrent.CarRentWSDLPortType port = service.getCasaPort1();
        return port.reservation(reservationInput);
    }
}
