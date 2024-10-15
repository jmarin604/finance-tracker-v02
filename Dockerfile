# Dockerfile básico
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Exponer el puerto
EXPOSE 8080

# Configurar variables de entorno para tu aplicación (si es necesario)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://pg-34e1e67b-juandavidmarin-2b6b.h.aivencloud.com:21506/finance_tracker
ENV SPRING_DATASOURCE_USERNAME=avnadmin
ENV SPRING_DATASOURCE_PASSWORD=AVNS_BmD3-uGWeOpfxpxn761

# Copiar el archivo JAR generado al contenedor
COPY target/finance-tracker-0.0.1-SNAPSHOT.jar /app/finance-tracker.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/finance-tracker.jar"]
