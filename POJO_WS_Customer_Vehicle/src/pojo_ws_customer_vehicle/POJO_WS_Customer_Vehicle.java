/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_customer_vehicle;

import ws_customer_vehicles.CustomerExistanceException_Exception;
import ws_customer_vehicles.VehicleExistanceException_Exception;

/**
 *
 * @author manri
 */
public class POJO_WS_Customer_Vehicle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int id_customer, id_vehicle;
        int vez = 20;
        boolean resp1, resp2;

        
        long t0, t1, dt;
        
        t0 = System.currentTimeMillis();
        for(int i=0; i<vez; i++){
            id_customer = (int) (Math.random() * 20) + 1;
            id_vehicle = (int) (Math.random() * 20) + 1;
            
            try {
                resp1 = customerExistance(id_customer); // Llama al método que verifica la existencia del cliente
                System.out.println("El cliente con ID " + id_customer + " existe.");
            } catch (CustomerExistanceException_Exception e) {
                // Maneja la excepción lanzada cuando el cliente no existe
                resp1 = false;
                System.err.println("Error: " + e.getMessage());
            }
            try {
                resp2 = vehicleExistance(id_vehicle); // Llama al método que verifica la existencia del vehículo
                System.out.println("El vehículo con ID " + id_vehicle + " existe.");
            } catch (VehicleExistanceException_Exception e) {
                // Maneja la excepción lanzada cuando el vehículo no existe
                resp2 = false;
                System.err.println("Error: " + e.getMessage());
            }  
        }
        t1 = System.currentTimeMillis();
        dt = t1 - t0;
        System.out.println("==========Tiempo tomado: " + dt + "==========");
    }

    private static boolean customerExistance(int arg0) throws CustomerExistanceException_Exception {
        ws_customer_vehicles.WSCustomerVehicles_Service service = new ws_customer_vehicles.WSCustomerVehicles_Service();
        ws_customer_vehicles.WSCustomerVehicles port = service.getWSCustomerVehiclesPort();
        return port.customerExistance(arg0);
    }

    private static boolean vehicleExistance(int arg0) throws VehicleExistanceException_Exception {
        ws_customer_vehicles.WSCustomerVehicles_Service service = new ws_customer_vehicles.WSCustomerVehicles_Service();
        ws_customer_vehicles.WSCustomerVehicles port = service.getWSCustomerVehiclesPort();
        return port.vehicleExistance(arg0);
    }

    

    
}
