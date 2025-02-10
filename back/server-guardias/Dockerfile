FROM amazoncorretto:21.0.4-alpine3.18

WORKDIR /app

COPY . .

EXPOSE 8000

RUN ./mvnw clean install

ENTRYPOINT ["./mvnw", "spring-boot:run"]
