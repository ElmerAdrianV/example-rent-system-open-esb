connect 'jdbc:derby://localhost:1527/db_project_active_rent;user=app;password=app';

DROP TABLE active_rent;

------------------------------- 
--     ENVIOS
-------------------------------
CREATE  TABLE  active_rent 
(
  id INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
     (START WITH 1 ,INCREMENT BY 1) 
     CONSTRAINT CUSTOMER_PK PRIMARY KEY,
  id_Tda            INT NOT NULL,	 
  id_reservation INT NOT NULL,	 
  name              VARCHAR(45) NOT NULL ,
  email             VARCHAR(45) ,
  phone             VARCHAR(45) NOT NULL ,
  address           VARCHAR(45) NOT NULL ,
  city_region       VARCHAR(2) NOT NULL
  );

-------------------------------------
-- Ejemplos
-------------------------------------
--'Ditirambo Farfulla','ditirambo.farfulla@mkt.bond','56284000','Arroyo Bajo 10 int 10','AO'
--'Gandulfo Roncante','gandulfo.roncante@mkt.bond','56284000','Arroyo Bajo 10 int 12','AO'
--'Vagonzo Durmiente','vagonzo.durminte@mkt.bond','56284000','Arroyo Bajo 10 int 45','AO'
--'Hambrosio Comensal','hambrosio.comensal@mkt.bond','56284000','Arroyo Bajo 10 int 58','AO'
-------------------------------------



disconnect;