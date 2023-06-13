FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /workspace/app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
