FROM maven:3-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY microservice-mysql-springdatajpa/pom.xml microservice/pom.xml
COPY microservice-mysql-springdatajpa/src microservice/src

COPY common/pom.xml common/pom.xml
COPY common/src common/src

RUN mvn -f common/pom.xml clean install -DskipTests dependency:go-offline
RUN mvn -f microservice/pom.xml clean package -DskipTests dependency:go-offline

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

COPY --from=build /workspace/microservice/target/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xms512m", "-Xmx12g", "-jar", "app.jar"]