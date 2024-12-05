/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsreservation;

/**
 *
 * @author manri
 */
public class ClsItem {
    int id_vehicle;
    int id_costumer;

    public Integer getVehicleId() {
        return id_vehicle;
    }

    public void setVehicleId(Integer vehicleId) {
        this.id_vehicle = vehicleId;
    }   
    
    public Integer getCustomerId() {
        return id_costumer;
    }

    public void setCustomerId(Integer customerId) {
        this.id_costumer = customerId;
    }
    
}
