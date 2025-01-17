-- CREATE USER 'guardias'@'localhost' identified with sha256_password by '';
-- GRANT ALL ON guardiascamp.* TO 'guardias'@'localhost';

DROP DATABASE IF EXISTS guardiascamp;
CREATE DATABASE guardiascamp;

USE guardiascamp;

DROP TABLE IF EXISTS profesor;
CREATE TABLE profesor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(32),
    apellidos VARCHAR(32),
    numero INT,
    abreviacion varchar(20),
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
    abreviacion VARCHAR(20),
    nombre VARCHAR(32)
);

DROP TABLE IF EXISTS intervalo;
CREATE TABLE intervalo (
	id INT PRIMARY KEY AUTO_INCREMENT,
    horainicio time,
    horafin time
);

DROP TABLE IF EXISTS dia;
CREATE TABLE dia (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(20),
    abreviacion VARCHAR(1)
);

DROP TABLE IF EXISTS grupo;
CREATE TABLE grupo (
	id INT PRIMARY KEY AUTO_INCREMENT,
	numero INT,
    abreviacion VARCHAR(20),
    nombre VARCHAR(60),
    curso VARCHAR(20)
);

DROP TABLE IF EXISTS curso;
CREATE TABLE curso (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(32),
    abreviacion VARCHAR(20)
);

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
    nombre VARCHAR(32),
    abreviacion VARCHAR(20)
);

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
