# Použijeme základní JDK obraz
FROM openjdk:17-jdk-slim

# Nastavíme pracovní adresář v kontejneru
WORKDIR /app

# Zkopírujeme aplikaci do obrazu
COPY target/TimeKeeper-1.0-SNAPSHOT.jar app.jar

# Nastavíme příkaz pro spuštění aplikace
ENTRYPOINT ["java", "-jar", "app.jar"]