-- Conexión a la base de datos
CONNECT 'jdbc:derby://localhost:1527/db_project_customer_vehicles;user=app;password=app';

-- Eliminar tabla existente si es necesario
DROP TABLE vehicle;
DROP TABLE customer;

-- Crear tabla `customer` (clientes)
CREATE TABLE customer (
    customer_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,         -- Nombre del cliente
    last_name VARCHAR(50) NOT NULL,          -- Apellido del cliente
    nationality VARCHAR(50) NOT NULL,        -- Nacionalidad
    date_of_birth DATE NOT NULL,             -- Fecha de nacimiento
    license_number VARCHAR(20) NOT NULL UNIQUE, -- Número de licencia de conducir
    address VARCHAR(255) NOT NULL,           -- Dirección completa
    bank_account VARCHAR(25) NOT NULL UNIQUE -- Número de cuenta bancaria
);

-- Insertar datos en `customer`
INSERT INTO customer (first_name, last_name, nationality, date_of_birth, license_number, address, bank_account)
VALUES
('Juan', 'Pérez', 'Mexicana', '1990-05-15', 'JPMX123456', 'Calle Falsa 123, CDMX, México', '12345678901234567890'),
('Maria', 'Gómez', 'Estadounidense', '1985-03-22', 'MGUSA654321', '456 Elm Street, Los Angeles, USA', '09876543210987654321'),
('Carlos', 'López', 'Canadiense', '1992-11-10', 'CLCAN987654', '789 Maple Ave, Toronto, Canada', '11122233344455566677'),
('Ditirambo', 'Farfulla', 'Mexicana', '1992-12-10', 'DFMX987624', 'Arroyo Bajo 10 int 10', '123456'),
('Gandulfo', 'Roncante', 'Mexicana', '1999-12-10', 'GRMX087624', 'Arroyo Bajo 10 int 12', '654321'),
('Vagonzo', 'Durmiente', 'Mexicana', '1992-02-01', 'VDMX387124', 'Arroyo Bajo 10 int 45', '123459'),
('Hambrosio', 'Comensal', 'Mexicana', '1992-10-21', 'HCMX387124', 'Arroyo Bajo 10 int 58', '183456');

-- Crear tabla `vehicle` (vehículos)
CREATE TABLE vehicle (
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

-- Insertar datos en `vehicle`
INSERT INTO vehicle (license_plate, brand, model, years, category, fuel_type, capacity, daily_rate, status, last_service_date)
VALUES
('ABC123', 'Toyota', 'Corolla', 2020, 'automóvil', 'gasolina', 5, 350.00, 'available', '2024-11-01'),
('DEF456', 'Ford', 'F-150', 2019, 'camioneta', 'diésel', 3, 500.00, 'available', '2024-10-15'),
('GHI789', 'Tesla', 'Model S', 2021, 'automóvil', 'eléctrico', 5, 800.00, 'rented', '2024-11-20');

-- Desconectar
DISCONNECT;
