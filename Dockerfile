# Dockerfile for Order Service
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/delivery_Order_Service-0.0.1-SNAPSHOT.jar app.jar

# Expose the service's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
