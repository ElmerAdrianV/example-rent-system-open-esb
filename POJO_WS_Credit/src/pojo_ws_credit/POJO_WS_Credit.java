/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_credit;

import ws_credit.ExcepNoClient_Exception;
import ws_credit.ExcepNoCredit_Exception;

/**
 *
 * @author manri
 */
public class POJO_WS_Credit implements interfazservicioestres.InterfazServiciosEstres{

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
       int id_clte;
       double dblMonto = 0.0;
       long t0, t1, dt;
       
       t0 = System.currentTimeMillis();
       

           dblMonto = 100.0 * Math.random();
           id_clte  = (int)(10.0 * Math.random());
           
           try
           {
               if( autorizaDouble(id_clte,dblMonto))
               {
                   System.out.println("Se ha autorizado el credito de " + dblMonto + " para el id_clte " +id_clte);
               }
               else
               {
                   System.out.println("No se ha autorizado el credito de " + dblMonto + " para el id_clte " +id_clte);
               }
           }
           catch( Exception ex)
           {
               System.out.println(ex.toString());
           }
               
       
       t1 = System.currentTimeMillis();
       
       dt = t1 -t0;
       
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
        POJO_WS_Credit objServ = new POJO_WS_Credit();
        
        objServ.inizializa(25);
        int n_veces = args.length > 0 ? Integer.parseInt(args[0]):5;
        for( int vez = 1; vez <= n_veces; vez++)
            objServ.invocaServicio(vez);
        objServ.cierra(); 
    }   
    
    // =========================================================================
    //                    Utilerías del WS 
    // =========================================================================
    private static boolean autorizaDouble(int idClte, double dblMonto) throws ExcepNoClient_Exception, ExcepNoCredit_Exception {
        ws_credit.WSCredito service = new ws_credit.WSCredito();
        ws_credit.WSCredit port = service.getWSCreditPort();
        return port.autorizaDouble(idClte, dblMonto);
    }    
    
}
