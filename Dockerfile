# Multi-stage build for optimal image size
FROM maven:3.9.11-eclipse-temurin-25-alpine AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - Eclipse Temurin JRE
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]