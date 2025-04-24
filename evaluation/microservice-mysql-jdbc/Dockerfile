FROM openjdk:21-jdk-slim

COPY target/microservice-mysql-jdbc-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]