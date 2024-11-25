FROM openjdk:17-alpine

WORKDIR /app

COPY target/pi_fisio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]