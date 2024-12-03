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
    FOREIGN KEY(idmateria) REFERENCES materia(id)
);

DROP TABLE IF EXISTS grupo;
CREATE TABLE grupo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(10),
    idtutor INT,
    FOREIGN KEY(idtutor) REFERENCES profesor(id)
);

DROP TABLE IF EXISTS intervalo;
CREATE TABLE intervalo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    horainicio TIME,
    horafin TIME
);

DROP TABLE IF EXISTS sesion;
CREATE TABLE sesion (
    id INT AUTO_INCREMENT PRIMARY KEY,
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
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(10)
);

DROP TABLE IF EXISTS curso;
CREATE TABLE curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(10)
);

DROP TABLE IF EXISTS cuadrante;
CREATE TABLE cuadrante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idintervalo INT,
    idcargo INT,
    idcurso INT,
    idprofesor INT,
    idsesion INT,
    incidencias TEXT,
    firma TEXT,
    deberes TEXT,
    fecha DATE,
    FOREIGN KEY(idintervalo) REFERENCES intervalo(id),
    FOREIGN KEY(idcargo) REFERENCES cargo(id),
    FOREIGN KEY(idcurso) REFERENCES curso(id),
    FOREIGN KEY(idprofesor) REFERENCES profesor(id),
    FOREIGN KEY(idsesion) REFERENCES sesion(id)
);
