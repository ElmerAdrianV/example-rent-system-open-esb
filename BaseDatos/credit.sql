-- Crear tabla `credito` para referencias cruzadas de financiamiento
CREATE TABLE credito (
    credito_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    customer_id INT NOT NULL, -- Relación con customers
    credit_amount DECIMAL(10, 2) NOT NULL, -- Cantidad de crédito asignada
    credit_used DECIMAL(10, 2) DEFAULT 0,  -- Cantidad de crédito utilizada
    CONSTRAINT fk_credit_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Insertar datos en `credito`
INSERT INTO credit (customer_id, credit_amount, credit_used)
VALUES
(1, 5000.00, 0.00),
(2, 10000.00, 2000.00),
(3, 7000.00, 1000.00);
