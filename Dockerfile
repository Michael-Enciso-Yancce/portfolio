# Build Stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create uploads directory for persistent storage
RUN mkdir -p uploads && chown -R 1000:1000 uploads

# Render usually uses PORT environment variable
EXPOSE 8080

# Production-ready entrypoint
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
