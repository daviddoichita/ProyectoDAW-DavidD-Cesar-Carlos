DROP DATABASE IF EXISTS guardiascamp;

CREATE DATABASE guardiascamp;
USE guardiascamp;

-- CREATE USER 'guardias'@'localhost' identified by '';
-- GRANT ALL ON guardiascamp.* TO 'guardias'@'localhost';

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

DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60)
);

INSERT INTO
    roles (nombre)
VALUES
    ("direccion"),
    ("profesor");

DROP TABLE IF EXISTS profesor_roles;

CREATE TABLE profesor_roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idprofesor INT,
    idrol INT,
    FOREIGN KEY (idprofesor) REFERENCES profesor (id),
    FOREIGN KEY (idrol) REFERENCES roles (id)
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

INSERT INTO
    intervalo (horainicio, horafin)
VALUES
    ("8:15", "9:10"),
    ("9:10", "10:05"),
    ("10:05", "11:00"),
    ("11:00", "11:20"),
    ("11:20", "12:15"),
    ("12:15", "13:10"),
    ("13:10", "14:05"),
    ("14:05", "14:15"),
    ("14:15", "15:10"),
    ("15:15", "16:10"),
    ("16:10", "17:05"),
    ("17:05", "18:00"),
    ("18:20", "19:15"),
    ("19:15", "20:10"),
    ("20:10", "21:00"),
    ("21:05", "22:00");

DROP TABLE IF EXISTS dia;

CREATE TABLE dia (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    abreviacion VARCHAR(1)
);

INSERT INTO
    dia (abreviacion, nombre)
VALUES
    ("L", "Lunes"),
    ("M", "Martes"),
    ("X", "Miercoles"),
    ("J", "Jueves"),
    ("V", "Viernes");

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

INSERT INTO
    curso (nombre, abreviacion)
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
    FOREIGN KEY (idprofesor) REFERENCES profesor (id),
    FOREIGN KEY (idmateria) REFERENCES materia (id),
    FOREIGN KEY (idgrupo) REFERENCES grupo (id),
    FOREIGN KEY (idaula) REFERENCES aula (id),
    FOREIGN KEY (idintervalo) REFERENCES intervalo (id),
    FOREIGN KEY (idcurso) REFERENCES curso (id),
    FOREIGN KEY (iddia) REFERENCES dia (id)
);

DROP TABLE IF EXISTS cargo;

CREATE TABLE cargo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    abreviacion VARCHAR(60)
);

INSERT INTO
    cargo (abreviacion, nombre)
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
    FOREIGN KEY (idcargo) REFERENCES cargo (id),
    FOREIGN KEY (idguardia) REFERENCES sesion (id)
);

DROP TABLE IF EXISTS sesionfalta;

CREATE TABLE sesionfalta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idsesion INT,
    idcuadrante INT,
    incidencias TEXT,
    firma TEXT,
    deberes BOOLEAN,
    FOREIGN KEY (idsesion) REFERENCES sesion (id),
    FOREIGN KEY (idcuadrante) REFERENCES cuadrante (id)
);


INSERT INTO profesor (nombre, apellidos, numero, abreviacion, nif, contrasenya, admin, direccion, telefono, email, activo) values ("admin", "admin", 1, "adm", "12345678A", "$2y$12$5K9vWyNbeCULvkASrnrMhOlN.Lzus5A6WoKJJY4iwq4Sx9k6S5frO", 1, "admin", 123456789, "admin@gmail.com", 1);
INSERT INTO profesor_roles values (1,1,1);
