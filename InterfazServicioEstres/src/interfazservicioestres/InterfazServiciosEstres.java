package interfazservicioestres;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manri
 */
public interface InterfazServiciosEstres {
    boolean inizializa(int quienSoy);
    long invocaServicio(int vez);
    void cierra();
    
}
