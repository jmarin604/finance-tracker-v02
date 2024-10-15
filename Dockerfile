# Cambiar a la imagen base de Java 
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Exponer el puerto
EXPOSE 8080

# Copiar el archivo JAR generado al contenedor
COPY target/finance-tracker-0.0.1-SNAPSHOT.jar /app/finance-tracker.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/finance-tracker.jar"]