drop database envios;
create database envios;
use envios;

CREATE TABLE camionero(
	dni VARCHAR(9)  NOT NULL,
	nombre VARCHAR(20),
	poblacion VARCHAR(15),
	telefono INT,
	salario DOUBLE(7,2),
	-- idx2 INTEGER,
	CONSTRAINT PK_camionero PRIMARY KEY (dni)
);

CREATE TABLE camion(
	matricula VARCHAR(7)  NOT NULL,
	potencia DOUBLE(4,2),
	modelo VARCHAR(15),
	tipo VARCHAR(20),
	-- idx1 INTEGER,
	CONSTRAINT PK_camion PRIMARY KEY (matricula)
);

CREATE TABLE reparto(
	id INT AUTO_INCREMENT NOT NULL,
	dniCamionero VARCHAR(9),
	matriculaCamion VARCHAR(7),
	fecha DATE,
    	CONSTRAINT PK_reparto PRIMARY KEY (id),
	CONSTRAINT FK_camionero FOREIGN KEY (dniCamionero) REFERENCES camionero(dni),
	CONSTRAINT FK_camion FOREIGN KEY (matriculaCamion) REFERENCES camion(matricula)
);

CREATE TABLE paquete(
	codigo INT AUTO_INCREMENT NOT NULL,
	descripcion VARCHAR(20),
	destino VARCHAR(15),
	entregado TINYINT,
	id_reparto INT,
    CONSTRAINT PK_paquete PRIMARY KEY (codigo),
	CONSTRAINT PK_repartoPa FOREIGN KEY (id_reparto) REFERENCES reparto(id)
);