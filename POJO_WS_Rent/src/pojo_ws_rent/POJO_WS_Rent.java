/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo_ws_rent;
/**
 *
 * @author manri
 */
public class POJO_WS_Rent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.util.List<wsrent.Reservations>  listaReser    = new java.util.ArrayList<>();
        
        listaReser = listReservations();
        int num_reser = listaReser.size();

        // Verificar si las listas están vacías
        if (num_reser == 0) {
            System.err.println("Las listas de entrega está vacía.");
            return;
        }

        long t0, t1, dt;
        int vez = 5;  // Número de repeticiones para la prueba de estrés

        // Marcar tiempo de inicio
        t0 = System.currentTimeMillis();

        // Ejecutar el estrés
        for (int i = 0; i < vez; i++) {
            // Inicialización de variables
            int ReservationId;
            String report = "Reservation created via stress test";
            int queRes = 0;
            int idTda = (int) (Math.random()*10)+1;
            String nombre = ("Nombre" + i);
            String email = (nombre + "@hotmail.com");
            long numero = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
            String Snumero = String.valueOf(numero);
            int CP = (int) (Math.random() * 90_000) + 10_000;
            String direccion = ("Calle" + i + ", CP: " + CP);
            String region = "México";

            // Seleccionar vehículo aleatorio
            queRes = (int) (num_reser * Math.random());
            ReservationId = listaReser.get(queRes).getReservationId();

            System.out.println("-----------------------------------------------");
            System.out.println("Vez: " + (i + 1) + "Store: " + idTda + ", Reservation: " + ReservationId);
            System.out.println("-----------------------------------------------");


            boolean resp = solicitudEntrega(idTda, ReservationId, nombre, email, Snumero, direccion, region);

            // Mostrar el resultado de la creación de la reservación
            if (resp) {
                System.out.println("Su entrega esta lista");
            } else {
                System.out.println("Hubo un error en la entrega");
            }

            System.out.println("===============================================");
        }

        // Marcar tiempo de fin y calcular el delta
        t1 = System.currentTimeMillis();
        dt = t1 - t0;
        System.out.println("Tiempo de respuesta total: " + dt + " milisegundos.");
    }

    private static boolean solicitudEntrega(int idTda, int idReservation, java.lang.String name, java.lang.String email, java.lang.String phone, java.lang.String address, java.lang.String cityRegion) {
        wsrent.WSRent_Service service = new wsrent.WSRent_Service();
        wsrent.WSRent port = service.getWSRentPort();
        return port.solicitudEntrega(idTda, idReservation, name, email, phone, address, cityRegion);
    }

    private static java.util.List<wsrent.Reservations> listReservations() {
        wsrent.WSRent_Service service = new wsrent.WSRent_Service();
        wsrent.WSRent port = service.getWSRentPort();
        return port.listReservations();
    }
    
    
}
