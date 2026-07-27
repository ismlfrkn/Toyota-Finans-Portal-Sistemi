# Build context must be the "microservices" directory (parent reactor root),
# e.g.: docker build -f bff-server/Dockerfile .
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY bff-server ./bff-server

WORKDIR /workspace/bff-server
RUN chmod +x mvnw \
    && ./mvnw -q -B -DskipTests dependency:go-offline \
    && ./mvnw -q -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app
COPY --from=build /workspace/bff-server/target/*.jar app.jar

EXPOSE 9000
HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=6 \
    CMD curl -f http://localhost:9000/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
