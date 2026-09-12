# Etapa 1: Imagen base ligera de ejecución JRE sobre Alpine
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el archivo JAR generado por el build de Maven
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
