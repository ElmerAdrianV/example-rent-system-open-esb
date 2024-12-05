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
        
        // Inicializar las listas de clientes y vehículos
        List<wsreservation.Customers> listaCltes = listCustomers();
        List<wsreservation.Vehicles> listaVehic = listVehicles();
        
        int num_cltes = listaCltes.size();
        int num_vehicles = listaVehic.size();

        // Verificar si las listas están vacías
        if (num_cltes == 0 || num_vehicles == 0) {
            System.err.println("Las listas de clientes o vehículos están vacías.");
            return;
        }

        long t0, t1, dt;
        int vez = 5;  // Número de repeticiones para la prueba de estrés

        // Marcar tiempo de inicio
        t0 = System.currentTimeMillis();

        // Ejecutar el estrés
        for (int i = 0; i < vez; i++) {
            // Inicialización de variables
            int customerId, vehicleId;
            Date startDate, endDate;
            String report = "Reservation created via stress test";
            int queClte, queVeh = 0;

            // Seleccionar cliente aleatorio
            queClte = (int) (num_cltes * Math.random());
            customerId = listaCltes.get(queClte).getCustomerId();

            // Seleccionar vehículo aleatorio
            queVeh = (int) (num_vehicles * Math.random());
            vehicleId = listaVehic.get(queVeh).getVehicleId();

            // Generar fechas aleatorias para la reservación
            long currentTime = System.currentTimeMillis();
            startDate = new Date(currentTime + (long) (Math.random() * 1000000));  // Fecha de inicio aleatoria
            endDate = new Date(startDate.getTime() + (long) (Math.random() * 1000000));  // Fecha de fin aleatoria

            System.out.println("-----------------------------------------------");
            System.out.println("Vez: " + (i + 1) + ", Clte: " + customerId + ", Vehículo: " + vehicleId);
            System.out.println("-----------------------------------------------");

            // Crear la reservación
            XMLGregorianCalendar xmlStartDate = toXMLGregorianCalendar(startDate);
            XMLGregorianCalendar xmlEndDate = toXMLGregorianCalendar(endDate);
            int reservationId = createReservation(customerId, vehicleId, xmlStartDate, xmlEndDate, report);

            // Mostrar el resultado de la creación de la reservación
            if (reservationId != 0) {
                System.out.println("El número de reservación es: " + reservationId);
            } else {
                System.out.println("Hubo un error en la reservación");
            }

            System.out.println("===============================================");
        }

        // Marcar tiempo de fin y calcular el delta
        t1 = System.currentTimeMillis();
        dt = t1 - t0;
        System.out.println("Tiempo de respuesta total: " + dt + " milisegundos.");
    }

    // Método que simula la consulta de clientes
    private static List<wsreservation.Customers> listCustomers() {
        // Este método debería devolver una lista de clientes desde la base de datos o API.
        return new java.util.ArrayList<>();
    }

    // Método que simula la consulta de vehículos
    private static List<wsreservation.Vehicles> listVehicles() {
        // Este método debería devolver una lista de vehículos desde la base de datos o API.
        return new java.util.ArrayList<>();
    }

    // Método que convierte una fecha en un XMLGregorianCalendar
    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (javax.xml.datatype.DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean solicitudEntrega(int idTda, int idReservation, java.lang.String name, java.lang.String email, java.lang.String phone, java.lang.String address, java.lang.String cityRegion) {
        wsrent.WSRent_Service service = new wsrent.WSRent_Service();
        wsrent.WSRent port = service.getWSRentPort();
        return port.solicitudEntrega(idTda, idReservation, name, email, phone, address, cityRegion);
    }


    
}
