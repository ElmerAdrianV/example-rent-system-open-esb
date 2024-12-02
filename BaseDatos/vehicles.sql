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

-- Ejemplo de inserción de datos
INSERT INTO vehicles (license_plate, brand, model, years, category, fuel_type, capacity, daily_rate, status, last_service_date)
VALUES
('ABC123', 'Toyota', 'Corolla', 2020, 'automóvil', 'gasolina', 5, 350.00, 'available', '2024-11-01'),
('DEF456', 'Ford', 'F-150', 2019, 'camioneta', 'diésel', 3, 500.00, 'available', '2024-10-15'),
('GHI789', 'Tesla', 'Model S', 2021, 'automóvil', 'eléctrico', 5, 800.00, 'rented', '2024-11-20');
