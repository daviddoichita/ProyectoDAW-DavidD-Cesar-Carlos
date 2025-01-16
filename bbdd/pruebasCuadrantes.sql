insert into profesor
values
(1, "david", "doichita", "1234567A", "david", 1, "Calle", "123456789", "david@gmail.com", 1),
(2, "carlos", "ripolles", "8765432B", "carlos", 1, "Avenida", "987654321", "carlos@gmail.com", 1),
(3, "cesar", "torres", "87612345C", "cesar", 1, "Calle", "987123456", "cesar@gmail.com", 1);

insert into materia
values
(1, "matematicas"),
(2, "base datos"),
(3, "tecnologia");

insert into materiaprofesor
values
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

insert into grupo
values
(1, 1, "1A"),
(2, 2, "2A"),
(3, 3, "3A");

insert into intervalo
values
(1, "08:20:00", "09:15:00"),
(2, "09:15:00", "10:10:00"),
(3, "10:10:00", "11:00:00"),
(4, "11:25:00", "12:20:00"),
(5, "12:20:00", "13:10:00"),
(6, "13:10:00", "14:00:00"),
(7, "14:10:00", "15:00:00"),
(8, "15:15:00", "16:05:00"),
(9, "16:05:00", "17:00:00"),
(10, "17:00:00", "17:50:00"),
(11, "18:10:00", "19:00:00"),
(12, "19:00:00", "19:50:00"),
(13, "19:50:00", "20:40:00"),
(14, "20:50:00", "21:40:00");

insert into dia
values
(1, "lunes"),
(2, "martes"),
(3, "miercoles"),
(4, "jueves"),
(5, "viernes");

insert into aula
values
(1, "A1"),
(2, "A2"),
(3, "A3");

insert into cargo
values
(1, "coor"),
(2, "it1"),
(3, "conviv");

insert into curso
values
(1, "24/25");

insert into sesion 
    (idprofesor, idgrupo, idintervalo, idmateria, iddia, idaula)
values 
  -- P  G  I  M  D  A 
    (1, 1, 1, 1, 1, 1),
    (2, 2, 1, 2, 1, 2),
    (3, 3, 1, 3, 1, 3),

    (1, NULL, 2, NULL, 1, NULL),
    (2, NULL, 2, NULL, 1, NULL),
    (3, NULL, 2, NULL, 1, NULL);

