# Project Architecture

## Overview
This project is a portfolio management system built with Java Spring Boot. It follows **Hexagonal Architecture (Ports & Adapters)** for most modules to ensure maintainability, testability, and separation of concerns.

## Technologies
- **Java 17**: Core programming language.
- **Spring Boot 3**: Framework for building the application.
- **Spring Security**: For authentication and authorization (JWT & RBAC).
- **Spring Data JPA**: For database interactions.
- **PostgreSQL**: Relational database management system.
- **Flyway**: Database migration tool.
- **MapStruct**: For entity-DTO mapping.
- **Lombok**: To reduce boilerplate code.
- **Maven**: Build automation tool.

## Architecture Pattern: Hexagonal Architecture

The project implements **Hexagonal Architecture** (also known as Ports and Adapters) for the following modules:
- `project`
- `education`
- `experience`
- `catalog`
- `file`

### Hexagonal Architecture Layers

#### 1. Domain Layer (`domain/`)
- **Purpose**: Contains the core business logic and domain models
- **Components**:
  - **Domain Entities**: Pure business objects (e.g., `Project`, `EducationEntity`, `ExperienceEntity`)
  - **Ports (Interfaces)**: Define contracts for external dependencies (e.g., `ProjectRepository`, `FileStoragePort`)
- **Rules**:
  - No dependencies on external frameworks
  - Contains only business logic
  - Defines interfaces (ports) for external interactions

#### 2. Application Layer (`application/`)
- **Purpose**: Orchestrates business logic through Use Cases
- **Components**:
  - **Use Cases**: Implement specific business operations (e.g., `CreateProjectUseCase`, `GetFileUseCase`)
  - **DTOs**: Data Transfer Objects for request/response (e.g., `CreateProjectRequest`, `ProjectResponse`)
  - **Mappers**: Convert between domain entities and DTOs (e.g., `ProjectMapper`)
- **Rules**:
  - Depends on Domain layer
  - No direct dependency on Infrastructure layer
  - Uses ports (interfaces) to interact with external systems

#### 3. Infrastructure Layer (`infrastructure/`)
- **Purpose**: Implements technical details and external integrations
- **Components**:
  - **Adapters**: Implement ports defined in Domain layer
    - **Persistence Adapters**: Database implementations (e.g., `JpaProjectRepository`)
    - **Storage Adapters**: File system implementations (e.g., `FileSystemStorageAdapter`)
  - **Configuration**: Spring Bean definitions (e.g., `ProjectConfiguration`)
- **Rules**:
  - Implements interfaces from Domain layer
  - Contains framework-specific code
  - Handles external system integrations

#### 4. Presentation Layer (`controller/`)
- **Purpose**: Exposes REST API endpoints
- **Components**:
  - **Controllers**: Handle HTTP requests and responses (e.g., `ProjectController`, `FileController`)
- **Rules**:
  - Depends on Application layer (Use Cases)
  - Handles HTTP-specific concerns (validation, status codes, etc.)
  - Delegates business logic to Use Cases

## Module Structure Example: Project Module

```
project/
├── controller/                    # Presentation Layer
│   └── ProjectController.java    # REST API endpoints
├── application/                   # Application Layer
│   ├── dto/
│   │   ├── CreateProjectRequest.java
│   │   └── ProjectResponse.java
│   ├── mapper/
│   │   └── ProjectMapper.java
│   └── usecase/
│       └── CreateProjectUseCase.java
├── domain/                        # Domain Layer
│   ├── Project.java              # Domain Entity
│   └── ProjectRepository.java    # Port (Interface)
└── infrastructure/                # Infrastructure Layer
    ├── configuration/
    │   └── ProjectConfiguration.java  # Bean definitions
    └── persistence/
        ├── ProjectEntity.java         # JPA Entity
        ├── JpaProjectRepository.java  # Adapter implementation
        └── SpringDataProjectRepository.java  # Spring Data interface
```

## Benefits of Hexagonal Architecture

1. **Testability**: Business logic can be tested without external dependencies
2. **Flexibility**: Easy to swap implementations (e.g., change database, file storage)
3. **Maintainability**: Clear separation of concerns
4. **Independence**: Domain logic is independent of frameworks
5. **Scalability**: Easy to add new features without affecting existing code

## Legacy Modules

The `auth` module still follows a traditional layered architecture:
- **Controller Layer**: Handles HTTP requests
- **Service Layer**: Contains business logic
- **Repository Layer**: Data access
- **Entity Layer**: JPA entities

## Database Schema
The database schema is managed via Flyway migrations located in `src/main/resources/db/migration`.
Key tables include:
- `users`, `roles`, `user_roles`: Authentication and Authorization.
- `projects`, `skills`, `experiences`, `educations`: Portfolio content.
- `catalogs`: Lookup tables (e.g., `project_statuses`, `proficiency_levels`).

## Key Design Decisions

1. **Hexagonal Architecture**: Adopted for better separation of concerns and testability
2. **Use Cases**: Each business operation is encapsulated in a dedicated Use Case class
3. **Ports & Adapters**: Domain defines interfaces (ports), Infrastructure provides implementations (adapters)
4. **RBAC**: Role-Based Access Control is implemented to secure admin endpoints (`ROLE_ADMIN`)
5. **File Storage**: Abstracted through `FileStoragePort` interface with `FileSystemStorageAdapter` implementation
6. **Configuration**: Spring Beans for Use Cases are defined in `*Configuration` classes in the Infrastructure layer

## Dependency Flow

```
Presentation (Controllers)
    ↓
Application (Use Cases, DTOs, Mappers)
    ↓
Domain (Entities, Ports/Interfaces)
    ↑
Infrastructure (Adapters, Configuration)
```

- **Presentation** depends on **Application**
- **Application** depends on **Domain**
- **Infrastructure** implements **Domain** interfaces
- **Domain** has no dependencies on other layers
