DROP DATABASE IF EXISTS guardias;
CREATE DATABASE guardias;

USE guardias;

DROP TABLE IF EXISTS profesor;
CREATE TABLE profesor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(32),
    apellidos VARCHAR(32),
    nif VARCHAR(9),
    contrasenya TEXT,
    admin BOOLEAN,
    direccion VARCHAR(256),
    telefono VARCHAR(9),
    email VARCHAR(256)
);

DROP TABLE IF EXISTS materia;
CREATE TABLE materia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(32)
);

DROP TABLE IF EXISTS materiaprofesor;
CREATE TABLE materiaprofesor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idprofesor INT,
    idmateria INT,
    FOREIGN KEY(idprofesor) REFERENCES profesor(id),
);
