-- CREATE USER 'guardias'@'localhost' identified with sha256_password by '';
-- GRANT ALL ON guardiascamp.* TO 'guardias'@'localhost';

DROP DATABASE IF EXISTS guardiascamp;
CREATE DATABASE guardiascamp;

USE guardiascamp;

DROP TABLE IF EXISTS profesor;
CREATE TABLE profesor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    apellidos VARCHAR(60),
    numero INT,
    abreviacion varchar(60),
    nif VARCHAR(9),
    contrasenya TEXT,
    admin BOOLEAN DEFAULT FALSE,
    direccion VARCHAR(256),
    telefono VARCHAR(9),
    email VARCHAR(256),
    activo BOOL DEFAULT TRUE
);

DROP TABLE IF EXISTS materia;
CREATE TABLE materia (
	id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT,
	  abreviacion VARCHAR(60),
    nombre VARCHAR(60),
    codigo VARCHAR(60),
    horas INT
);

DROP TABLE IF EXISTS aula;
CREATE TABLE aula (
	id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT,
    abreviacion VARCHAR(60),
    nombre VARCHAR(60)
);

DROP TABLE IF EXISTS intervalo;
CREATE TABLE intervalo (
	id INT PRIMARY KEY AUTO_INCREMENT,
    horainicio time,
    horafin time
);

INSERT INTO intervalo (horainicio, horafin)
VALUES
("08:20:00", "09:15:00"),
("09:15:00", "10:10:00"),
("10:10:00", "11:00:00"),
("11:25:00", "12:20:00"),
("12:20:00", "13:10:00"),
("13:10:00", "14:00:00"),
("14:10:00", "15:00:00"),
("15:15:00", "16:05:00"),
("16:05:00", "17:00:00"),
("17:00:00", "17:50:00"),
("18:10:00", "19:00:00"),
("19:00:00", "19:50:00"),
("19:50:00", "20:40:00"),
("20:50:00", "21:40:00");

DROP TABLE IF EXISTS dia;
CREATE TABLE dia (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    abreviacion VARCHAR(1)
);

DROP TABLE IF EXISTS grupo;
CREATE TABLE grupo (
	id INT PRIMARY KEY AUTO_INCREMENT,
	numero INT,
    abreviacion VARCHAR(60),
    nombre VARCHAR(60),
    curso VARCHAR(60)
);

DROP TABLE IF EXISTS curso;
CREATE TABLE curso (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    abreviacion VARCHAR(60)
);

INSERT INTO curso (nombre, abreviacion)
VALUES 
("Curso 2024-2025", "24/25"),
("Curso 2025-2026", "25/26");

DROP TABLE IF EXISTS sesion;
CREATE TABLE sesion (
	id INT PRIMARY KEY AUTO_INCREMENT,
    idprofesor INT,
    idmateria INT,
    idgrupo INT,
    idaula INT,
    idintervalo INT,
    idcurso INT,
    iddia INT,
    FOREIGN KEY(idprofesor) REFERENCES profesor(id),
    FOREIGN KEY(idmateria) REFERENCES materia(id),
    FOREIGN KEY(idgrupo) REFERENCES grupo(id),
    FOREIGN KEY(idaula) REFERENCES aula(id),
    FOREIGN KEY(idintervalo) REFERENCES intervalo(id),
    FOREIGN KEY(idcurso) REFERENCES curso(id),
    FOREIGN KEY(iddia) REFERENCES dia(id)
);

DROP TABLE IF EXISTS cargo;
CREATE TABLE cargo (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    abreviacion VARCHAR(60)
);

INSERT INTO cargo (nombre, abreviacion)
VALUES
("COOR", "Coordinador"),
("IT1", "Itinerante 1"),
("IT2", "Itinerante 2"),
("IT3", "Itinerante 3"),
("IT4", "Itinerante 4"),
("IT5", "Itinerante 5"),
("IT6", "Itinerante 6"),
("P0", "Planta 0"),
("P1", "Planta 1"),
("P2", "Planta 2"),
("CONVIV", "Convivencia");

DROP TABLE IF EXISTS cuadrante;
CREATE TABLE cuadrante (
	id INT PRIMARY KEY AUTO_INCREMENT,
    idcargo INT,
    idguardia INT,
    fecha DATE,
    incidencias TEXT,
    firma TEXT,
    deberes BOOLEAN,
    FOREIGN KEY(idcargo) REFERENCES cargo(id),
    FOREIGN KEY(idguardia) REFERENCES sesion(id)
);

DROP TABLE IF EXISTS sesionfalta;
CREATE TABLE sesionfalta (
	id INT PRIMARY KEY AUTO_INCREMENT,
    idsesion INT,
    idcuadrante INT,
    FOREIGN KEY(idsesion) REFERENCES sesion(id),
    FOREIGN KEY(idcuadrante) REFERENCES cuadrante(id)
);
