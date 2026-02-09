## Configuración de base de datos PostgreSQL

En esta sección se detallan las propiedades de conexión a PostgreSQL para cada perfil de Spring Boot (dev, prod y test). Cada perfil define su propio **datasource** en `src/main/resources`, garantizando aislamiento de entornos y facilitando despliegues consistentes.

### Perfiles y propósito  
- **dev**: conexión a instancia gestionada en Aiven.  
- **prod**: conexión a instancia local en `localhost:5432/finance_tracker`.  
- **test**: igual a prod, pero pensada para pruebas locales.  

---

### Tabla comparativa de configuración  

| Perfil  | Archivo                          | URL                                                                  | Usuario    | Contraseña                    | Driver                          |
|---------|----------------------------------|----------------------------------------------------------------------|------------|-------------------------------|---------------------------------|
| dev 🛠️  | application-dev.properties       | `jdbc:postgresql://pg-34e1e67b-juandavidmarin-2b6b.h.aivencloud.com:21506/finance_tracker` | `avnadmin` | `AVNS_BmD3-uGWeOpfxpxn761`    | `org.postgresql.Driver`         |
| prod ✅ | application-prod.properties      | `jdbc:postgresql://localhost:5432/finance_tracker`                   | `postgres` | `DeskTop2281588`              | `org.postgresql.Driver`         |
| test 🧪 | application-test.properties      | `jdbc:postgresql://localhost:5432/finance_tracker`                   | `postgres` | `DeskTop2281588`              | `org.postgresql.Driver`         |

---

### Propiedades de conexión comunes  
- **spring.datasource.url**: cadena JDBC con host, puerto y nombre de la base de datos.  
- **spring.datasource.username**: usuario de PostgreSQL.  
- **spring.datasource.password**: contraseña asociada al usuario.  
- **spring.datasource.driver-class-name**: clase del driver JDBC (`org.postgresql.Driver`).  
- **spring.jpa.hibernate.ddl-auto**: estrategia DDL (ej. `update`).  
- **spring.jpa.properties.hibernate.dialect**: dialecto Hibernate para PostgreSQL.

---

### Configuración por perfil

#### Dev 🛠️  
```properties
# src/main/resources/application-dev.properties
spring.datasource.url=jdbc:postgresql://pg-34e1e67b-juandavidmarin-2b6b.h.aivencloud.com:21506/finance_tracker
spring.datasource.username=avnadmin
spring.datasource.password=AVNS_BmD3-uGWeOpfxpxn761
spring.datasource.driver-class-name=org.postgresql.Driver
```


#### Prod ✅  
```properties
# src/main/resources/application-prod.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=DeskTop2281588
spring.datasource.driver-class-name=org.postgresql.Driver
```


#### Test 🧪  
```properties
# src/main/resources/application-test.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=DeskTop2281588
spring.datasource.driver-class-name=org.postgresql.Driver
```


---

### Importancia de una configuración correcta  
- Una URL o credenciales erróneas bloquean el arranque de la aplicación.  
- Evitar exponer datos sensibles en el repositorio.  

```card
{
  "title": "Mejor Práctica",
  "content": "Almacenar credenciales en variables de entorno o sistemas de vault para producción."
}
```

> ⚠️ **Antes de desplegar**, asegúrate de que la URL, usuario y contraseña están correctamente apuntando al entorno deseado.