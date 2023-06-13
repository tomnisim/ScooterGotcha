FROM adoptopenjdk:17-jdk-hotspot

# Set Java version
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

# Copy only the necessary files for the Maven build
COPY pom.xml .
COPY src ./src

# Build the project with Maven
RUN mvn clean install

# Copy the built JAR file to the container
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
