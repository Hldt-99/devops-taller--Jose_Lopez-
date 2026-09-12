# Etapa de ejecución ultra-ligera
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar únicamente el archivo JAR empaquetado
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
