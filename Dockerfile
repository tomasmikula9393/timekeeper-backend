# První fáze: Build aplikace pomocí Maven
FROM maven:3.8.8-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# Druhá fáze: Spuštění aplikace pomocí JDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/TimeKeeper-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]