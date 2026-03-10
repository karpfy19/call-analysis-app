# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Make Maven wrapper executable (important for Linux containers)
RUN chmod +x mvnw

# Build the Spring Boot app, skipping tests
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot default port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/call-analysis-app-0.0.1-SNAPSHOT.jar"]