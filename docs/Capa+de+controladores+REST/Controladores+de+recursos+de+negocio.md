# Capa de controladores REST – Controladores de recursos de negocio

La **Capa de Controladores REST** expone los endpoints que permiten al cliente interactuar con los recursos de la aplicación. Cada controlador maneja operaciones CRUD sobre un recurso específico, valida la entrada mediante DTOs y orquesta la llamada a la **Capa de Servicio**. Además, extrae el usuario autenticado del contexto de seguridad usando `Utilities.getAuthenticatedUser()` y delega la lógica de negocio correspondiente.

---

## CategoryRestController 📂

Este controlador gestiona las operaciones sobre las **categorías** de transacción.  
Se asocia a la ruta base `/api/v1/category` y utiliza `CategoryDTO` como modelo de entrada/salida. 

| Método | Ruta                        | Descripción                       |
| ------ | --------------------------- | --------------------------------- |
| POST   | /api/v1/category/create     | Crea una nueva categoría          |
| PUT    | /api/v1/category/update     | Actualiza una categoría existente |
| DELETE | /api/v1/category/{categoryId} | Elimina una categoría por su ID   |

**Flujo**  
- Obtiene el **DTO** del cuerpo de la petición.  
- Recupera el usuario autenticado.  
- Llama a `categoryService` para realizar la operación.  
- Responde con **HTTP 200 OK** si todo va bien; lanza `NotFoundException` (HTTP 404) si no se encuentra el recurso.

### POST /api/v1/category/create

```api
{
  "title": "Crear Categoría",
  "description": "Crea una nueva categoría de gasto o ingreso",
  "method": "POST",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/category/create",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"name\": \"Transporte\",\n  \"description\": \"Gastos de viaje\"\n}",
  "responses": {
    "200": { "description": "Categoría creada con éxito", "body": "" },
    "404": { "description": "Usuario o datos inválidos", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"...\"\n}" }
  }
}
```

### PUT /api/v1/category/update

```api
{
  "title": "Actualizar Categoría",
  "description": "Modifica una categoría existente",
  "method": "PUT",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/category/update",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"catId\": 5,\n  \"name\": \"Alimentación\",\n  \"description\": \"Compras de comida\"\n}",
  "responses": {
    "200": { "description": "Categoría actualizada", "body": "" },
    "404": { "description": "Categoría no encontrada", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"Category not found\"\n}" }
  }
}
```

### DELETE /api/v1/category/{categoryId}

```api
{
  "title": "Eliminar Categoría",
  "description": "Deshabilita una categoría por su ID",
  "method": "DELETE",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/category/{categoryId}",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true }
  ],
  "pathParams": [
    { "key": "categoryId", "value": "ID de la categoría", "required": true }
  ],
  "bodyType": "none",
  "responses": {
    "200": { "description": "Categoría eliminada", "body": "" },
    "404": { "description": "Categoría no encontrada", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"Category not found\"\n}" }
  }
}
```

---

## TransactionRestController 💰

Maneja las **transacciones** del usuario sobre la ruta base `/api/v1/transaction`.  
Emplea `TransactionDTO` para recibir datos como monto, fecha, IDs de categoría, tipo y usuario. 

| Método | Ruta                             | Descripción                         |
| ------ | -------------------------------- | ----------------------------------- |
| POST   | /api/v1/transaction/create       | Registra una nueva transacción      |
| PUT    | /api/v1/transaction/update       | Actualiza una transacción existente |
| DELETE | /api/v1/transaction/{transactionId} | Elimina (deshabilita) una transacción |

### POST /api/v1/transaction/create

```api
{
  "title": "Crear Transacción",
  "description": "Registra una nueva transacción financiera",
  "method": "POST",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transaction/create",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"amount\": \"150.00\",\n  \"transactionDate\": \"2026-02-01T14:30:00\",\n  \"category\": 3,\n  \"transactionType\": 2,\n  \"user\": 1\n}",
  "responses": {
    "200": { "description": "Transacción creada", "body": "" },
    "404": { "description": "Recurso no encontrado", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"...\"\n}" }
  }
}
```

### PUT /api/v1/transaction/update

```api
{
  "title": "Actualizar Transacción",
  "description": "Modifica los datos de una transacción existente",
  "method": "PUT",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transaction/update",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"transId\": 10,\n  \"amount\": \"175.50\",\n  \"transactionDate\": \"2026-02-02T10:00:00\",\n  \"category\": 4,\n  \"transactionType\": 1,\n  \"user\": 1\n}",
  "responses": {
    "200": { "description": "Transacción actualizada", "body": "" },
    "404": { "description": "Transacción no encontrada", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"Transaction not found\"\n}" }
  }
}
```

### DELETE /api/v1/transaction/{transactionId}

```api
{
  "title": "Eliminar Transacción",
  "description": "Deshabilita una transacción por su ID",
  "method": "DELETE",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transaction/{transactionId}",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true }
  ],
  "pathParams": [
    { "key": "transactionId", "value": "ID de la transacción", "required": true }
  ],
  "bodyType": "none",
  "responses": {
    "200": { "description": "Transacción eliminada", "body": "" },
    "404": { "description": "Transacción no encontrada", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"Transaction not found\"\n}" }
  }
}
```

---

## TransactionTypeRestController 🔄

Controla los **tipos de transacción** en `/api/v1/transactionType`, usando `TransactionTypeDTO`. 

| Método | Ruta                                    | Descripción                              |
| ------ | --------------------------------------- | ---------------------------------------- |
| POST   | /api/v1/transactionType/create          | Crea un nuevo tipo de transacción        |
| PUT    | /api/v1/transactionType/update          | Actualiza un tipo de transacción existente |
| DELETE | /api/v1/transactionType/{transactionTypeId} | Deshabilita un tipo de transacción por ID |

### POST /api/v1/transactionType/create

```api
{
  "title": "Crear Tipo de Transacción",
  "description": "Registra un nuevo tipo (ej. ingreso, gasto)",
  "method": "POST",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transactionType/create",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"name\": \"Ingreso\",\n  \"description\": \"Dinero recibido\"\n}",
  "responses": {
    "200": { "description": "Tipo creado", "body": "" },
    "404": { "description": "Error de validación", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"TransactionType is null\"\n}" }
  }
}
```

### PUT /api/v1/transactionType/update

```api
{
  "title": "Actualizar Tipo de Transacción",
  "description": "Modifica un tipo existente",
  "method": "PUT",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transactionType/update",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"transTypId\": 2,\n  \"name\": \"Gasto\",\n  \"description\": \"Dinero gastado\"\n}",
  "responses": {
    "200": { "description": "Tipo actualizado", "body": "" },
    "404": { "description": "Tipo no encontrado", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"TransactionType not found\"\n}" }
  }
}
```

### DELETE /api/v1/transactionType/{transactionTypeId}

```api
{
  "title": "Eliminar Tipo de Transacción",
  "description": "Deshabilita un tipo por su ID",
  "method": "DELETE",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/transactionType/{transactionTypeId}",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true }
  ],
  "pathParams": [
    { "key": "transactionTypeId", "value": "ID del tipo", "required": true }
  ],
  "bodyType": "none",
  "responses": {
    "200": { "description": "Tipo deshabilitado", "body": "" },
    "404": { "description": "Tipo no encontrado", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"TransactionType not found\"\n}" }
  }
}
```

---

## UserRestController 🙍

Expone la gestión de **usuarios** bajo `/api/v1/user`, con `UserDTO`. 

| Método | Ruta                       | Descripción                          |
| ------ | -------------------------- | ------------------------------------ |
| POST   | /api/v1/user/create        | Registra un nuevo usuario           |
| PUT    | /api/v1/user/update        | Actualiza datos de un usuario       |
| DELETE | /api/v1/user/{userId}      | Elimina (deshabilita) un usuario    |

### POST /api/v1/user/create

```api
{
  "title": "Crear Usuario",
  "description": "Registra un nuevo usuario en el sistema",
  "method": "POST",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/user/create",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"username\": \"juanp\",\n  \"email\": \"juan@example.com\",\n  \"password\": \"Secreto123\"\n}",
  "responses": {
    "200": { "description": "Usuario creado", "body": "" },
    "404": { "description": "Error de validación", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"...\"\n}" }
  }
}
```

### PUT /api/v1/user/update

```api
{
  "title": "Actualizar Usuario",
  "description": "Modifica la información de un usuario existente",
  "method": "PUT",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/user/update",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true },
    { "key": "Content-Type", "value": "application/json", "required": true }
  ],
  "bodyType": "json",
  "requestBody": "{\n  \"userId\": 3,\n  \"username\": \"juanperez\",\n  \"email\": \"juan.perez@example.com\"\n}",
  "responses": {
    "200": { "description": "Usuario actualizado", "body": "" },
    "404": { "description": "Usuario no encontrado", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"User not found\"\n}" }
  }
}
```

### DELETE /api/v1/user/{userId}

```api
{
  "title": "Eliminar Usuario",
  "description": "Deshabilita un usuario por su ID",
  "method": "DELETE",
  "baseUrl": "http://localhost:8080",
  "endpoint": "/api/v1/user/{userId}",
  "headers": [
    { "key": "Authorization", "value": "Bearer <token>", "required": true }
  ],
  "pathParams": [
    { "key": "userId", "value": "ID del usuario", "required": true }
  ],
  "bodyType": "none",
  "responses": {
    "200": { "description": "Usuario eliminado", "body": "" },
    "404": { "description": "Usuario no encontrado", "body": "{\n  \"status\": \"NOT_FOUND\",\n  \"message\": \"User not found\"\n}" }
  }
}
```

---

**Manejo de errores**  
Todas las excepciones `NotFoundException` se capturan con el handler global `RestResponseEntityExceptionHandler`, que devuelve un JSON con el estado HTTP 404 y un mensaje detallado.  
Las validaciones de parámetros (por ejemplo, campos nulos) devuelven HTTP 400 con los errores de cada campo. 

---

**Resumen**  
- Cada controlador define su **ruta base**, mapea métodos HTTP y utiliza **DTOs** para entrada/salida.  
- Se extrae el **usuario autenticado** con `Utilities.getAuthenticatedUser()`.  
- Se orquesta la lógica de negocio en la **Capa de Servicio** y se maneja la respuesta HTTP con `ResponseEntity`.  
- El **mapeo de excepciones** garantiza respuestas consistentes para errores de negocio y validación.