# Sistema de Gestión de Portafolio

Un sistema integral de gestión de portafolio construido con Java Spring Boot, que incluye autenticación JWT, control de acceso basado en roles y una API REST completa para administrar contenido de portafolio profesional.

## 📋 Tabla de Contenidos
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Prerrequisitos](#prerrequisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecutar la Aplicación](#ejecutar-la-aplicación)
- [Documentación de la API](#documentación-de-la-api)
- [Esquema de Base de Datos](#esquema-de-base-de-datos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Credenciales por Defecto](#credenciales-por-defecto)

## ✨ Características

- **Autenticación y Autorización**
  - Autenticación basada en JWT
  - Control de Acceso Basado en Roles (RBAC)
  - Registro e inicio de sesión de usuarios
  - Encriptación segura de contraseñas con BCrypt

- **Gestión de Portafolio**
  - Gestión de historial educativo
  - Portafolio de proyectos con etiquetado de habilidades
  - Seguimiento de experiencia profesional
  - Soporte para carga de archivos (imágenes, logos)

- **Gestión de Catálogos**
  - Catálogo de habilidades
  - Niveles de competencia
  - Estados de proyectos

- **Seguridad**
  - Seguridad a nivel de método con `@PreAuthorize`
  - Endpoints de administrador protegidos
  - Validación de tokens JWT

## 🛠 Tecnologías

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security** - Autenticación y Autorización
- **Spring Data JPA** - ORM de Base de Datos
- **PostgreSQL** - Base de Datos
- **Flyway** - Migraciones de base de datos
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de código repetitivo
- **JWT (io.jsonwebtoken)** - Autenticación basada en tokens
- **Maven** - Herramienta de construcción

## 📦 Prerrequisitos

Antes de ejecutar esta aplicación, asegúrate de tener instalado lo siguiente:

- **Java 17** o superior
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git** (para clonar el repositorio)

## 🚀 Instalación

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd portafolio
```

### 2. Crear Base de Datos PostgreSQL
```sql
CREATE DATABASE portfolio_db;
```

### 3. Configurar Propiedades de la Aplicación
Crear o actualizar `src/main/resources/application.properties`:

```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/portfolio_db
spring.datasource.username=tu_usuario_db
spring.datasource.password=tu_contraseña_db
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Configuración de Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Configuración de JWT
jwt.secret=tu-clave-secreta-aqui-hazla-larga-y-segura
jwt.expiration=86400000

# Configuración de Carga de Archivos
file.upload-dir=./uploads
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Configuración del Servidor
server.port=8080
```

### 4. Construir el Proyecto
```bash
mvn clean install
```

## ⚙️ Configuración

### Variables de Entorno (Opcional)
También puedes configurar la aplicación usando variables de entorno:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/portfolio_db
export DB_USERNAME=tu_usuario_db
export DB_PASSWORD=tu_contraseña_db
export JWT_SECRET=tu-clave-secreta
export JWT_EXPIRATION=86400000
```

### Almacenamiento de Archivos
La aplicación almacena los archivos cargados en el directorio especificado por `file.upload-dir`. Asegúrate de que este directorio exista y tenga permisos de escritura:

```bash
mkdir uploads
```

## 🏃 Ejecutar la Aplicación

### Usando Maven
```bash
mvn spring-boot:run
```

### Usando Java
```bash
mvn clean package
java -jar target/michael-0.0.1-SNAPSHOT.jar
```

La aplicación se iniciará en `http://localhost:8080`

## 📚 Documentación de la API

### Endpoints de Autenticación

#### Registrar Usuario
```http
POST /api/admin/register
Content-Type: application/json

{
  "fullName": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123"
}
```

#### Iniciar Sesión
```http
POST /api/admin/login
Content-Type: application/json

{
  "email": "admin@portfolio.com",
  "password": "admin123"
}
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### Endpoints de Educación (Solo Administrador)

#### Obtener Toda la Educación
```http
GET /api/admin/education
Authorization: Bearer {token}
```

#### Crear Educación
```http
POST /api/admin/education
Authorization: Bearer {token}
Content-Type: multipart/form-data

institution: "Nombre de la Universidad"
degree: "Licenciatura en Ciencias"
fieldOfStudy: "Ciencias de la Computación"
startDate: "2018-01-01"
endDate: "2022-06-30"
description: "Descripción aquí"
logo: [archivo]
```

#### Actualizar Educación
```http
PUT /api/admin/education/{id}
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

#### Eliminar Educación
```http
DELETE /api/admin/education/{id}
Authorization: Bearer {token}
```

### Endpoints de Proyectos (Solo Administrador)

#### Obtener Todos los Proyectos
```http
GET /api/admin/projects
Authorization: Bearer {token}
```

#### Crear Proyecto
```http
POST /api/admin/projects
Authorization: Bearer {token}
Content-Type: multipart/form-data

title: "Título del Proyecto"
description: "Descripción del proyecto"
startDate: "2023-01-01"
endDate: "2023-12-31"
projectUrl: "https://proyecto.com"
repositoryUrl: "https://github.com/usuario/repo"
statusId: 1
skillIds: [1, 2, 3]
image: [archivo]
```

#### Actualizar Proyecto
```http
PUT /api/admin/projects/{id}
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

#### Eliminar Proyecto
```http
DELETE /api/admin/projects/{id}
Authorization: Bearer {token}
```

### Endpoints de Experiencia (Solo Administrador)

#### Obtener Todas las Experiencias
```http
GET /api/admin/experiences
Authorization: Bearer {token}
```

#### Crear Experiencia
```http
POST /api/admin/experiences
Authorization: Bearer {token}
Content-Type: multipart/form-data

company: "Nombre de la Empresa"
position: "Ingeniero de Software"
startDate: "2022-01-01"
endDate: "2024-01-01"
description: "Descripción del trabajo"
companyLogo: [archivo]
```

#### Actualizar Experiencia
```http
PUT /api/admin/experiences/{id}
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

#### Eliminar Experiencia
```http
DELETE /api/admin/experiences/{id}
Authorization: Bearer {token}
```

### Endpoints de Catálogos (Solo Administrador)

#### Obtener Todas las Habilidades
```http
GET /api/admin/catalogs/skills
Authorization: Bearer {token}
```

#### Crear Habilidad
```http
POST /api/admin/catalogs/skills
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Java",
  "description": "Lenguaje de Programación"
}
```

#### Actualizar Habilidad
```http
PUT /api/admin/catalogs/skills/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Java",
  "description": "Lenguaje de Programación Actualizado"
}
```

#### Eliminar Habilidad
```http
DELETE /api/admin/catalogs/skills/{id}
Authorization: Bearer {token}
```

#### Obtener Todos los Niveles de Competencia
```http
GET /api/admin/catalogs/proficiency-levels
Authorization: Bearer {token}
```

#### Obtener Todos los Estados de Proyecto
```http
GET /api/admin/catalogs/project-statuses
Authorization: Bearer {token}
```

## 🗄️ Esquema de Base de Datos

La aplicación utiliza Flyway para las migraciones de base de datos. El esquema incluye:

### Tablas Principales
- `users` - Cuentas de usuario
- `roles` - Roles de usuario (ROLE_USER, ROLE_ADMIN)
- `user_roles` - Mapeo Usuario-Rol

### Tablas de Portafolio
- `education` - Formación académica
- `projects` - Proyectos del portafolio
- `experiences` - Experiencia laboral

### Tablas de Catálogos
- `skills` - Habilidades disponibles
- `proficiency_levels` - Niveles de competencia de habilidades
- `project_statuses` - Tipos de estado de proyecto

### Tablas de Relación
- `user_skills` - Habilidades de usuario con niveles de competencia
- `project_skills` - Habilidades utilizadas en proyectos

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/portfolio/michael/
│   │   ├── controller/        # Controladores REST
│   │   │   └── admin/         # Endpoints de administrador
│   │   ├── dto/               # Objetos de Transferencia de Datos
│   │   │   ├── admin/
│   │   │   │   ├── request/   # DTOs de Solicitud
│   │   │   │   └── response/  # DTOs de Respuesta
│   │   ├── entity/            # Entidades JPA
│   │   ├── exception/         # Excepciones Personalizadas
│   │   ├── helper/            # Servicios auxiliares
│   │   ├── mapper/            # Mapeadores MapStruct
│   │   ├── repository/        # Repositorios JPA
│   │   ├── security/          # Configuración de Seguridad
│   │   └── service/           # Lógica de Negocio
│   │       └── admin/
│   │           └── impl/      # Implementaciones de Servicios
│   └── resources/
│       ├── db/migration/      # Migraciones Flyway
│       └── application.properties
└── test/                      # Pruebas unitarias e integración
```

## 🔑 Credenciales por Defecto

Después de ejecutar la aplicación, se crea un usuario administrador por defecto:

- **Email:** `admin@portfolio.com`
- **Contraseña:** `admin123`

> ⚠️ **Importante:** ¡Cambia estas credenciales en producción!

## 🔒 Notas de Seguridad

1. **Secreto JWT:** Usa una clave secreta fuerte y aleatoria en producción
2. **Credenciales de Base de Datos:** Nunca confirmes credenciales en el control de versiones
3. **HTTPS:** Siempre usa HTTPS en producción
4. **Política de Contraseñas:** Implementa requisitos de contraseñas fuertes
5. **Limitación de Tasa:** Considera agregar limitación de tasa para endpoints de autenticación

## 📝 Notas Adicionales

- Todos los endpoints de administrador requieren autenticación con un token JWT válido
- El token debe incluirse en el encabezado `Authorization` como `Bearer {token}`
- Las cargas de archivos están limitadas a 10MB por defecto
- Los archivos cargados se almacenan en el directorio `uploads`

## 🤝 Contribuir

1. Haz un fork del repositorio
2. Crea una rama de característica (`git checkout -b feature/caracteristica-increible`)
3. Confirma tus cambios (`git commit -m 'Agregar alguna característica increíble'`)
4. Empuja a la rama (`git push origin feature/caracteristica-increible`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT.

## 📧 Contacto

Para preguntas o soporte, por favor contacta al equipo de desarrollo.