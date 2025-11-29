# Project Architecture

## Overview
This project is a portfolio management system built with Java Spring Boot. It follows a standard layered architecture to separate concerns and ensure maintainability.

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

## Architecture Layers

### 1. Controller Layer (`com.portfolio.michael.controller`)
- Handles incoming HTTP requests.
- Validates input using Jakarta Validation.
- Delegates business logic to the Service layer.
- Returns DTOs wrapped in `ApiResponse`.

### 2. Service Layer (`com.portfolio.michael.service`)
- Contains business logic.
- Transaction management.
- Maps Entities to DTOs and vice versa.
- Interacts with the Repository layer.

### 3. Repository Layer (`com.portfolio.michael.repository`)
- Interfaces extending `JpaRepository`.
- Handles data access and persistence.

### 4. Entity Layer (`com.portfolio.michael.entity`)
- JPA Entities representing database tables.
- Mapped to the database schema.

### 5. DTO Layer (`com.portfolio.michael.dto`)
- Data Transfer Objects for API requests and responses.
- Decouples the internal domain model from the external API.

### 6. Security (`com.portfolio.michael.security`)
- JWT Authentication filter.
- Custom UserDetailsService.
- Security Configuration (SecurityFilterChain).

### 7. Exception Handling (`com.portfolio.michael.exception`)
- Global Exception Handler using `@RestControllerAdvice`.
- Custom exceptions.

## Database Schema
The database schema is managed via Flyway migrations located in `src/main/resources/db/migration`.
Key tables include:
- `users`, `roles`, `user_roles`: Authentication and Authorization.
- `projects`, `skills`, `experiences`: Portfolio content.
- `catalogs`: Lookup tables (e.g., `project_statuses`, `proficiency_levels`).

## Key Design Decisions
- **RBAC**: Role-Based Access Control is implemented to secure admin endpoints (`ROLE_ADMIN`).
- **File Storage**: A helper service `FileStorageService` manages file uploads (images, logos).
- **MapStruct**: Used for performance and type safety in object mapping.
