FROM mysql:8.0

ENV MYSQL_DATABASE=guardiascamp
ENV MYSQL_USER=guardias
ENV MYSQL_ROOT_PASSWORD=IesCampMorvedre01%
ENV MYSQL_PASSWORD=IesCampMorvedre01%

COPY ./creacion_bbdd.sql /docker-entrypoint-initdb.d/

EXPOSE 3307
