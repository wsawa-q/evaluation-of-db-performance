FROM openjdk:21-jdk-slim

COPY target/microservice-mysql-springdatajpa-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]