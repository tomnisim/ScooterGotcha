FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

# Copy only the necessary files for the Maven build
COPY pom.xml .
COPY src ./src

# Build the project with Maven
RUN mvn clean install

# Copy the built JAR file to the container
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
