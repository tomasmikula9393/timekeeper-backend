# 1) Build stage – Maven + Temurin JDK 17
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2) Runtime stage – Temurin JRE 17 (lehčí, rychlejší)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/TimeKeeper-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
