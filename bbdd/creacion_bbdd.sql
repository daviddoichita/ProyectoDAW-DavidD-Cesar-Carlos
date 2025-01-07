DROP DATABASE IF EXISTS guardiascamp;
CREATE DATABASE guardiascamp;

USE guardiascamp;

DROP TABLE IF EXISTS profesor;
CREATE TABLE profesor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(32),
    apellidos VARCHAR(32),
    nif VARCHAR(9),
    contrasenya TEXT,
    admin BOOLEAN DEFAULT FALSE,
    direccion VARCHAR(256),
    telefono VARCHAR(9),
    email VARCHAR(256)
);

DROP TABLE IF EXISTS materia;
CREATE TABLE materia (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(32)
);

DROP TABLE IF EXISTS materiaprofesor;
CREATE TABLE materiaprofesor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idmateria INT,
    idprofesor INT,
    FOREIGN KEY(idmateria) REFERENCES materia(id),
    FOREIGN KEY(idprofesor) REFERENCES profesor(id)
);

DROP TABLE IF EXISTS grupo;
CREATE TABLE grupo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idtutor INT,
    nombre VARCHAR(10),
    FOREIGN KEY(idtutor) REFERENCES profesor(id)
);

DROP TABLE IF EXISTS intervalo;
CREATE TABLE intervalo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    horainicio TIME,
    horafin TIME
);

DROP TABLE IF EXISTS sesion;
CREATE TABLE sesion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idprofesor INT,
    idgrupo INT,
    idintervalo INT,
    idmateria INT,
    aula VARCHAR(16),
    FOREIGN KEY(idprofesor) REFERENCES profesor(id),
    FOREIGN KEY(idgrupo) REFERENCES grupo(id),
    FOREIGN KEY(idintervalo) REFERENCES intervalo(id),
    FOREIGN KEY(idmateria) REFERENCES materia(id)
);

DROP TABLE IF EXISTS cargo;
CREATE TABLE cargo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(10)
);

DROP TABLE IF EXISTS curso;
CREATE TABLE curso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(10)
);

DROP TABLE IF EXISTS cuadrante;
CREATE TABLE cuadrante (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idintervalo INT,
    idcargo INT,
    idsesion INT,
    idprofesor INT,
    idcurso INT,
    incidencias TEXT,
    firma TEXT,
    deberes BOOLEAN,
    fecha DATE,
    FOREIGN KEY(idintervalo) REFERENCES intervalo(id),
    FOREIGN KEY(idcargo) REFERENCES cargo(id),
    FOREIGN KEY(idsesion) REFERENCES sesion(id),
    FOREIGN KEY(idprofesor) REFERENCES profesor(id),
    FOREIGN KEY(idcurso) REFERENCES curso(id)
);

-- CREATE USER 'guardias'@'localhost' identified with sha256_password by '';
-- GRANT ALL ON guardiascamp.* TO 'guardias'@'localhost';
