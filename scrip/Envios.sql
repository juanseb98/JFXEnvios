-- CREATE USER 'admin'@'localhost' IDENTIFIED BY 'root1234';
-- GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';
drop database envios;
create database envios;
use envios;

CREATE TABLE camionero(
	dni VARCHAR(9)  NOT NULL,
	nombre VARCHAR(20),
	pwd VARCHAR(30),
	poblacion VARCHAR(15),
	telefono INT UNIQUE,
	salario DOUBLE(7,2),
	logueado TINYINT,
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
	descripcion VARCHAR(30),
	destino VARCHAR(15),
	entregado TINYINT,
	id_reparto INT,
	CONSTRAINT PK_paquete PRIMARY KEY (codigo),
	CONSTRAINT PK_repartoPa FOREIGN KEY (id_reparto) REFERENCES reparto(id)
);

INSERT INTO camionero(dni,pwd,nombre,poblacion,telefono,salario,logueado)  VALUES("20503879T", "password", "Juan", "Sevilla", 697366754, 1400.0,0);
INSERT INTO camionero(dni,pwd,nombre,poblacion,telefono,salario,logueado)  VALUES("12345678A", "password", "Pedro", "Sevilla", 685965365, 1500.0,0);
INSERT INTO camionero(dni,pwd,nombre,poblacion,telefono,salario,logueado)  VALUES("78945612A", "password", "Antonio", "Malaga", 632587693, 1632.0,0);

INSERT INTO camion(matricula,modelo,potencia,tipo) VALUES("1234jdk", "Mercedes", 25.0, "Diesel");
INSERT INTO camion(matricula,modelo,potencia,tipo) VALUES("2356dse", "Cadilac", 30.0, "Gasolina");
INSERT INTO camion(matricula,modelo,potencia,tipo) VALUES("1568red", "Toledo", 50.0, "Electrico");

INSERT INTO paquete(descripcion,destino,entregado) VALUES("Paquete grande", "Sevilla", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Paquete pequeño", "Malaga", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Paquete mediano", "Alcala", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Paquete diminuto", "Sevilla", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Paquete enorme", "Cadiz", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Spice", "Koralle", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Artichokes", "Terri", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Stock", "Johanna", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Wine La Vielle", "Rafaelita", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Parsley Italian", "Tabbie", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Ginger", "Kirstin", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Chips", "Brien", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Salt And Pepper Mix", "Darsie", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Lettuce", "Lorraine", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Flour", "Bamby", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Nantucket", "Barbabas", false);
INSERT INTO paquete(descripcion,destino,entregado) VALUES("Easy Off Oven Cleaner", "Cordula", false);

INSERT INTO reparto(dniCamionero,matriculaCamion,fecha) VALUES("20503879T","1234jdk","2019-02-20");
