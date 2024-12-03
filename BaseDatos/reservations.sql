-- Conexión a la base de datos
CONNECT 'jdbc:derby://localhost:1527/db_project_reservations;user=app;password=app';

-- Eliminar tabla existente si es necesario
DROP TABLE reservations;

CREATE TABLE reservations (
    reservation_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    customer_id INT NOT NULL,                -- Identificador del cliente (relación con customers)
    vehicle_id INT NOT NULL,                 -- Identificador del vehículo (relación con vehicles)
    start_date DATE NOT NULL,                -- Fecha de inicio de la renta
    end_date DATE NOT NULL,                  -- Fecha de entrega del vehículo
    report  VARCHAR(1000) NOT NULL,                             -- Reporte detallado de la reservación
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Ejemplo de inserción de datos
INSERT INTO reservations (customer_id, vehicle_id, start_date, end_date, report)
VALUES
(1, 1, '2024-12-01', '2024-12-10', 'El cliente ha solicitado un Toyota Corolla para un viaje de negocios. Todo en regla.'),
(2, 2, '2024-11-25', '2024-12-02', 'Renta de una Ford F-150 para mudanza. Cliente pidió extensión del seguro.'),
(3, 3, '2024-11-30', '2024-12-05', 'Se rentó un Tesla Model S. Sin reportes adicionales.');
-- Desconectar
DISCONNECT;
