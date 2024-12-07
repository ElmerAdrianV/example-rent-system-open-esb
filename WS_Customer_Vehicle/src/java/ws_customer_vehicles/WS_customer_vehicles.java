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
}
