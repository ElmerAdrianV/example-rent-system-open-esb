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
    
    @WebMethod(operationName = "CustomerExistance")
    public boolean CustomerExistance(int idCustomer) throws CustomerExistanceException {
        List<Customer> customers = listCustomers(); // Llama al método que devuelve todos los clientes

        // Verifica si existe un cliente con el ID proporcionado
        for (Customer customer : customers) {
            if (customer.getCustomerId() == idCustomer) {
                return true; // Si se encuentra, devuelve true
            }
        }

        // Si no se encuentra, lanza la excepción personalizada
        throw new CustomerExistanceException("El cliente con ID " + idCustomer + " no existe.");
    }
    
    @WebMethod(operationName = "VehicleExistance")
    public boolean VehicleExistance(int idVehicle) throws VehicleExistanceException {
        List<Vehicle> vehicles = listVehicles(); // Llama al método que devuelve todos los vehículos

        // Verifica si existe un vehículo con el ID proporcionado
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == idVehicle) {
                return true; // Si se encuentra, devuelve true
            }
        }

        // Si no se encuentra, lanza la excepción personalizada
        throw new VehicleExistanceException("El vehículo con ID " + idVehicle + " no existe.");
    }

    
}
