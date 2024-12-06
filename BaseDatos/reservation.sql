CONNECT 'jdbc:derby://localhost:1527/db_project_active_rent;user=app;password=app';

-- Eliminar tabla si ya existe
DROP TABLE reservation;

------------------------------- 
--     RESERVATIONS
-------------------------------
-- Crear tabla `reservation` (reservacion)
CREATE TABLE reservations (
    reservation_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    customer_id INT NOT NULL,                -- Identificador del cliente (relación con customers)
    vehicle_id INT NOT NULL,                 -- Identificador del vehículo (relación con vehicles)
    start_date DATE NOT NULL,                -- Fecha de inicio de la renta
    end_date DATE NOT NULL,                  -- Fecha de entrega del vehículo
    report VARCHAR(1000) DEFAULT 'Reservation has not been started' NOT NULL, -- Reporte detallado de la reservación
    active BOOLEAN DEFAULT false,            -- Estado de la reservación (activa o no)
    finished BOOLEAN DEFAULT false           -- Estado final de la reservación (terminada o no)
);

INSERT INTO reservation (customer_id, vehicle_id, start_date, end_date, report, active, finished)
VALUES
(1, 1, '2024-12-01', '2024-12-10', 'El cliente ha solicitado un Toyota Corolla para un viaje de negocios. Todo en regla.', false, true),
(2, 2, '2024-11-25', '2024-12-02', 'Renta de una Ford F-150 para mudanza. Cliente pidió extensión del seguro.', false, true),
(3, 3, '2024-11-30', '2024-12-05', 'Se rentó un Tesla Model S. Sin reportes adicionales.', false, true);

-- Ejemplo de inserción que usará el valor predeterminado para `report`
INSERT INTO reservation (customer_id, vehicle_id, start_date, end_date)
VALUES
(4, 4, '2024-12-05', '2024-12-15');

DISCONNECT;
