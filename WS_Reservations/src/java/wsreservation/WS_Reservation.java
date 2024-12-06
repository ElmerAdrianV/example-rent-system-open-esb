/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsreservation;

import entities.Reservation;
import facades.ReservationFacade;
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

@WebService(serviceName = "WS_Reservation")
public class WS_Reservation {

    @Resource(mappedName="java:app/jms/SolicitudReservationFactory")
    private  ConnectionFactory connectionFactory;

    @Resource(mappedName="java:app/jms/ReservationRequest")
    private  Queue queue; 
    
    @EJB
    private ReservationFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Reservation entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Reservation entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Reservation entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Reservation find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Reservation> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Reservation> findRange(@WebParam(name = "range") int[] range) {
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
        
        entities.Reservation reservation = new entities.Reservation();
        
//        Reservation.setIdTda(id_Tda);
//        Reservation.setIdReservation(id_Reservation);
//        Reservation.setName(name);
//        active_rent.setEmail(email);
//        active_rent.setPhone(phone);
//        active_rent.setAddress(address);
//        active_rent.setCityRegion(city_region);
        
        try 
        {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);

            ObjectMessage message = session.createObjectMessage();
            
            message.setObject(reservation);
            messageProducer.send(message);
            
//            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
//                    "Encolando solicitud de reservación para:" + reservation.getName() + ", para el pedido:" + reservation.getIdReservation() + " co email:" + active_rent.getEmail());

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
