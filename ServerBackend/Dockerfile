FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

# Copy only the necessary files for the Maven build
COPY pom.xml .
COPY src ./src

# Build the project with Maven and create the executable JAR
RUN mvn clean package

# Set the location of the executable JAR
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
