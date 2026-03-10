# Use Maven + Java 17 (Maven included)
FROM maven:3.9.13-eclipse-temurin-17-alpine

# Set working directory inside container
WORKDIR /app

# Copy all project files
COPY . .

# Build the Spring Boot app, skipping tests
RUN mvn clean package -DskipTests

# Expose Spring Boot default port
EXPOSE 8080

# Run the app (use wildcard in case jar name changes)
CMD ["sh", "-c", "java -jar target/*.jar"]