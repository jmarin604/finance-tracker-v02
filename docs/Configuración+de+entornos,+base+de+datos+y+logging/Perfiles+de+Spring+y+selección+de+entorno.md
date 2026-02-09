# Configuración de entornos, base de datos y logging – Perfiles de Spring y selección de entorno

Esta sección describe cómo Spring Boot gestiona distintos entornos mediante perfiles. Se documenta el punto de entrada de configuración `application.properties`, la separación en archivos específicos para **dev**, **prod** y **test**, y las formas de cambiar el perfil activo.

## Spring Profiles en Spring Boot

Spring Profiles permiten agrupar propiedades de configuración según el entorno (desarrollo, producción, pruebas).  
- Facilitan cambiar conexiones de base de datos, niveles de logging y otras opciones.  
- Cada archivo `application-<perfil>.properties` sobrescribe o amplía las propiedades comunes.

## Archivo base: application.properties

Este fichero define la configuración común y el perfil por defecto.  

```properties
# Información general
spring.application.name=finance-tracker

# Perfil activo por defecto
spring.profiles.active=dev
```  
- **spring.profiles.active**: perfil que Spring carga si no se especifica otro.  
- Aquí, el modo **dev** se activa automáticamente en cada arranque .

## Configuraciones por perfil

A continuación se muestran las propiedades clave de cada entorno.

| Perfil | Archivo                         | Base de datos                           | Logging                      |
|:------:|:--------------------------------|:----------------------------------------|:-----------------------------|
| 💻 dev  | `application-dev.properties`    | URL remota Aiven:<br>`jdbc:postgresql://pg.../finance_tracker`<br>Usuario: `avnadmin`<br>Contraseña: `AVNS_BmD3…` | Fichero: `logs/financetracker.log`<br>Nivel: `INFO` |
| 🚀 prod | `application-prod.properties`   | URL local:<br>`jdbc:postgresql://localhost:5432/finance_tracker`<br>Usuario: `postgres`<br>Contraseña: `DeskTop2281588` | Fichero: `logs/financetracker.log`<br>Nivel: `INFO` |
| 🧪 test | `application-test.properties`   | Mismos datos que producción (local)     | Mismos ajustes que prod.     |

### Desglose de application-dev.properties

```properties
# Spring DataSource Basic Config
spring.datasource.url=jdbc:postgresql://pg-34e1e67b-juandavidmarin-2b6b.h.aivencloud.com:21506/finance_tracker
spring.datasource.username=avnadmin
spring.datasource.password=AVNS_BmD3-uGWeOpfxpxn761
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Servidor
server.port=8080

# Logging
logging.file.name=logs/financetracker.log
logging.level.org.springframework=INFO
```  
Este perfil habilita la conexión a un **host remoto** de Aiven y muestra las consultas SQL en consola .

### Desglose de application-prod.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=DeskTop2281588
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080

logging.file.name=logs/financetracker.log
logging.level.org.springframework=INFO
```  
Se conecta a una base de datos local y mantiene un nivel de logging estable para producción .

### Desglose de application-test.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=DeskTop2281588
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080

logging.file.name=logs/financetracker.log
logging.level.org.springframework=INFO
```  
Orientado a pruebas de integración, comparte la configuración de prod pero aislado en un entorno de test .

## Selección dinámica del perfil activo

Puedes cambiar el perfil sin modificar el código ni el `application.properties`.

### 1. Variable de entorno 🌐

Establece `SPRING_PROFILES_ACTIVE` antes de iniciar la aplicación:

```bash
export SPRING_PROFILES_ACTIVE=prod
java -jar finance-tracker.jar
```

### 2. Línea de comandos ⚙️

Usa el parámetro `--spring.profiles.active`:

```bash
java -jar finance-tracker.jar --spring.profiles.active=test
```

## Separación de responsabilidades

- `application.properties`: propiedades comunes y perfil por defecto.
- `application-<perfil>.properties`: agrega o sobreescribe propiedades específicas.
- Spring Boot fusiona ambos conjuntos al arranque, priorizando el perfil activo.

---

> **Buen hábito**: No incluyas credenciales en el repositorio. Utiliza vaults o variables de entorno para datos sensibles.