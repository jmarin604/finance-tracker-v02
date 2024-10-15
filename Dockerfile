# Usar la imagen base de OpenJDK con JDK 21
FROM openjdk:21-jdk-slim

# Crear un directorio para la aplicación
WORKDIR /app

# Exponer el puerto de la aplicación
EXPOSE 8080

# Copiar el archivo JAR al contenedor
COPY target/finance-tracker-0.0.1-SNAPSHOT.jar /app/finance-tracker.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/finance-tracker.jar"]
