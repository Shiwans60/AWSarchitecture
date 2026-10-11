
# Stage 1: Build the application using Maven and JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the Maven configuration and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and package the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application using a lightweight JRE
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Change this port for each service
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
