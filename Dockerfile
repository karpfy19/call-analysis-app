# Use Maven + Java 17 (Maven is included)
FROM maven:3.9.13-eclipse-temurin-17-alpine

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Build the Spring Boot app, skipping tests
RUN mvn clean package -DskipTests

# Expose Spring Boot default port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/call-analysis-app-0.0.1-SNAPSHOT.jar"]