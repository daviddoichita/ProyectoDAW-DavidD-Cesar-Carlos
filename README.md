# ProyectoDAW

Proyecto intermodular Carlos Ripolles, Cesar Torres, David Doichita

select * from cuadrante where idguardia = any (select id from sesion
where iddia = 1 and idintervalo = 1) and fecha = CURDATE();

insert into sesionfalta (idsesion, idcuadrante) values (20, 6385), (21, 6392), (22, 6399);
