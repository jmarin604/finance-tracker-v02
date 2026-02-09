# Capa de seguridad y autenticación JWT – Constantes de seguridad

La clase **CustomSecurityConstants** centraliza todas las constantes relacionadas con la seguridad y la autenticación JWT. Agrupa rutas públicas, nombres de encabezados HTTP y parámetros de token, garantizando consistencia en filtros y generación de tokens.

```java
package com.finance.tracker.security;

public class CustomSecurityConstants {
    private CustomSecurityConstants() {
        throw new IllegalStateException("Constants class");
    }

    // Rutas públicas
    public static final String LOGIN_URL          = "/api/v1/auth/login";
    public static final String ACTUATOR_URL       = "/actuator/*";
    public static final String API_DOCS           = "/v3/api-docs/**";
    public static final String SWAGGER_UI         = "/swagger-ui/**";

    // Encabezados HTTP
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX      = "Bearer ";

    // Parámetros de JWT
    public static final String ISSUER_INFO           = "https://pablito-portfolio.netlify.app";
    public static final String SUPER_SECRET_KEY      = "q7r8s9t0u1v2w3x4y5z6Q7R8S9T0U1V2W3X4Y5Z6a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v";
    public static final long   TOKEN_EXPIRATION_TIME = 864_000_000; // 10 días
}
```

---

## 🚪 Rutas públicas permitidas

Estas rutas se exponen sin requerir token JWT. Se usan en la configuración de seguridad para `permitAll()`.

| Constante | Valor | Propósito |
| --- | --- | --- |
| LOGIN_URL | `/api/v1/auth/login` | Punto de autenticación (login) |
| ACTUATOR_URL | `/actuator/*` | Endpoints de Spring Boot Actuator |
| API_DOCS | `/v3/api-docs/**` | Documentación OpenAPI (JSON) |
| SWAGGER_UI | `/swagger-ui/**` | Interfaz gráfica de Swagger UI |


---

## 🧾 Encabezados HTTP

Define cómo se envía el JWT en cada petición.

| Constante | Valor | Uso |
| --- | --- | --- |
| HEADER_AUTHORIZATION_KEY | `Authorization` | Nombre del encabezado que transporta el token |
| TOKEN_BEARER_PREFIX | `Bearer ` | Prefijo que antecede el token en el header |


---

## 🔑 Parámetros del token JWT

Controlan la emisión y validación del JWT en filtros y servicios.

| Constante | Valor | Descripción |
| --- | --- | --- |
| ISSUER_INFO | `https://pablito-portfolio.netlify.app` | Emisor del JWT (`iss` claim) |
| SUPER_SECRET_KEY | Cadena Base64 larga | Clave secreta para firma/verificación (HMAC SHA) |
| TOKEN_EXPIRATION_TIME | `864_000_000` | Tiempo de vida del token en milisegundos (10 días) |


---

## 🔄 Uso en otros componentes

- **CustomWebSecurity**
- Permite las rutas públicas (`LOGIN_URL`, `ACTUATOR_URL`, `API_DOCS`) en el método `authorizeHttpRequests()` de Spring Security.
- **CustomJWTAuthorizationFilter**
- Lee el encabezado `Authorization`, comprueba el prefijo `Bearer `, decodifica y valida el token con `SUPER_SECRET_KEY`.
- **AuthServiceImpl**
- Genera el JWT tras verificar credenciales: utiliza `ISSUER_INFO`, firma con `SUPER_SECRET_KEY` y fija la expiración con `TOKEN_EXPIRATION_TIME`.

---

## ⚙️ Buenas prácticas

- **Externalizar el secreto**: mover `SUPER_SECRET_KEY` a variables de entorno o vault.
- **Rotación periódica**: cambiar la clave secreta y expirar tokens antiguos.
- **Tiempo de expiración razonable**: balancear usabilidad y seguridad al definir `TOKEN_EXPIRATION_TIME`.
- **HTTPS obligatorio**: siempre servir la API bajo SSL/TLS para proteger las cabeceras.

---

```card
{
    "title": "Aviso de seguridad",
    "content": "Nunca incluyas secretos planos en el repositorio. Usa gesti\u00f3n de secretos centralizada."
}
```