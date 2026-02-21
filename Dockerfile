# docker build -t blk-hacking-ind-pradhuman .

# Base image: Using Eclipse Temurin (OpenJDK) on Alpine Linux
# Selection Criteria:
# - Alpine Linux: Minimal, secure, and lightweight distribution (~5MB base)
# - Reduced attack surface with fewer packages and vulnerabilities
# - Optimized for containerized applications with smaller image size
# - Eclipse Temurin: Official OpenJDK builds, enterprise-grade quality
# - Java 21: Required by the Spring Boot 4.0.3 application (as per pom.xml)
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the Maven build artifact
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 5477 as required
EXPOSE 5477

# Create non-root user for security best practices
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

