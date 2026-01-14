# Sistema de Gestión de Proyectos y Tareas - Backend

Sistema de gestión de proyectos y tareas desarrollado con **Spring Boot 3** y **Java 17**, aplicando **Arquitectura Hexagonal (Ports & Adapters)** y principios de **Clean Architecture**.

---

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.5.9**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **JUnit 5 + Mockito** (Pruebas unitarias)
- **Springdoc OpenAPI** (Swagger)
- **Docker + Docker Compose**

---

## 🏗️ Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)** con separación en capas:
```
src/main/java/com/riwi/projectmanagement/
├── domain/                    # Núcleo del negocio (sin dependencias externas)
│   ├── models/               # Entidades del dominio (Project, Task, User)
│   └── ports/
│       ├── in/              # Casos de uso (interfaces)
│       └── out/             # Puertos de salida (interfaces)
│
├── application/              # Lógica de aplicación
│   └── services/            # Implementación de casos de uso
│
└── infrastructure/           # Adaptadores externos
    ├── adapters/
    │   ├── in/rest/        # Controllers REST
    │   └── out/
    │       ├── persistence/ # JPA Repositories
    │       └── security/    # JWT, Password Encoder
    ├── config/              # Configuraciones (CORS, OpenAPI)
    └── security/            # Spring Security Config
```

### Principios aplicados:
- ✅ El dominio NO depende de Spring, JPA ni frameworks
- ✅ Las dependencias apuntan hacia el dominio
- ✅ Controllers y JPA son adaptadores
- ✅ Sin lógica de negocio en controllers

---

## 📋 Casos de Uso Implementados

### Autenticación
- `RegisterUserUseCase` - Registro de usuarios
- `LoginUserUseCase` - Autenticación con JWT

### Proyectos
- `CreateProjectUseCase` - Crear proyecto
- `ListProjectsUseCase` - Listar proyectos del usuario
- `ActivateProjectUseCase` - Activar proyecto
- `DeleteProjectUseCase` - Eliminar proyecto (soft delete)

### Tareas
- `CreateTaskUseCase` - Crear tarea
- `ListTasksByProjectUseCase` - Listar tareas de un proyecto
- `CompleteTaskUseCase` - Completar tarea
- `DeleteTaskUseCase` - Eliminar tarea (soft delete)

---

## 🔐 Seguridad

- **JWT (JSON Web Token)** para autenticación stateless
- **BCrypt** para encriptación de contraseñas
- **Spring Security** con validación de propietario
- Todos los endpoints (excepto `/api/auth/**`) requieren JWT válido
- Códigos HTTP correctos: 401 Unauthorized, 403 Forbidden, 400 Bad Request

---

## 📊 Reglas de Negocio

1. ✅ Un proyecto solo puede activarse si tiene **al menos una tarea**
2. ✅ Solo el propietario puede modificar sus proyectos y tareas
3. ✅ Una tarea completada no puede modificarse
4. ✅ Todas las eliminaciones son **lógicas** (soft delete)
5. ✅ Activación de proyectos y finalización de tareas generan **auditoría**
6. ✅ Activación de proyectos y finalización de tareas generan **notificación**

---

## 📡 API REST

### Autenticación (público)
- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión

### Proyectos (requiere JWT)
- `GET /api/projects` - Listar proyectos
- `POST /api/projects` - Crear proyecto
- `PATCH /api/projects/{id}/activate` - Activar proyecto
- `DELETE /api/projects/{id}` - Eliminar proyecto

### Tareas (requiere JWT)
- `GET /api/projects/{projectId}/tasks` - Listar tareas
- `POST /api/projects/{projectId}/tasks` - Crear tarea
- `PATCH /api/tasks/{id}/complete` - Completar tarea
- `DELETE /api/tasks/{id}` - Eliminar tarea

### Documentación
- `GET /swagger-ui.html` - Interfaz Swagger
- `GET /v3/api-docs` - Especificación OpenAPI

---

## ⚙️ Instalación y Ejecución

### Prerequisitos
- Java 17+
- Maven 3.8+
- MySQL 8.0 (o Docker)

### Opción 1: Ejecución local

**1. Clonar el repositorio**
```bash
git clone <repository-url>
cd project-management
```

**2. Configurar base de datos**

Crear base de datos en MySQL:
```sql
CREATE DATABASE project_management_db;
```

Actualizar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/project_management_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

**3. Compilar y ejecutar**
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

### Opción 2: Ejecución con Docker (Recomendado)

**1. Construir y levantar contenedores**
```bash
docker-compose up -d
```

**2. Ver logs**
```bash
docker-compose logs -f
```

**3. Detener servicios**
```bash
docker-compose down
```

**4. Detener y eliminar volúmenes**
```bash
docker-compose down -v
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 🧪 Pruebas Unitarias

El proyecto incluye **5 pruebas unitarias** usando JUnit 5 y Mockito:
```bash
mvn test
```

### Pruebas implementadas:
1. ✅ `ActivateProject_WithTasks_ShouldSucceed`
2. ✅ `ActivateProject_WithoutTasks_ShouldFail`
3. ✅ `ActivateProject_ByNonOwner_ShouldFail`
4. ✅ `CompleteTask_AlreadyCompleted_ShouldFail`
5. ✅ `CompleteTask_ShouldGenerateAuditAndNotification`

---

## 👤 Credenciales de Prueba

Puedes registrar usuarios desde:
- **Swagger:** `http://localhost:8080/swagger-ui.html`
- **Frontend:** `http://localhost:5500` (si usas Live Server)

**Ejemplo de usuario:**
```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "123456"
}
```

---

## 🌐 Swagger / OpenAPI

Accede a la documentación interactiva de la API:
```
http://localhost:8080/swagger-ui.html
```

Para probar endpoints protegidos:
1. Ejecuta `POST /api/auth/login`
2. Copia el token JWT de la respuesta
3. Click en "Authorize" (🔒)
4. Ingresa: `Bearer {tu-token}`
5. Prueba los endpoints

---

## 🗄️ Modelo de Datos

### User
- `id` (UUID)
- `username` (String, único)
- `email` (String, único)
- `password` (String, encriptado con BCrypt)

### Project
- `id` (UUID)
- `ownerId` (UUID) - Referencia a User
- `name` (String)
- `status` (Enum: DRAFT, ACTIVE)
- `deleted` (Boolean) - Soft delete

### Task
- `id` (UUID)
- `projectId` (UUID) - Referencia a Project
- `title` (String)
- `completed` (Boolean)
- `deleted` (Boolean) - Soft delete

---

## 🐳 Docker

### Dockerfile
Construye la imagen de la aplicación con Maven multi-stage build.

### docker-compose.yml
Orquesta dos servicios:
- **app:** Backend Spring Boot (puerto 8080)
- **mysql:** Base de datos MySQL 8.0 (puerto 3307→3306)

### Volúmenes
- `mysql_data`: Persiste datos de la base de datos

---

## 🛠️ Decisiones Técnicas

### 1. Arquitectura Hexagonal
Separa el dominio del negocio de los frameworks. Facilita testing y mantenibilidad.

### 2. JWT Stateless
No se mantiene estado en el servidor. El token contiene toda la información necesaria.

### 3. Soft Delete
Las eliminaciones son lógicas (flag `deleted`). Permite auditoría y recuperación de datos.

### 4. Auditoría en Consola
Implementación simple de `AuditLogPort` que imprime en consola. Puede reemplazarse por persistencia en BD.

### 5. CORS Configurado
Permite peticiones desde el frontend en diferentes puertos (5500, 8000, 3000).

### 6. Validaciones en Dominio
Las reglas de negocio se validan en el dominio antes de persistir.

### 7. GlobalExceptionHandler
Maneja excepciones de forma centralizada y devuelve códigos HTTP apropiados.

---

## 📂 Estructura de Puertos

### Puertos IN (Casos de Uso)
Interfaces que definen las acciones del negocio.

### Puertos OUT (Necesidades)
- `ProjectRepositoryPort` - Persistencia de proyectos
- `TaskRepositoryPort` - Persistencia de tareas
- `UserRepositoryPort` - Persistencia de usuarios
- `AuditLogPort` - Registro de auditoría
- `NotificationPort` - Notificaciones
- `CurrentUserPort` - Usuario autenticado
- `JwtServicePort` - Generación/validación JWT
- `PasswordEncoderPort` - Encriptación de contraseñas

---

## 📧 Contacto

Desarrollado como parte del Assessment Técnico de RIWI.

---

## 📄 Licencia

Este proyecto es de uso educativo.