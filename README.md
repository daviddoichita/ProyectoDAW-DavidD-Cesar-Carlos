# ProyectoDAW

Proyecto intermodular Carlos Ripolles, Cesar Torres, David Doichita

select * from cuadrante where idguardia = any (select id from sesion
where iddia = 1 and idintervalo = 1) and fecha = CURDATE();
