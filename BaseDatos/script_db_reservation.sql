-- Conexión a la base de datos
CONNECT 'jdbc:derby://localhost:1527/bd_project_reservations;user=app;password=app';

-- Eliminar tabla existente si es necesario
DROP TABLE reservations;
DROP TABLE vehicles;
DROP TABLE customers;

-- Crear tabla `customers` (clientes)
CREATE TABLE customers (
    customer_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,         -- Nombre del cliente
    last_name VARCHAR(50) NOT NULL,          -- Apellido del cliente
    nationality VARCHAR(50) NOT NULL,        -- Nacionalidad
    date_of_birth DATE NOT NULL,             -- Fecha de nacimiento
    license_number VARCHAR(20) NOT NULL UNIQUE, -- Número de licencia de conducir
    address VARCHAR(255) NOT NULL,           -- Dirección completa
    bank_account VARCHAR(25) NOT NULL UNIQUE -- Número de cuenta bancaria
);

-- Insertar datos en `customers`
INSERT INTO customers (first_name, last_name, nationality, date_of_birth, license_number, address, bank_account)
VALUES
('Juan', 'Pérez', 'Mexicana', '1990-05-15', 'JPMX123456', 'Calle Falsa 123, CDMX, México', '12345678901234567890'),
('Maria', 'Gómez', 'Estadounidense', '1985-03-22', 'MGUSA654321', '456 Elm Street, Los Angeles, USA', '09876543210987654321'),
('Carlos', 'López', 'Canadiense', '1992-11-10', 'CLCAN987654', '789 Maple Ave, Toronto, Canada', '11122233344455566677');

-- Crear tabla `vehicles` (vehículos)
CREATE TABLE vehicles (
    vehicle_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    license_plate VARCHAR(20) NOT NULL UNIQUE, -- Placa única del vehículo
    brand VARCHAR(50) NOT NULL,               -- Marca del vehículo (e.g., Toyota, Ford)
    model VARCHAR(50) NOT NULL,               -- Modelo del vehículo (e.g., Corolla, F-150)
    years INT NOT NULL,                        -- Año de fabricación
    category VARCHAR(20) NOT NULL,           -- Categoría (automóvil, camioneta, camión)
    fuel_type VARCHAR(20) NOT NULL,          -- Tipo de combustible (gasolina, diésel, eléctrico)
    capacity INT NOT NULL,                   -- Capacidad de pasajeros o carga
    daily_rate DECIMAL(10, 2) NOT NULL,      -- Tarifa diaria de renta
    status VARCHAR(20) NOT NULL DEFAULT 'available', -- Estado (available, rented, maintenance)
    last_service_date DATE                    -- Fecha del último servicio de mantenimiento
);

-- Insertar datos en `vehicles`
INSERT INTO vehicles (license_plate, brand, model, years, category, fuel_type, capacity, daily_rate, status, last_service_date)
VALUES
('ABC123', 'Toyota', 'Corolla', 2020, 'automóvil', 'gasolina', 5, 350.00, 'available', '2024-11-01'),
('DEF456', 'Ford', 'F-150', 2019, 'camioneta', 'diésel', 3, 500.00, 'available', '2024-10-15'),
('GHI789', 'Tesla', 'Model S', 2021, 'automóvil', 'eléctrico', 5, 800.00, 'rented', '2024-11-20');

-- Crear tabla `reservations` (reservaciones)
CREATE TABLE reservations (
    reservation_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    customer_id INT NOT NULL,                -- Identificador del cliente (relación con customers)
    vehicle_id INT NOT NULL,                 -- Identificador del vehículo (relación con vehicles)
    start_date DATE NOT NULL,                -- Fecha de inicio de la renta
    end_date DATE NOT NULL,                  -- Fecha de entrega del vehículo
    report  VARCHAR(1000) NOT NULL,          -- Reporte detallado de la reservación
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Insertar datos en `reservations`
INSERT INTO reservations (customer_id, vehicle_id, start_date, end_date, report)
VALUES
(1, 1, '2024-12-01', '2024-12-10', 'El cliente ha solicitado un Toyota Corolla para un viaje de negocios. Todo en regla.'),
(2, 2, '2024-11-25', '2024-12-02', 'Renta de una Ford F-150 para mudanza. Cliente pidió extensión del seguro.'),
(3, 3, '2024-11-30', '2024-12-05', 'Se rentó un Tesla Model S. Sin reportes adicionales.');


-- Desconectar
DISCONNECT;
