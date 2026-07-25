# Build context must be the "microservices" directory (parent reactor root),
# e.g.: docker build -f config-server/Dockerfile .
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY config-server ./config-server

WORKDIR /workspace/config-server
RUN chmod +x mvnw \
    && ./mvnw -q -B -DskipTests dependency:go-offline \
    && ./mvnw -q -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app
COPY --from=build /workspace/config-server/target/*.jar app.jar

EXPOSE 8888
HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=6 \
    CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
