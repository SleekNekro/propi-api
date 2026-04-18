# Propi API

REST API para el tracker personal de hábitos Propi.

## Base URL

```
http://localhost:8080/api
```

## Autenticación

> ⚠️ **Nota**: Todos los endpoints (excepto `/api/auth/register` y `/api/auth/login`) requieren autenticación JWT.

### Flujo de autenticación

1. **Registrar** usuario → recibe token JWT
2. **Login** → recibe token JWT
3. Usar token en header `Authorization: Bearer <token>`

---

### Registro de usuario

```http
POST /api/auth/register
Content-Type: application/json
```

```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "securepassword123"
}
```

**Respuesta** (`201 Created`):

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "username": "john",
    "email": "john@example.com"
  }
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "john@example.com",
  "password": "securepassword123"
}
```

**Respuesta** (`200 OK`):

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "username": "john",
    "email": "john@example.com"
  }
}
```

### Usar el token

Todos los endpoints protegidos requieren el token:

```http
GET /api/users/1/habits
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

## Endpoints

Todos los endpoints de recursos están anidados bajo `{userId}`.

---

## Hábitos

### Listar hábitos

```http
GET /api/users/{userId}/habits
```

**Respuesta** (`200 OK`):

```json
[
  {
    "id": 1,
    "userId": 1,
    "name": "Morning run",
    "difficulty": "NORMAL",
    "weekDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
    "duration": 30
  }
]
```

### Obtener hábito por ID

```http
GET /api/users/{userId}/habits/{id}
```

**Respuesta** (`200 OK`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Morning run",
  "difficulty": "NORMAL",
  "weekDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "duration": 30
}
```

### Crear hábito

```http
POST /api/users/{userId}/habits
Content-Type: application/json
```

```json
{
  "name": "Morning run",
  "difficulty": "NORMAL",
  "weekDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "duration": 30
}
```

**Parámetros**:

| Campo | Tipo | Requerido | Descripción |
|-------|------|----------|-------------|
| `name` | String | ✅ | Nombre del hábito |
| `difficulty` | Difficulty | ✅ | `EASY`, `NORMAL`, `HARD` |
| `weekDays` | List<Week> | ✅ | Días de la semana |
| `duration` | Int? | ❌ | Duración en minutos |

**Difficulty**:

```kotlin
enum class Difficulty {
    EASY,   // Fácil
    NORMAL,  // Normal
    HARD     // Difícil
}
```

**Week**:

```kotlin
enum class Week {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
```

**Respuesta** (`201 Created`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Morning run",
  "difficulty": "NORMAL",
  "weekDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "duration": 30
}
```

### Actualizar hábito

```http
PUT /api/users/{userId}/habits/{id}
Content-Type: application/json
```

```json
{
  "name": "Evening run",
  "difficulty": "HARD",
  "weekDays": ["TUESDAY", "THURSDAY", "SATURDAY"],
  "duration": 45
}
```

**Respuesta** (`200 OK`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Evening run",
  "difficulty": "HARD",
  "weekDays": ["TUESDAY", "THURSDAY", "SATURDAY"],
  "duration": 45
}
```

### Eliminar hábito

```http
DELETE /api/users/{userId}/habits/{id}
```

**Respuesta** (`204 No Content`)

---

## Tareas

### Listar tareas

```http
GET /api/users/{userId}/tasks
```

**Respuesta** (`200 OK`):

```json
[
  {
    "id": 1,
    "userId": 1,
    "name": "Buy groceries",
    "dateTime": "2024-01-15T10:00",
    "completed": false
  }
]
```

### Obtener tarea por ID

```http
GET /api/users/{userId}/tasks/{id}
```

**Respuesta** (`200 OK`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Buy groceries",
  "dateTime": "2024-01-15T10:00",
  "completed": false
}
```

### Crear tarea

```http
POST /api/users/{userId}/tasks
Content-Type: application/json
```

```json
{
  "name": "Buy groceries",
  "dateTime": "2024-01-15T10:00"
}
```

**Parámetros**:

| Campo | Tipo | Requerido | Descripción |
|-------|------|----------|-------------|
| `name` | String | ✅ | Nombre de la tarea |
| `dateTime` | String | ✅ | Fecha y hora (ISO 8601) |

**Respuesta** (`201 Created`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Buy groceries",
  "dateTime": "2024-01-15T10:00",
  "completed": false
}
```

### Actualizar tarea

```http
PUT /api/users/{userId}/tasks/{id}
Content-Type: application/json
```

```json
{
  "name": "Buy groceries and cleaning",
  "dateTime": "2024-01-15T14:00"
}
```

**Respuesta** (`200 OK`):

```json
{
  "id": 1,
  "userId": 1,
  "name": "Buy groceries and cleaning",
  "dateTime": "2024-01-15T14:00",
  "completed": false
}
```

### Eliminar tarea

```http
DELETE /api/users/{userId}/tasks/{id}
```

**Respuesta** (`204 No Content`)

---

## Daily Logs

### Listar logs entre fechas

```http
GET /api/users/{userId}/dailylogs?startDate={startDate}&endDate={endDate}
```

**Parámetros de query**:

| Parámetro | Tipo | Requerido | Descripción |
|----------|------|----------|-------------|
| `startDate` | String | ✅ | Fecha inicial (YYYY-MM-DD) |
| `endDate` | String | ✅ | Fecha final (YYYY-MM-DD) |

**Ejemplo**:

```http
GET /api/users/1/dailylogs?startDate=2024-01-01&endDate=2024-01-31
```

**Respuesta** (`200 OK`):

```json
[
  {
    "id": 1,
    "habitId": 1,
    "taskId": null,
    "date": "2024-01-15",
    "completed": true,
    "karmaPoints": 10.5
  }
]
```

### Marcar completado

```http
PATCH /api/users/{userId}/dailylogs/{habitId}/completed?date={date}&completed={completed}
```

**Parámetros de query**:

| Parámetro | Tipo | Requerido | Descripción |
|----------|------|----------|-------------|
| `date` | String | ✅ | Fecha del log (YYYY-MM-DD) |
| `completed` | Boolean | ✅ | Estado de completado |

**Ejemplo**:

```http
PATCH /api/users/1/dailylogs/1/completed?date=2024-01-15&completed=true
```

**Respuesta** (`200 OK`)

---

## Períodos de Descanso

### Listar períodos de descanso

```http
GET /api/users/{userId}/rest-periods
```

**Respuesta** (`200 OK`):

```json
[
  {
    "id": 1,
    "userId": 1,
    "dateIni": "2024-12-25",
    "dateEnd": "2025-01-01"
  }
]
```

### Listar períodos activos por fecha

```http
GET /api/users/{userId}/rest-periods/active?date={date}
```

**Parámetros de query**:

| Parámetro | Tipo | Requerido | Descripción |
|----------|------|----------|-------------|
| `date` | String | ✅ | Fecha a consultar (YYYY-MM-DD) |

**Ejemplo**:

```http
GET /api/users/1/rest-periods/active?date=2024-12-28
```

**Respuesta** (`200 OK`):

```json
[
  {
    "id": 1,
    "userId": 1,
    "dateIni": "2024-12-25",
    "dateEnd": "2025-01-01"
  }
]
```

### Crear período de descanso

```http
POST /api/users/{userId}/rest-periods
Content-Type: application/json
```

```json
{
  "dateIni": "2024-12-25",
  "dateEnd": "2025-01-01"
}
```

**Parámetros**:

| Campo | Tipo | Requerido | Descripción |
|-------|------|----------|-------------|
| `dateIni` | String | ✅ | Fecha inicial (YYYY-MM-DD) |
| `dateEnd` | String | ✅ | Fecha final (YYYY-MM-DD) |

**Respuesta** (`201 Created`):

```json
{
  "id": 1,
  "userId": 1,
  "dateIni": "2024-12-25",
  "dateEnd": "2025-01-01"
}
```

### Eliminar período de descanso

```http
DELETE /api/users/{userId}/rest-periods/{id}
```

**Respuesta** (`204 No Content`)

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| `200` | OK - Solicitud exitosa |
| `201` | Created - Recurso creado |
| `204` | No Content - Respuesta vacía |
| `400` | Bad Request - Solicitud inválida |
| `401` | Unauthorized - No autenticado |
| `403` | Forbidden - Sin permisos |
| `404` | Not Found - Recurso no encontrado |
| `500` | Internal Server Error - Error del servidor |

---

## Modelos de Datos

### HabitRequestDTO

```kotlin
data class HabitRequestDTO(
    val name: String,
    val difficulty: Difficulty,
    val weekDays: List<Week>,
    val duration: Int?
)
```

### HabitResponseDTO

```kotlin
data class HabitResponseDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val difficulty: Difficulty,
    val weekDays: List<Week>,
    val duration: Int?
)
```

### TaskRequestDTO

```kotlin
data class TaskRequestDTO(
    val name: String,
    val dateTime: String
)
```

### TaskResponseDTO

```kotlin
data class TaskResponseDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val dateTime: String,
    val completed: Boolean
)
```

### DailyLogDTO

```kotlin
data class DailyLogDTO(
    val id: Long,
    val habitId: Long?,
    val taskId: Long?,
    val date: String,
    val completed: Boolean,
    val karmaPoints: Float
)
```

### RestPeriodRequestDTO

```kotlin
data class RestPeriodRequestDTO(
    val dateIni: String,
    val dateEnd: String
)
```

### RestPeriodResponseDTO

```kotlin
data class RestPeriodResponseDTO(
    val id: Long,
    val userId: Long,
    val dateIni: String,
    val dateEnd: String
)
```

### RegisterDTO

```kotlin
data class RegisterDTO(
    val username: String,
    val email: String,
    val password: String
)
```

### UserResponseDTO

```kotlin
data class UserResponseDTO(
    val id: Long,
    val username: String,
    val email: String
)
```

---

## Tecnologías

- **Kotlin** - Lenguaje de programación
- **Spring Boot** - Framework web
- **Spring Data JPA** - Persistencia
- **PostgreSQL** - Base de datos (H2 para desarrollo)
- **JWT** - Autenticación con tokens
- **kotlinx.serialization** - Serialización JSON
- **Spring Security** - Seguridad

---

## Repositorios

- [propi-api](https://github.com/SleekNekro/propi-api) - API REST
- [propi-shared](https://github.com/SleekNekro/propi-shared) - DTOs compartidos
- [propi-desktop](https://github.com/SleekNekro/propi-desktop) - Cliente de escritorio (pendiente)