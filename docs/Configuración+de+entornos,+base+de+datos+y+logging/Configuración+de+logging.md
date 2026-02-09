## Configuración de entornos, base de datos y logging

### Configuración de logging

Esta sección describe cómo se configura el logging en los diferentes perfiles de la aplicación. Veremos las propiedades clave definidas en los ficheros `application-*.properties`, la ubicación de los archivos generados, las estrategias de rotación/recolección en producción y los ajustes habituales para diagnóstico.

#### Propiedades clave de logging

En cada perfil (`dev`, `test`, `prod`) se definen dos propiedades esenciales:

| Propiedad | Descripción | Ejemplo de valor |
| --- | --- | --- |
| **logging.file.name** | Ruta y nombre del archivo donde se vuelcan los logs | `logs/financetracker.log` |
| **logging.level.org.springframework** | Nivel de logging para los paquetes de Spring Framework | `INFO` |


```properties
# src/main/resources/application-dev.properties
logging.file.name=logs/financetracker.log
logging.level.org.springframework=INFO
```

#### 📂 Ubicación de los archivos de log

> Estas mismas líneas aparecen en `application-test.properties` y `application-prod.properties`, garantizando consistencia entre entornos.

- Los logs se escriben en el directorio `logs/` **relativo al directorio de ejecución** de la aplicación.
- Si se despliega con Docker, monte un volumen en `/app/logs` (o la ruta que elija) para persistir los ficheros.
- Por defecto, Spring Boot crea automáticamente la carpeta si no existe.

#### 🔄 Rotación y recolección en producción

Spring Boot no incluye rotación automática sólo con `logging.file.name`. Para gestionar el tamaño y número de ficheros, puede:

- Añadir un fichero `logback-spring.xml` en `src/main/resources/` con `<rollingPolicy>` y `<triggeringPolicy>`.
- Usar propiedades de Spring Boot (desde versión 2.2+):

```properties
  logging.file.max-size=10MB
  logging.file.total-size-cap=1GB
  logging.file.max-history=30
```

- Delegar la rotación a herramientas del sistema:
- `logrotate` en Linux
- Process Manager (PM2, systemd) configurado con `--max-size`
- En entornos cloud, reenviar logs a un agregador (ELK, Splunk, CloudWatch).

#### 🔧 Ajustes habituales durante troubleshooting

Para obtener más detalle en fases de diagnóstico, conviene elevar temporalmente el nivel de logging:

- **DEBUG** para clases propias:

```bash
  java -jar finance-tracker.jar \
    --logging.level.com.finance.tracker=DEBUG
```

- **TRACE** en casos extremos:

```properties
  logging.level.org.hibernate.SQL=TRACE
```

- **ERROR** o **WARN** en producción para reducir ruido.

🎯 **Consejo**: Combine niveles por paquete y use el **Spring Boot Actuator** (`/actuator/loggers`) para modificar niveles en caliente sin reiniciar.

---

```card
{
    "title": "Buenas pr\u00e1cticas",
    "content": "Mantenga separados los ficheros de log por entorno y automatice la rotaci\u00f3n/log shipping en producci\u00f3n."
}
```