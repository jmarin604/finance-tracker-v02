# Finance Tracker Application

Este proyecto sigue una **arquitectura por capas (Layered Architecture)**, diseñada para mejorar la organización, modularidad y mantenibilidad del código. A continuación, se describe cada capa y su responsabilidad dentro de la aplicación.

## Estructura del Proyecto

El proyecto está organizado en las siguientes capas principales:

### 1. Capa de Controlador (`controller`)
Esta capa se encarga de manejar las peticiones HTTP (GET, POST, PUT, DELETE) que llegan a la aplicación. Define los puntos de entrada o endpoints para que los clientes interactúen con los servicios de la aplicación.

### 2. Capa de Dominio (`domain`)
Contiene las clases que representan las entidades del negocio. Estas entidades son modelos que mapean las tablas en la base de datos y encapsulan la lógica relacionada con los datos del dominio.

### 3. Capa de DTO (`dto`)
Esta capa contiene los Data Transfer Objects, que son objetos simples diseñados para transferir datos entre las capas del sistema. Los DTO ayudan a proteger las entidades del dominio evitando exponerlas directamente.

### 4. Capa de Excepciones (`exception`)
Maneja las excepciones personalizadas y el control de errores de manera centralizada, mejorando la robustez de la aplicación.

### 5. Capa de Repositorio (`repository`)
Esta capa es responsable de interactuar con la base de datos. Utiliza los patrones de repositorio y DAO (Data Access Object) para gestionar la persistencia y recuperación de datos. Es común usar frameworks como JPA o Hibernate aquí.

### 6. Capa de Seguridad (`security`)
Se encarga de la autenticación y autorización dentro de la aplicación, garantizando que solo los usuarios autorizados puedan acceder a los recursos.

### 7. Capa de Servicio (`service`)
Esta capa contiene la lógica de negocio de la aplicación. Los servicios coordinan la interacción entre los controladores y los repositorios, ejecutando la lógica requerida para cumplir con los casos de uso.

### 8. Capa de Utilidades (`utility`)
Contiene clases auxiliares que proporcionan funcionalidades compartidas entre las demás capas, como validaciones, conversiones y otros métodos comunes.

## Estructura de Carpetas

```bash
src/
│
└─── main/
     └─── java/
          └─── com/finance/tracker/
               └─── controller/
               └─── domain/
               └─── dto/
               └─── exception/
               └─── repository/
               └─── security/
               └─── service/
               └─── utility/
     └─── resources/
└─── test/
     └─── java/
          └─── com/finance/tracker/
