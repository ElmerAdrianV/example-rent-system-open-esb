/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wscredit;

import entities.Credit;
import entities.ExcepNoClient;
import entities.ExcepNoCredit;
import fachades.CreditFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author manri
 */
@WebService(serviceName = "WSCredito")
public class WSCredit {

    @EJB
    private CreditFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Credit entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Credit entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Credit entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Credit find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Credit> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Credit> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    /**
     * Web service operation
     * @param id_clte
     * @param monto
     * @return 
     * @throws entidades.ExcepNoCredito
     * @throws entidades.ExcepNoExisteClte
     */
    @WebMethod(operationName = "autoriza")
    public boolean autoriza(@WebParam(name = "id_clte") final int id_clte, @WebParam(name = "monto") final BigDecimal monto) throws ExcepNoCredit, ExcepNoClient {
        
        boolean blnAutorizado = ejbRef.updateCredit(id_clte, monto);
        
        return blnAutorizado;
    }
    
    @WebMethod(operationName = "autoriza_double")
    public boolean autoriza_double(@WebParam(name = "id_clte") final int id_clte, @WebParam(name = "dbl_monto") final double dbl_monto) throws ExcepNoCredit, ExcepNoClient {
        
        BigDecimal monto = BigDecimal.valueOf(dbl_monto);

        monto.setScale(2,BigDecimal.ROUND_HALF_UP);

        boolean blnAutorizado = ejbRef.updateCredit(id_clte, monto);
        
        return blnAutorizado;
    }
    
    
}
