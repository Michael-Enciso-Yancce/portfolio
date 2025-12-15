# Arquitectura del Proyecto

## Visión General

Este proyecto es un sistema de gestión de portafolio construido con Java Spring Boot. Implementa **Arquitectura Hexagonal (Ports & Adapters)** para la mayoría de los módulos, asegurando mantenibilidad, testeabilidad y separación de responsabilidades.

## Stack Tecnológico

### Backend Core
- **Java 17**: Lenguaje de programación principal
- **Spring Boot 3.5.3**: Framework de aplicación
- **Spring Security**: Autenticación y autorización (JWT & RBAC)
- **Spring Data JPA**: Interacciones con base de datos
- **Spring WebSocket**: Comunicación en tiempo real
- **Maven**: Herramienta de construcción y gestión de dependencias

### Base de Datos
- **MySQL 8.0**: Sistema de gestión de base de datos relacional
- **Flyway**: Herramienta de migración de base de datos

### Seguridad y Autenticación
- **JWT (io.jsonwebtoken 0.11.5)**: Autenticación basada en tokens
- **BCrypt**: Encriptación de contraseñas
- **OAuth2**: Soporte para Google OAuth2 (google-api-client 2.2.0)

### Utilidades
- **MapStruct 1.5.5**: Mapeo entre entidades y DTOs
- **Lombok 1.18.30**: Reducción de código boilerplate
- **Apache Tika 2.9.1**: Detección de tipos MIME para archivos
- **SpringDoc OpenAPI 2.8.5**: Documentación automática de API (Swagger)

### Containerización
- **Docker**: Containerización de la aplicación
- **Docker Compose**: Orquestación de servicios (app + database)

## Patrón de Arquitectura: Arquitectura Hexagonal

### Módulos con Arquitectura Hexagonal

Los siguientes módulos implementan el patrón Hexagonal completo:
- `auth` - Autenticación y autorización
- `project` - Gestión de proyectos
- `education` - Historial educativo
- `experience` - Experiencia laboral
- `catalog` - Catálogos (skills, niveles, estados)
- `file` - Almacenamiento de archivos

### Capas de la Arquitectura Hexagonal

#### 1. Capa de Dominio (`domain/`)

**Propósito**: Contiene la lógica de negocio central y modelos de dominio

**Componentes**:
- **Entidades de Dominio**: Objetos de negocio puros (ej: `Project`, `User`, `EducationEntity`)
- **Ports (Interfaces)**: Definen contratos para dependencias externas (ej: `ProjectRepository`, `FileStoragePort`)
- **Enums**: Tipos de dominio (ej: `AuthProvider`, `ProjectStatus`)

**Reglas**:
- Sin dependencias en frameworks externos
- Solo contiene lógica de negocio
- Define interfaces (ports) para interacciones externas
- Completamente independiente de la infraestructura

**Ejemplo**:
```java
// Domain Entity
public class User {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private AuthProvider authProvider;
    private String providerId;
    private Set<Role> roles;
}

// Port (Interface)
public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
```

#### 2. Capa de Aplicación (`application/`)

**Propósito**: Orquesta la lógica de negocio a través de Use Cases

**Componentes**:
- **Use Cases**: Implementan operaciones de negocio específicas (ej: `CreateProjectUseCase`, `GoogleLoginUseCase`)
- **DTOs**: Data Transfer Objects para request/response (ej: `CreateProjectRequest`, `ProjectResponse`)
- **Mappers**: Convierten entre entidades de dominio y DTOs (ej: `ProjectMapper`)
- **Ports**: Interfaces adicionales para servicios (ej: `TokenProvider`, `PasswordEncoderPort`)

**Reglas**:
- Depende de la capa de Dominio
- Sin dependencia directa en la capa de Infraestructura
- Usa ports (interfaces) para interactuar con sistemas externos
- Contiene la lógica de orquestación

**Ejemplo**:
```java
public class GoogleLoginUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;
    
    public AuthResponse execute(GoogleLoginRequest request) {
        // 1. Verificar token de Google
        // 2. Buscar o crear usuario
        // 3. Generar token JWT
        // 4. Retornar respuesta
    }
}
```

#### 3. Capa de Infraestructura (`infrastructure/`)

**Propósito**: Implementa detalles técnicos e integraciones externas

**Componentes**:
- **Adapters**: Implementan ports definidos en la capa de Dominio
  - **Persistence Adapters**: Implementaciones de base de datos (ej: `JpaUserRepository`)
  - **Storage Adapters**: Implementaciones de almacenamiento (ej: `FileSystemStorageAdapter`)
  - **Security Adapters**: Implementaciones de seguridad (ej: `JwtTokenProvider`, `SpringSecurityPasswordEncoder`)
- **Configuration**: Definiciones de Spring Beans (ej: `ProjectConfiguration`, `SecurityConfig`)
- **JPA Entities**: Entidades de persistencia (ej: `UserJpaEntity`, `ProjectEntity`)

**Reglas**:
- Implementa interfaces de la capa de Dominio
- Contiene código específico del framework
- Maneja integraciones con sistemas externos
- Convierte entre entidades de dominio y entidades JPA

**Ejemplo**:
```java
@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository jpa;
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email)
            .map(UserJpaEntity::toDomain);
    }
    
    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        return jpa.save(entity).toDomain();
    }
}
```

#### 4. Capa de Presentación (`controller/`)

**Propósito**: Expone endpoints de API REST

**Componentes**:
- **Controllers**: Manejan requests y responses HTTP (ej: `AuthController`, `ProjectController`)

**Reglas**:
- Depende de la capa de Aplicación (Use Cases)
- Maneja aspectos específicos de HTTP (validación, códigos de estado, etc.)
- Delega lógica de negocio a Use Cases
- Responsable de la serialización/deserialización JSON

**Ejemplo**:
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final GoogleLoginUseCase googleLoginUseCase;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }
    
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest request) {
        return ResponseEntity.ok(googleLoginUseCase.execute(request));
    }
}
```

## Estructura de Módulos

### Módulo Auth (Autenticación y Autorización)

```
auth/
├── controller/
│   └── AuthController.java           # Endpoints de autenticación
├── application/
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── GoogleLoginRequest.java
│   │   └── AuthResponse.java
│   ├── port/
│   │   ├── TokenProvider.java        # Port para generación de tokens
│   │   └── PasswordEncoderPort.java  # Port para encriptación
│   └── usecase/
│       ├── LoginUseCase.java
│       ├── RegisterUseCase.java
│       └── GoogleLoginUseCase.java
├── domain/
│   ├── User.java                     # Entidad de dominio
│   ├── Role.java
│   ├── AuthProvider.java             # Enum: LOCAL, GOOGLE
│   ├── UserRepository.java           # Port
│   └── RoleRepository.java           # Port
└── infrastructure/
    ├── persistence/
    │   ├── UserJpaEntity.java        # Entidad JPA
    │   ├── RoleJpaEntity.java
    │   ├── JpaUserRepository.java    # Adapter
    │   ├── JpaRoleRepository.java
    │   ├── SpringDataUserRepository.java
    │   └── SpringDataRoleRepository.java
    ├── security/
    │   ├── SecurityConfig.java       # Configuración de Spring Security
    │   ├── JwtService.java
    │   ├── JwtTokenProvider.java     # Implementación de TokenProvider
    │   ├── JwtAuthenticationFilter.java
    │   ├── CustomUserDetailsService.java
    │   └── SpringSecurityPasswordEncoder.java
    └── configuration/
        └── AuthConfiguration.java    # Beans de Use Cases
```

### Módulo Project (Gestión de Proyectos)

```
project/
├── controller/
│   └── ProjectController.java        # Gestiona Proyectos y Estados
├── application/
│   ├── dto/
│   │   ├── CreateProjectRequest.java
│   │   ├── UpdateProjectRequest.java
│   │   └── ProjectResponse.java
│   ├── mapper/
│   │   └── ProjectMapper.java
│   └── usecase/
│       ├── CreateProjectUseCase.java
│       ├── UpdateProjectUseCase.java
│       ├── GetProjectUseCase.java
│       └── DeleteProjectUseCase.java
├── domain/
│   ├── Project.java
│   └── ProjectRepository.java
└── infrastructure/
    ├── persistence/
    │   ├── ProjectEntity.java
    │   ├── JpaProjectRepository.java
    │   └── SpringDataProjectRepository.java
    └── configuration/
        └── ProjectConfiguration.java
```

### Módulo File (Gestión de Archivos)

```
file/
├── controller/
│   └── FileController.java
├── application/
│   └── usecase/
│       ├── UploadFileUseCase.java
│       └── GetFileUseCase.java
├── domain/
│   ├── model/
│   │   └── FileInput.java
│   └── port/
│       └── FileStoragePort.java      # Port para almacenamiento
└── infrastructure/
    ├── storage/
    │   └── FileSystemStorageAdapter.java  # Implementación local
    └── configuration/
        └── FileConfiguration.java

### Módulo Showcase (Documentación de Proyectos)

```
showcase/
├── controller/
│   └── ShowcaseController.java        # Admin API (Movido a paquete estándar)
├── application/
│   ├── dto/
│   │   └── CreateShowcaseRequest.java
│   ├── usecase/
│   │   ├── CreateProjectShowcaseUseCase.java
│   │   ├── GetProjectShowcaseUseCase.java
│   │   └── UpdateProjectShowcaseUseCase.java
├── domain/
│   ├── ProjectShowcase.java
│   ├── ProjectShowcaseContent.java    # POJO para JSON content
│   └── ProjectShowcaseRepository.java
└── infrastructure/
    ├── persistence/
    │   ├── ProjectShowcaseJpaEntity.java
    │   ├── ProjectShowcaseContentConverter.java
    │   └── JpaProjectShowcaseRepository.java
    └── web/
        └── (Eliminado/Vacio)
```

### Módulo Public API (Exposición Pública)

```
publicapi/
├── controller/
│   └── PublicController.java          # Unifica endpoints públicos
├── application/
│   ├── dto/
│   │   ├── ProfileResponse.java
│   │   └── ProjectPublicResponse.java
│   └── usecase/
│       ├── GetProfileUseCase.java
│       └── GetPublicProjectsUseCase.java
└── infrastructure/
    └── configuration/
        └── PublicApiConfiguration.java
```

### Módulo Skill (Habilidades)

```
skill/
├── controller/
│   └── SkillController.java
├── application/
│   ├── usecase/
│   │   ├── CreateSkillUseCase.java
│   │   ├── UpdateUserSkillUseCase.java  # Gestión de relación usuario-skill
│   │   └── ...
├── domain/
│   ├── Skill.java
│   └── UserSkill.java                 # Entidad de relación con nivel
└── infrastructure/
    ├── persistence/
    │   ├── SkillJpaEntity.java
    │   └── UserSkillJpaEntity.java
    └── configuration/
        └── SkillConfiguration.java
```

### Otros Módulos (Resumen)

- **Contact**: Gestión de mensajes de contacto (`ContactController`, `CreateContactUseCase`).
- **Education**: Historial académico (`EducationController`, `EducationJpaEntity`).
- **Experience**: Experiencia laboral (`ExperienceController`, `ExperienceJpaEntity`).
- **ProficiencyLevel**: Catálogo de niveles (`ProficiencyLevelJpaEntity`).
- **Dashboard**: Agregador de métricas (`GetDashboardStatsUseCase`).
```

## Flujo de Dependencias

```
┌─────────────────────────────────────────────┐
│         Presentation Layer                   │
│         (Controllers)                        │
│  - AuthController                            │
│  - ProjectController                         │
│  - FileController                            │
└──────────────────┬──────────────────────────┘
                   │ depende de
                   ▼
┌─────────────────────────────────────────────┐
│         Application Layer                    │
│         (Use Cases, DTOs, Mappers)          │
│  - LoginUseCase                              │
│  - CreateProjectUseCase                      │
│  - GoogleLoginUseCase                        │
└──────────────────┬──────────────────────────┘
                   │ depende de
                   ▼
┌─────────────────────────────────────────────┐
│         Domain Layer                         │
│         (Entities, Ports/Interfaces)        │
│  - User, Project                             │
│  - UserRepository, ProjectRepository         │
│  - TokenProvider, FileStoragePort            │
└──────────────────▲──────────────────────────┘
                   │ implementa
                   │
┌─────────────────────────────────────────────┐
│         Infrastructure Layer                 │
│         (Adapters, Configuration)           │
│  - JpaUserRepository                         │
│  - JwtTokenProvider                          │
│  - FileSystemStorageAdapter                  │
│  - SecurityConfig                            │
└─────────────────────────────────────────────┘
```

**Principios clave**:
- **Presentation** → depende de → **Application**
- **Application** → depende de → **Domain**
- **Infrastructure** → implementa → **Domain** (ports)
- **Domain** → NO tiene dependencias externas

## Esquema de Base de Datos

### Tablas Principales

#### Autenticación y Autorización
- `users`: Información de usuarios
  - Campos OAuth: `auth_provider` (LOCAL/GOOGLE), `provider_id`
  - `password` es nullable para usuarios OAuth
- `roles`: Roles del sistema (ROLE_USER, ROLE_ADMIN)
- `user_roles`: Tabla de relación muchos-a-muchos

#### Contenido del Portafolio
- `projects`: Proyectos del portafolio
- `educations`: Historial educativo
- `experiences`: Experiencia laboral
- `skills`: Habilidades técnicas
- `project_skills`: Relación entre proyectos y habilidades

#### Catálogos
- `proficiency_levels`: Niveles de competencia (Básico, Intermedio, Avanzado, Experto)
- `project_statuses`: Estados de proyectos (En Progreso, Completado, Archivado)

#### Archivos
- `files`: Metadata de archivos cargados

### Migraciones

Las migraciones de base de datos se gestionan con **Flyway** y se encuentran en:
```
src/main/resources/db/migration/
├── V1__create_table_users.sql
├── V2__create_table_educations.sql
├── V3__create_table_files.sql
├── V4__add_logo_and_default_user.sql
├── V5__remove_logo_from_educations.sql
├── V6__enterprise_schema_refactor.sql
├── V7__complete_schema_implementation.sql
├── V8__default_admin_user.sql
└── V9__add_oauth_support.sql          # Soporte OAuth2
```

## Decisiones de Diseño Clave

### 1. Arquitectura Hexagonal
**Razón**: Mejor separación de responsabilidades, testeabilidad y flexibilidad para cambiar implementaciones sin afectar la lógica de negocio.

### 2. Use Cases
**Razón**: Cada operación de negocio está encapsulada en una clase dedicada, facilitando el mantenimiento y testing.

### 3. Ports & Adapters
**Razón**: El dominio define interfaces (ports), la infraestructura provee implementaciones (adapters). Esto permite cambiar fácilmente de base de datos, sistema de archivos, o proveedor de autenticación.

### 4. RBAC (Role-Based Access Control)
**Razón**: Control de acceso granular usando roles. Los endpoints de administración requieren `ROLE_ADMIN`.

### 5. JWT para Autenticación
**Razón**: Stateless authentication, escalable y compatible con arquitecturas de microservicios.

### 6. OAuth2 Support
**Razón**: Permite autenticación con proveedores externos (Google) mejorando la experiencia de usuario.

### 7. Separación Domain/JPA Entities
**Razón**: Las entidades de dominio son independientes de JPA. Las entidades JPA (`UserJpaEntity`) se mapean hacia/desde entidades de dominio (`User`), manteniendo el dominio limpio.

### 8. File Storage Abstraction
**Razón**: El almacenamiento de archivos está abstraído mediante `FileStoragePort`. Actualmente usa `FileSystemStorageAdapter`, pero puede cambiarse fácilmente a S3, Azure Blob, etc.

### 9. Configuration Classes
**Razón**: Los Spring Beans para Use Cases se definen en clases `*Configuration` en la capa de infraestructura, manteniendo la lógica de negocio independiente de Spring.

## Seguridad

### Autenticación
- **JWT**: Tokens firmados con HMAC-SHA256
- **BCrypt**: Hash de contraseñas con salt automático
- **OAuth2**: Verificación de tokens de Google usando `GoogleIdTokenVerifier`

### Autorización
- **Method Security**: Anotaciones `@PreAuthorize` en controllers
- **Role-based**: Endpoints protegidos por roles (ROLE_USER, ROLE_ADMIN)
- **Stateless Sessions**: No se mantiene estado de sesión en el servidor

### Configuración de Seguridad
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    // Endpoints públicos:
    // - /api/auth/** (login, register, google)
    // - /api/public/**
    // - /swagger-ui/**
    
    // Endpoints protegidos:
    // - /api/admin/** (requiere autenticación)
}
```

## Validación

### Validación de Entrada
- **Bean Validation**: Anotaciones `@Valid`, `@NotBlank`, `@Email`, `@Size`
- **Custom Validators**: `@ValidFile` para validación de archivos

### Manejo de Errores
- **GlobalExceptionHandler**: Maneja excepciones globalmente
- **Custom Exceptions**: `ResourceNotFoundException`, `FileStorageException`
- **Respuestas Estandarizadas**: Formato consistente de errores

## Testing

### Estrategia de Testing
1. **Unit Tests**: Testing de Use Cases con mocks de repositories
2. **Integration Tests**: Testing de adapters con base de datos en memoria
3. **Controller Tests**: Testing de endpoints con MockMvc

### Ventajas de Hexagonal para Testing
- Use Cases pueden testearse sin Spring
- Repositories pueden mockearse fácilmente
- Lógica de negocio aislada de infraestructura

## Beneficios de la Arquitectura

### 1. Testeabilidad
La lógica de negocio puede testearse sin dependencias externas.

### 2. Flexibilidad
Fácil cambiar implementaciones (ej: cambiar de MySQL a PostgreSQL, de filesystem a S3).

### 3. Mantenibilidad
Clara separación de responsabilidades facilita el mantenimiento.

### 4. Independencia
La lógica de dominio es independiente de frameworks.

### 5. Escalabilidad
Fácil agregar nuevas funcionalidades sin afectar código existente.

### 6. Evolución
Módulos pueden evolucionar independientemente.

## Containerización

### Docker
- **Dockerfile**: Multi-stage build (Maven build + JRE runtime)
- **Optimización**: Imagen final solo contiene JRE y JAR ejecutable
- **Volúmenes**: Persistencia de uploads

### Docker Compose
- **Servicios**: 
  - `app`: Aplicación Spring Boot
  - `db`: MySQL 8.0
- **Networking**: Red privada entre servicios
- **Persistencia**: Volumen para datos de MySQL
- **Variables de Entorno**: Configuración externalizada

## Próximos Pasos Arquitecturales

### Posibles Mejoras
1. **Event-Driven**: Implementar eventos de dominio
2. **CQRS**: Separar comandos de queries
3. **API Gateway**: Para múltiples microservicios
4. **Service Mesh**: Para comunicación entre servicios
5. **Cloud Storage**: Migrar de filesystem a S3/Azure Blob
6. **Caching**: Redis para mejorar performance
7. **Message Queue**: RabbitMQ/Kafka para procesamiento asíncrono
