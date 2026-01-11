# 📮 Endpoints para Postman - Course API

## 🚀 Información Base

**Base URL:** `http://localhost:8080`

**Base Path:** `/api/v1/students`

## 📋 Endpoints Disponibles

### 1. **GET** - Obtener todos los estudiantes

**URL:** `http://localhost:8080/api/v1/students`

**Método:** `GET`

**Headers:**
```
Content-Type: application/json
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "studentId": 1,
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@email.com"
  },
  {
    "studentId": 2,
    "firstName": "María",
    "lastName": "González",
    "email": "maria.gonzalez@email.com"
  }
]
```

**Respuesta Vacía (200 OK):**
```json
[]
```

---

### 2. **GET** - Obtener un estudiante por ID

**URL:** `http://localhost:8080/api/v1/students/{studentId}`

**Método:** `GET`

**Ejemplo:** `http://localhost:8080/api/v1/students/1`

**Path Variables:**
- `studentId` (Long) - ID del estudiante

**Headers:**
```
Content-Type: application/json
```

**Respuesta Exitosa (200 OK):**
```json
{
  "studentId": 1,
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@email.com"
}
```

**Respuesta No Encontrado (404 Not Found):**
```json
{
  "error": "Student not found"
}
```

---

### 3. **POST** - Crear un nuevo estudiante

**URL:** `http://localhost:8080/api/v1/students`

**Método:** `POST`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@email.com"
}
```

**Respuesta Exitosa (201 Created):**
```json
{
  "studentId": 1,
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@email.com"
}
```

**Respuesta Error - Email Duplicado (400 Bad Request):**
```json
{
  "email": "Email already exists"
}
```

**Respuesta Error - Validación (400 Bad Request):**
```json
{
  "firstName": "First name is required",
  "lastName": "Last name is required",
  "email": "Email is required"
}
```

---

### 4. **PUT** - Actualizar un estudiante existente

**URL:** `http://localhost:8080/api/v1/students/{studentId}`

**Método:** `PUT`

**Ejemplo:** `http://localhost:8080/api/v1/students/1`

**Path Variables:**
- `studentId` (Long) - ID del estudiante a actualizar

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "firstName": "Juan Carlos",
  "lastName": "Pérez García",
  "email": "juan.carlos.perez@email.com"
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "studentId": 1,
  "firstName": "Juan Carlos",
  "lastName": "Pérez García",
  "email": "juan.carlos.perez@email.com"
}
```

**Respuesta Error - Estudiante No Encontrado (404 Not Found):**
```json
{
  "error": "Student not found"
}
```

**Respuesta Error - Email Duplicado (400 Bad Request):**
```json
{
  "email": "Email already exists"
}
```

---

### 5. **DELETE** - Eliminar un estudiante

**URL:** `http://localhost:8080/api/v1/students/{studentId}`

**Método:** `DELETE`

**Ejemplo:** `http://localhost:8080/api/v1/students/1`

**Path Variables:**
- `studentId` (Long) - ID del estudiante a eliminar

**Headers:**
```
Content-Type: application/json
```

**Respuesta Exitosa (204 No Content):**
- Sin body en la respuesta

**Respuesta Error - Estudiante No Encontrado (404 Not Found):**
```json
{
  "error": "Student not found"
}
```

---

## ✅ Validaciones

### Campos Requeridos:
- `firstName` - No puede ser null o vacío
- `lastName` - No puede ser null o vacío
- `email` - No puede ser null o vacío, debe tener formato de email válido

### Reglas de Negocio:
- El email debe ser único (no puede haber dos estudiantes con el mismo email)
- El email debe tener un formato válido (ejemplo: `usuario@dominio.com`)

---

## 📝 Ejemplos de Uso en Postman

### 1. Crear un estudiante
```
POST http://localhost:8080/api/v1/students
Content-Type: application/json

{
  "firstName": "Ana",
  "lastName": "Rodríguez",
  "email": "ana.rodriguez@email.com"
}
```

### 2. Obtener todos los estudiantes
```
GET http://localhost:8080/api/v1/students
```

### 3. Obtener un estudiante específico
```
GET http://localhost:8080/api/v1/students/1
```

### 4. Actualizar un estudiante
```
PUT http://localhost:8080/api/v1/students/1
Content-Type: application/json

{
  "firstName": "Ana María",
  "lastName": "Rodríguez López",
  "email": "ana.maria.rodriguez@email.com"
}
```

### 5. Eliminar un estudiante
```
DELETE http://localhost:8080/api/v1/students/1
```

---

## 🔍 Códigos de Estado HTTP

- **200 OK** - Operación exitosa (GET, PUT)
- **201 Created** - Recurso creado exitosamente (POST)
- **204 No Content** - Operación exitosa sin contenido (DELETE)
- **400 Bad Request** - Error de validación o datos inválidos
- **404 Not Found** - Recurso no encontrado

---

## 🧪 Prueba Rápida

1. **Crear un estudiante:**
   ```bash
   POST http://localhost:8080/api/v1/students
   Body: {
     "firstName": "Test",
     "lastName": "Student",
     "email": "test@email.com"
   }
   ```

2. **Obtener todos los estudiantes:**
   ```bash
   GET http://localhost:8080/api/v1/students
   ```

3. **Obtener el estudiante creado:**
   ```bash
   GET http://localhost:8080/api/v1/students/1
   ```

4. **Actualizar el estudiante:**
   ```bash
   PUT http://localhost:8080/api/v1/students/1
   Body: {
     "firstName": "Updated",
     "lastName": "Name",
     "email": "updated@email.com"
   }
   ```

5. **Eliminar el estudiante:**
   ```bash
   DELETE http://localhost:8080/api/v1/students/1
   ```

---

## 📊 Estado de los Servicios

Para verificar que los servicios están corriendo:

```bash
# Ver estado de contenedores
docker-compose ps

# Ver logs de la aplicación
docker-compose logs -f app

# Ver logs de MySQL
docker-compose logs -f mysql
```

**Servicios:**
- ✅ **Aplicación:** http://localhost:8080
- ✅ **MySQL:** localhost:3306
