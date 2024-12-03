-- Conexión a la base de datos
CONNECT 'jdbc:derby://localhost:1527/bd_proyecto_credit;user=app;password=app';

-- Eliminar tabla existente si es necesario
DROP TABLE credit;

CREATE  TABLE  credit
(
  id INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
     (START WITH 1 ,INCREMENT BY 1) 
     CONSTRAINT CUSTOMER_PK PRIMARY KEY,
  name        VARCHAR(45) NOT NULL ,
  email       VARCHAR(45) NOT NULL ,
  phone       VARCHAR(45) NOT NULL ,
  address     VARCHAR(45) NOT NULL ,
  city_region VARCHAR(2) NOT NULL ,
  cc_number   VARCHAR(19) NOT NULL,
  credito     DECIMAL(10,2) NOT NULL ,  
  );

INSERT INTO credit (name,email,phone,address,city_region,cc_number,credito) VALUES
('Ditirambo Farfulla','ditirambo.farfulla@mkt.bond','56284000','Arroyo Bajo 10 int 10','AO','123456',1000.0),
('Gandulfo Roncante','gandulfo.roncante@mkt.bond','56284000','Arroyo Bajo 10 int 12','AO','123456',1000.0),
('Vagonzo Durmiente','vagonzo.durminte@mkt.bond','56284000','Arroyo Bajo 10 int 45','AO','123456',1000.0),
('Hambrosio Comensal','hambrosio.comensal@mkt.bond','56284000','Arroyo Bajo 10 int 58','AO','123456',1000.0);

-- Desconectar
DISCONNECT;
