-- Conexión a la base de datos
CONNECT 'jdbc:derby://localhost:1527/bd_proyecto_customers;user=app;password=app';

-- Eliminar tabla existente si es necesario
DROP TABLE customers;

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

-- Ejemplo de inserción de datos
INSERT INTO customers (first_name, last_name, nationality, date_of_birth, license_number, address, bank_account)
VALUES
('Juan', 'Pérez', 'Mexicana', '1990-05-15', 'JPMX123456', 'Calle Falsa 123, CDMX, México', '12345678901234567890'),
('Maria', 'Gómez', 'Estadounidense', '1985-03-22', 'MGUSA654321', '456 Elm Street, Los Angeles, USA', '09876543210987654321'),
('Carlos', 'López', 'Canadiense', '1992-11-10', 'CLCAN987654', '789 Maple Ave, Toronto, Canada', '11122233344455566677');
-- Desconectar
DISCONNECT;
