# ProyectoDAW

Proyecto intermodular Carlos Ripolles, Cesar Torres, David Doichita

# Instrucciones base de datos (MySQL)
Crear usuario guardias con la contraseña que quieras

# Instrucciones backend
### Paso 1 (HTTPS)
[En la carpeta keystore dentro de resources del backend](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/tree/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/src/main/resources/keystore) hay un [README.md](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/src/main/resources/keystore/README.md).
- Copiar el comando de este en cualquier terminal (la terminal debe estar dentro de esta carpeta) siempre y cuando tengas Java 21 y [JAVA_HOME configurado](https://www.google.com/search?q=JAVA_HOME+windows&sca_esv=ec9709b1ed79343c&sxsrf=AHTn8zpXYf8ll94jH4qTWr0vWQM5DTfr2g%3A1743875191677&ei=d2zxZ_CDKdTaxc8PxP6jwAI&ved=0ahUKEwiwm9SqucGMAxVUbfEDHUT_CCgQ4dUDCBA&uact=5&oq=JAVA_HOME+windows&gs_lp=Egxnd3Mtd2l6LXNlcnAiEUpBVkFfSE9NRSB3aW5kb3dzMgoQABiABBhDGIoFMgUQABiABDIIEAAYgAQYywEyCBAAGBYYChgeMgYQABgWGB4yBhAAGBYYHjIGEAAYFhgeMgYQABgWGB4yBhAAGBYYHjIGEAAYFhgeSNQcUNMIWOAbcAN4AZABAJgBaKABkQyqAQQxMy40uAEDyAEA-AEBmAIUoALuDMICChAAGLADGNYEGEfCAg4QLhiABBixAxiDARiKBcICCxAuGIAEGNEDGMcBwgIFEC4YgATCAgoQLhiABBhDGIoFwgIQEAAYgAQYsQMYQxiDARiKBcICCxAAGIAEGLEDGIMBwgIKEAAYgAQYFBiHApgDAIgGAZAGCJIHBDE1LjWgB96UAbIHBDEyLjW4B-EM&sclient=gws-wiz-serp) (En los demas SO se configura solo)
- Rellenar los campos que te pide o darle a enter hasta que te pida confirmacion de si los datos son correctos

### Paso 2 (Configuracion secretos)
[En la carpeta del backend](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/tree/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias) hay un archivo [.env.example](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/.env.example)
- Copiar como .env
- Rellenar [APP_SECRET](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/.env.example#L1) con la salida del comando (en Windows debe ser ejecutado en una git bash)

  ```bash
  openssl rand -base64 32
  ```
  
- Rellenar [DB_PASSWD](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/.env.example#L2) con la contraseña del usuario guardias de tu base de datos
- Rellenar [KEYSTORE_PASSWD](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias/.env.example#L3C1-L3C16) con la contraseña del keystore creado en el paso anterior

### Paso 3 (Ejecucion)
Ejecutar en la carpeta del [backend](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/tree/db7eef58202ae2ce5a6bc2257b56e678516f450e/back/server-guardias)

```bash
mvn spring-boot:run
```
O lanzar desde el IDE

# Instrucciones frontend
### Paso 1 (Dependencias)
Instalar las dependencias
```bash
npm i
```

### Paso 2 (HTTPS)
[En la carpeta keystore dentro de la carpeta del frontend](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/tree/db7eef58202ae2ce5a6bc2257b56e678516f450e/front/guardias-web/keystore) hay una archivo [README.md](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/blob/db7eef58202ae2ce5a6bc2257b56e678516f450e/front/guardias-web/keystore/README.md)
- En una terminal (esta vez debe ser git bash) dentro de esta carpeta ejecutar los comandos que aparecen por orden

### Paso 3 (Ejecucion)
Ejecutar en la carpeta del [frontend](https://github.com/daviddoichita/ProyectoDAW-DavidD-Cesar-Carlos/tree/db7eef58202ae2ce5a6bc2257b56e678516f450e/front/guardias-web)
```bash
ng s --ssl
```
# Instrucciones para probar
Debido a que el certificado HTTPS es autofirmado los navegadores no lo aceptan solos
- Entrar primero a https://localhost:8000/api/intervalos (por ejemplo) y aceptar el certificado
- Despues de esto, ya puedes entrar al [front](https://localhost:4200/), aceptar el certificado e iniciar sesion como admin
  
  | **Email** | **Contraseña** |
  | ----------|----------------|
  | admin     | admin1         |

# Mas
### Sentencias insercion faltas manualmente

Seleccionar cudrantes que se vayan a ver (cambiar iddia por el dia de hoy e idintervalo por el intervalo horario actual)
| **Dia**   | **id** |   | **Intervalo** | **id** |
|-----------|--------|---|---------------|--------|
| Lunes     | 1      |   | Primera hora  | 1      |
| Martes    | 2      |   | Segunda hora  | 2      |
| Miercoles | 3      |   | Tercera hora  | 3      |
| ...       | ...    |   | ...           | ...    |
```sql
select * from cuadrante where idguardia = any (select id from sesion
where iddia = 1 and idintervalo = 1) and fecha = CURDATE();
```
Cambiar los datos por los recogidos previamente (solo id del cuadrante) y una sesion cualquiera
```sql
insert into sesionfalta (idsesion, idcuadrante) values (20, 6385);
```
