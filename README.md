# Sistema de Gestión de Portafolio

Un sistema integral de gestión de portafolio construido con Java Spring Boot, que incluye autenticación JWT, control de acceso basado en roles y una API REST completa para administrar contenido de portafolio profesional.

## 📋 Tabla de Contenidos
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Prerrequisitos](#prerrequisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecutar la Aplicación](#ejecutar-la-aplicación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Credenciales por Defecto](#credenciales-por-defecto)
- [Documentación de la API](#documentación-de-la-api)

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
app.storage.location=./uploads
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
La aplicación almacena los archivos cargados en el directorio especificado por `app.storage.location`. Asegúrate de que este directorio exista y tenga permisos de escritura:

```bash
mkdir uploads
```

> **Nota:** El directorio de almacenamiento se crea automáticamente al iniciar la aplicación si no existe.

## 🏃 Ejecutar la Aplicación

### Usando Maven
```bash
mvn spring-boot:run
```

### Usando Java
```bash
java -jar target/portfolio_michael-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en `http://localhost:8080`

## 📁 Estructura del Proyecto

El proyecto sigue **Arquitectura Hexagonal (Ports & Adapters)** con una organización modular basada en dominios:

```
src/main/java/com/portfolio/michael/
├── modules/
│   ├── auth/                      # Módulo de Autenticación
│   │   ├── controller/            # AuthController
│   │   ├── dto/                   # LoginRequest, RegisterRequest, AuthResponse
│   │   ├── entity/                # User, Role
│   │   ├── repository/            # UserRepository, RoleRepository
│   │   ├── security/              # JwtService, JwtAuthenticationFilter, SecurityConfig
│   │   └── service/               # AuthService, AuthServiceImpl
│   │
│   ├── project/                   # Módulo de Proyectos (Hexagonal)
│   │   ├── controller/            # ProjectController (Presentation)
│   │   ├── application/           # Capa de Aplicación
│   │   │   ├── dto/               # CreateProjectRequest, ProjectResponse
│   │   │   ├── mapper/            # ProjectMapper
│   │   │   └── usecase/           # CreateProjectUseCase
│   │   ├── domain/                # Capa de Dominio
│   │   │   ├── Project.java       # Entidad de Dominio
│   │   │   └── ProjectRepository.java  # Port (Interface)
│   │   └── infrastructure/        # Capa de Infraestructura
│   │       ├── configuration/     # ProjectConfiguration (Beans)
│   │       └── persistence/       # JpaProjectRepository (Adapter)
│   │
│   ├── education/                 # Módulo de Educación (Hexagonal)
│   │   ├── controller/            # EducationController (Presentation)
│   │   ├── application/           # Capa de Aplicación
│   │   │   ├── dto/               # EducationRequest, EducationResponse
│   │   │   ├── mapper/            # EducationMapper
│   │   │   └── usecase/           # CreateEducationUseCase, UpdateEducationUseCase, etc.
│   │   ├── domain/                # Capa de Dominio
│   │   │   ├── EducationEntity.java    # Entidad de Dominio
│   │   │   └── EducationRepository.java # Port (Interface)
│   │   └── infrastructure/        # Capa de Infraestructura
│   │       ├── configuration/     # EducationConfiguration (Beans)
│   │       └── persistence/       # JpaEducationRepository (Adapter)
│   │
│   ├── experience/                # Módulo de Experiencia (Hexagonal)
│   │   ├── controller/            # ExperienceController (Presentation)
│   │   ├── application/           # Capa de Aplicación
│   │   │   ├── dto/               # ExperienceRequest, ExperienceResponse
│   │   │   ├── mapper/            # ExperienceMapper
│   │   │   └── usecase/           # CreateExperienceUseCase, UpdateExperienceUseCase, etc.
│   │   ├── domain/                # Capa de Dominio
│   │   │   ├── ExperienceEntity.java    # Entidad de Dominio
│   │   │   └── ExperienceRepository.java # Port (Interface)
│   │   └── infrastructure/        # Capa de Infraestructura
│   │       ├── configuration/     # ExperienceConfiguration (Beans)
│   │       └── persistence/       # JpaExperienceRepository (Adapter)
│   │
│   ├── catalog/                   # Módulo de Catálogos (Hexagonal)
│   │   ├── controller/            # CatalogController (Presentation)
│   │   ├── application/           # Capa de Aplicación
│   │   │   ├── dto/               # CatalogResponse, SkillRequest, etc.
│   │   │   └── usecase/           # Use Cases para Skills, ProficiencyLevel, etc.
│   │   ├── domain/                # Capa de Dominio
│   │   │   ├── Skill.java, ProficiencyLevel.java, ProjectStatus.java
│   │   │   └── SkillRepository.java, ProficiencyLevelRepository.java, etc.
│   │   └── infrastructure/        # Capa de Infraestructura
│   │       ├── configuration/     # CatalogConfiguration (Beans)
│   │       └── persistence/       # JpaSkillRepository, JpaProficiencyLevelRepository, etc.
│   │
│   └── file/                      # Módulo de Archivos (Hexagonal)
│       ├── controller/            # FileController (Presentation)
│       ├── application/           # Capa de Aplicación
│       │   └── usecase/           # GetFileUseCase
│       ├── domain/                # Capa de Dominio
│       │   ├── model/             # FileInput
│       │   └── port/              # FileStoragePort (Interface)
│       └── infrastructure/        # Capa de Infraestructura
│           ├── configuration/     # FileConfiguration (Beans)
│           └── storage/           # FileSystemStorageAdapter (Adapter)
│
├── shared/                        # Componentes Compartidos
│   ├── config/                    # SwaggerConfig, PasswordEncoderConfig
│   ├── dto/                       # ApiResponse
│   └── exception/                 # GlobalExceptionHandler, ResourceNotFoundException
│
└── validation/                    # Validadores Personalizados
    └── ValidFile                  # Validador de archivos
```

### Descripción de Módulos

- **auth**: Maneja autenticación, autorización y gestión de usuarios
- **project**: Gestión de proyectos del portafolio (Arquitectura Hexagonal)
- **education**: Gestión del historial educativo (Arquitectura Hexagonal)
- **experience**: Gestión de experiencia laboral (Arquitectura Hexagonal)
- **catalog**: Gestión de catálogos - habilidades, niveles de competencia, estados (Arquitectura Hexagonal)
- **file**: Manejo de carga y descarga de archivos (Arquitectura Hexagonal)
- **shared**: Componentes compartidos entre módulos (configuraciones, DTOs, excepciones)

### Arquitectura Hexagonal

Los módulos `project`, `education`, `experience`, `catalog` y `file` siguen **Arquitectura Hexagonal** con las siguientes capas:

- **Domain (Dominio)**: Contiene la lógica de negocio central, entidades de dominio y ports (interfaces)
- **Application (Aplicación)**: Contiene los Use Cases que orquestan la lógica de negocio, DTOs y mappers
- **Infrastructure (Infraestructura)**: Contiene los adapters (implementaciones de ports), configuración y persistencia
- **Presentation (Presentación)**: Controllers que exponen la API REST

## 🔑 Credenciales por Defecto

Para acceder al sistema con privilegios de administrador:

- **Email:** `admin@portfolio.com`
- **Contraseña:** `admin123`

> ⚠️ **Importante:** ¡Cambia estas credenciales en producción!

## 📚 Documentación de la API

La documentación completa de la API está disponible a través de Swagger UI una vez que la aplicación esté en ejecución:

```
http://localhost:8080/swagger-ui.html
```

Swagger proporciona:
- Documentación interactiva de todos los endpoints
- Capacidad de probar endpoints directamente desde el navegador
- Esquemas de request/response
- Códigos de estado y mensajes de error

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
- Los nombres de archivos se generan automáticamente con UUID para evitar conflictos
- El sistema valida que los archivos se almacenen dentro del directorio configurado por seguridad
- Los archivos se sirven a través del endpoint `/api/admin/files/{filename}` con el tipo MIME correcto

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