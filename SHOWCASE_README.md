# Portfolio Backend Showcase

## Project Information

**Name:** Portfolio Backend
**Description:** A comprehensive backend system for managing a professional portfolio. It handles users, projects, skills, education, experience, and contact messages. Built with robust architecture principles to ensure maintainability and scalability.
**Project URL:** http://localhost:8080 (Local)
**GitHub URL:** [Repository Link]
**Status:** In Development
**Dates:** 2024-12-01 - Present

### Skills & Technologies
-   **Java**: Core language (v17+).
-   **Spring Boot**: Framework for web, dependency injection, and security.
-   **PostgreSQL**: Relational database for persistence.
-   **Docker**: Containerization.
-   **JWT**: Stateless authentication.
-   **WebSocket**: Real-time updates.

---

## Showcase Content

### Problem
Professional portfolios often require dynamic content management that static sites cannot easily provide. The validation of skills, management of project history, and real-time interaction (like messaging or live updates) necessitate a robust backend solution that separates concerns and allows for flexible frontend integration.

### Demonstrates
-   **Hexagonal Architecture (Clean Architecture)**: Separation of Domain, Application (Use Cases), and Infrastructure layers.
-   **RESTful API Design**: Standardized resource management.
-   **Security**: Spring Security with JWT and role-based access control.
-   **Real-time Communication**: WebSocket integration for live updates.
-   **Database Migration**: Flyway for version control of the database schema.

### Architecture
The project follows a **Hexagonal Architecture**:
-   **Domain**: Contains business logic and entities (e.g., `Project`, `Skill`). Strictly POJOs.
-   **Application**: Contains Use Cases (e.g., `CreateProjectUseCase`) and DTOs. Orchestrates business flows.
-   **Infrastructure**: specific implementations (e.g., `JpaProjectRepository`, `ProjectController`, `SecurityConfig`).
-   **Shared**: Common utilities and exceptions.

**Diagram URL:** (Placeholder)

### Flow
1.  **Authentication**: User logs in -> JWT Token issued.
2.  **Project Management**: Admin creates/updates project -> Triggers WebSocket event -> Frontend updates in real-time.
3.  **Showcase View**: Public user accesses `/api/public/showcase/project/{id}` -> System retrieves the current active version of the showcase JSON.

### Decisions
-   **Hexagonal Architecture**: Chosen to decouple business logic from frameworks (Spring) and persistence details, facilitating testing and future changes.
-   **JSON for Showcase Content**: storing flexible content (like "Problem", "Architecture" details) as a JSON column allows for evolving the schema without complex table migrations.
-   **WebSocket Broadcasting**: Implemented to provide an "alive" feel to the admin dashboard and public view.

### Tests
-   **Types**: Unit Tests (Use Cases), Integration Tests (Controllers/Repositories).
-   **Coverage**: Focused on core business logic and critical paths.

### Future
-   **Cloud Storage**: Migrate file storage from local filesystem to AWS S3 / Cloudinary.
-   **CI/CD**: Automated deployment pipelines.
-   **Analytics**: Tracking views on projects.
