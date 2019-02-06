
create database envios;
use envios;

CREATE TABLE camionero(
	dni VARCHAR(9)  NOT NULL,
	nombre VARCHAR(20),
	poblacion VARCHAR(15),
	telefono INT,
	salario DOUBLE(5,2),
	CONSTRAINT PK_camionero PRIMARY KEY (dni)
);

CREATE TABLE camion(
	matricula VARCHAR(7)  NOT NULL,
	potencia DOUBLE(4,2),
	modelo VARCHAR(15),
	tipo VARCHAR(20),
	CONSTRAINT PK_camion PRIMARY KEY (matricula)
);

CREATE TABLE reparto(
	id INT AUTO_INCREMENT NOT NULL,
	dniCamionero VARCHAR(9)  NOT NULL,
	matriculaCamion VARCHAR(7)  NOT NULL,
	fecha DATE,
    CONSTRAINT PK_reparto PRIMARY KEY (id),
	CONSTRAINT FK_camionero FOREIGN KEY (dniCamionero) REFERENCES camionero(dni),
	CONSTRAINT PK_camion FOREIGN KEY (matriculaCamion) REFERENCES camion(matricula)
);

CREATE TABLE paquete(
	codigo INT AUTO_INCREMENT NOT NULL,
	descripcion VARCHAR(20),
	destino VARCHAR(15),
	id_reparto INT,
    CONSTRAINT PK_paquete PRIMARY KEY (codigo),
	CONSTRAINT PK_repartoPa FOREIGN KEY (id_reparto) REFERENCES reparto(id)
);