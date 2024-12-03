/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsrent;

import entities.ActiveRent;
import fachades.ActiveRentFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author manri
 */
@WebService(serviceName = "WSRent")
public class WSRent {

    @Resource(mappedName="jms/SolicitudEnviosFactory")
    private  ConnectionFactory connectionFactory;

    @Resource(mappedName="java:app/jms/SolicitudEnvios")
    private  Queue queue; 
    
    @EJB
    private ActiveRentFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") ActiveRent entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") ActiveRent entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") ActiveRent entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public ActiveRent find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<ActiveRent> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<ActiveRent> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    /**
     * Web service operation
     * @param id_Tda
     * @param id_Reservation
     * @param name
     * @param email
     * @param phone
     * @param address
     * @param city_region
     * @return 
     */
    @WebMethod(operationName = "solicitudEnvio")
    public boolean solicitudEnvio(@WebParam(name = "id_Tda")      int    id_Tda, 
                                  @WebParam(name = "id_Reservation")   int    id_Reservation,
                                  @WebParam(name = "name")        String name, 
                                  @WebParam(name = "email")       String email, 
                                  @WebParam(name = "phone")       String phone, 
                                  @WebParam(name = "address")     String address, 
                                  @WebParam(name = "city_region") String city_region) 
    {
        boolean blnRes = false;
        
        entities.ActiveRent active_rent = new entities.ActiveRent();
        
        active_rent.setIdTda(id_Tda);
        active_rent.setIdReservation(id_Reservation);
        active_rent.setName(name);
        active_rent.setEmail(email);
        active_rent.setPhone(phone);
        active_rent.setAddress(address);
        active_rent.setCityRegion(city_region);
        
        try 
        {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);

            ObjectMessage message = session.createObjectMessage();
            
            message.setObject(active_rent);
            messageProducer.send(message);
            
            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Encolando solicitud de reservación para:" + active_rent.getName() + ", para el pedido:" + active_rent.getIdReservation() + " co email:" + active_rent.getEmail());

            messageProducer.close();
            connection.close();

            blnRes = true;
        }
        catch (Exception ex) 
        {
            blnRes = false;
        }

        return blnRes;
    }
    
}
