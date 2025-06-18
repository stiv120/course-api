# Course API

API REST para la gestión de estudiantes, desarrollada con Spring Boot.

## Descripción

Este proyecto permite crear, consultar, actualizar y eliminar estudiantes en una base de datos MySQL. Incluye validaciones para los datos y soporte para mensajes de error en varios idiomas.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Spring Validation

## Endpoints principales

- `GET /api/v1/students` — Lista todos los estudiantes
- `GET /api/v1/students/{studentId}` — Consulta un estudiante por ID
- `POST /api/v1/students` — Crea un nuevo estudiante
- `PUT /api/v1/students/{studentId}` — Actualiza un estudiante existente
- `DELETE /api/v1/students/{studentId}` — Elimina un estudiante

## Ejemplo de JSON para crear o actualizar

```json
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@email.com"
}
```

## Validaciones

- El email debe ser único y válido.
- Los campos nombre, apellido y email son obligatorios.
- Los mensajes de error se muestran en el idioma del cliente si se envía el header `Accept-Language`.

## Cómo clonar el repositorio

```bash
# Clona el repositorio
 git clone https://github.com/stiv120/course-api.git
 cd course-api
```

## Configuración de la base de datos

1. Crea una base de datos MySQL llamada `course_db`.
2. Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
```

## Configuración rápida

1. Configura tu base de datos MySQL y actualiza `application.properties`.
2. Ejecuta el proyecto con Maven:
   ```bash
   mvn spring-boot:run
   ```

## Autor

Stiven Chávez
