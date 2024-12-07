/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_customer_vehicles;

import entities.*;
import facades.*;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author elmer
 */
@WebService(serviceName = "WS_customer_vehicles")
public class WS_customer_vehicles {
    
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private VehicleFacade vehicleFacade;
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
        @WebMethod(operationName = "listCustomers")
    public List<Customer> listCustomers() {
        return customerFacade.findAll();
    }

    @WebMethod(operationName = "listVehicles")
    public List<Vehicle> listVehicles() {
        return vehicleFacade.findAll();
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "ver_coches")
    public String ver_coches() {
        StringBuilder sb = new StringBuilder();
        List<Vehicle> lista = listVehicles();
        
        sb.append("Todos los coches");
        for (Vehicle coche : lista){
            sb.append("\n\n Coche con id: ").append(coche.getVehicleId());
            sb.append("\n   Para rentar");
            sb.append("\n      Precio diario: $").append(coche.getDailyRate());
            sb.append("\n      Capacidad: ").append(coche.getCapacity());
            sb.append("\n      Disponibilidad: ").append(coche.getStatus());
            sb.append("\n   Sobre el coche");
            sb.append("\n      Placas: ").append(coche.getLicensePlate());
            sb.append("\n      Marca: ").append(coche.getBrand());
            sb.append("\n      Modelo: ").append(coche.getModel());
            sb.append("\n      Año: ").append(coche.getYears());
            sb.append("\n      Tipo de gasolina: ").append(coche.getFuelType());
            
        }
        return sb.toString();
    }   
}
