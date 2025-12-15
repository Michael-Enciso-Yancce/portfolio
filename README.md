# Portfolio Management System

Sistema integral de gestión de portafolio construido con Java Spring Boot, que incluye autenticación JWT, OAuth2, control de acceso basado en roles y una API REST completa.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Requisitos Previos](#-requisitos-previos)
- [Inicio Rápido con Docker](#-inicio-rápido-con-docker)
- [Instalación Manual](#-instalación-manual)
- [Configuración](#️-configuración)
- [Ejecutar la Aplicación](#-ejecutar-la-aplicación)
- [Documentación de la API](#-documentación-de-la-api)
- [Autenticación](#-autenticación)
- [Endpoints Principales](#-endpoints-principales)
- [Credenciales por Defecto](#-credenciales-por-defecto)
- [Arquitectura](#-arquitectura)

## ✨ Características

### Autenticación y Autorización
- ✅ Autenticación basada en JWT
- ✅ OAuth2 con Google Sign-In
- ✅ Control de Acceso Basado en Roles (RBAC)
- ✅ Registro e inicio de sesión tradicional
- ✅ Encriptación segura de contraseñas con BCrypt

### Gestión de Portafolio
- 📚 Gestión de historial educativo
- 💼 Portafolio de proyectos con etiquetado de habilidades
- 🏢 Seguimiento de experiencia profesional
- 📁 Soporte para carga de archivos (imágenes, logos)

### Gestión de Catálogos
- 🎯 Catálogo de habilidades técnicas
- 📊 Niveles de competencia
- 🏷️ Estados de proyectos

### Seguridad
- 🔒 Seguridad a nivel de método con `@PreAuthorize`
- 🛡️ Endpoints de administrador protegidos
- ✔️ Validación de tokens JWT
- 🌐 Soporte CORS configurado

## 📦 Requisitos Previos

### Opción 1: Con Docker (Recomendado)
- Docker Desktop 20.10+
- Docker Compose 2.0+

### Opción 2: Instalación Manual
- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Git

## 🐳 Inicio Rápido con Docker

La forma más rápida de ejecutar el proyecto es usando Docker Compose:

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd portafolio
```

### 2. Configurar Google OAuth2 (Opcional)
Si deseas habilitar el login con Google, edita `docker-compose.yml`:

```yaml
environment:
  - SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=tu-client-id-aqui
```

### 3. Iniciar los Servicios
```bash
docker-compose up --build
```

Esto iniciará:
- **Aplicación Backend**: `http://localhost:8080`
- **Base de Datos MySQL**: Puerto 3307 (para evitar conflictos con MySQL local)

### 4. Verificar el Estado
```bash
# Ver logs
docker-compose logs -f app

# Verificar servicios en ejecución
docker-compose ps
```

### 5. Detener los Servicios
```bash
docker-compose down

# Para eliminar también los volúmenes (datos)
docker-compose down -v
```

## 🔧 Instalación Manual

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd portafolio
```

### 2. Crear Base de Datos MySQL
```sql
CREATE DATABASE portfolio;
```

### 3. Configurar Propiedades
Crea `src/main/resources/application.properties` basándote en `application.properties.example`:

```properties
# Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/portfolio?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=validate

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# JWT
jwt.secret=tu-clave-secreta-muy-larga-y-segura-de-al-menos-256-bits
jwt.expiration=86400000

# Google OAuth2 (opcional)
spring.security.oauth2.client.registration.google.client-id=tu-client-id

# Archivos
file.upload-dir=./uploads
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Servidor
server.port=8080
```

### 4. Construir el Proyecto
```bash
mvn clean install
```

## ⚙️ Configuración

### Variables de Entorno

Puedes configurar la aplicación usando variables de entorno:

```bash
# Base de datos
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/portfolio
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=password

# JWT
export JWT_SECRET=tu-clave-secreta
export JWT_EXPIRATION=86400000

# Google OAuth2
export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=tu-client-id

# Archivos
export FILE_UPLOAD_DIR=./uploads
```

### Configuración de Google OAuth2

Para habilitar el login con Google:

1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita la API de Google+ 
4. Crea credenciales OAuth 2.0
5. Configura las URIs de redirección autorizadas
6. Copia el Client ID y configúralo en tu aplicación

## 🏃 Ejecutar la Aplicación

### Con Docker Compose
```bash
docker-compose up
```

### Con Maven
```bash
mvn spring-boot:run
```

### Con Java (JAR)
```bash
# Primero construir el JAR
mvn clean package -DskipTests

# Luego ejecutar
java -jar target/michael-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: **http://localhost:8080**

## 📚 Documentación de la API

### Swagger UI (Recomendado)

Accede a la documentación interactiva en:

```
http://localhost:8080/swagger-ui.html
```

Swagger proporciona:
- 📖 Documentación de todos los endpoints
- 🧪 Capacidad de probar endpoints directamente
- 📝 Esquemas de request/response
- ⚠️ Códigos de estado y mensajes de error

### OpenAPI JSON

El esquema OpenAPI está disponible en:
```
http://localhost:8080/v3/api-docs
```

## 🔐 Autenticación

### Métodos de Autenticación

#### 1. Registro Tradicional
```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 2. Login Tradicional
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "juan@example.com",
  "password": "password123"
}
```

#### 3. Login con Google OAuth2
El backend maneja el flujo completo de OAuth2 con Google.

**Iniciar Login:**
```http
GET /api/auth/google
```

**Flujo completo:**
1. Usuario navega a `/api/auth/google`
2. Backend redirige a Google para autenticación
3. Google redirige de vuelta al backend con un código
4. Backend procesa el código y redirige al frontend con el token en la URL: `/#/oauth/callback?token=JWT`
5. **Frontend usa el token para obtener info del usuario:**

**Obtener Usuario Actual (Nuevo):**
Una vez que el frontend tiene el token, debe llamar a este endpoint para saber quién es el usuario y sus roles.

```http
GET /api/auth/me
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "token": null,
  "fullName": "Michael Enciso",
  "email": "user@example.com",
  "roles": ["ROLE_ADMIN"],
  "profileImageUrl": "https://lh3.googleusercontent.com/..."
}
```

**Ejemplo de uso desde el frontend:**
```javascript
// 1. Redirigir a Google
window.location.href = '/api/auth/google';

// 2. En la página de callback, extraer token de la URL
const urlParams = new URLSearchParams(window.location.hash.split('?')[1]);
const token = urlParams.get('token');

// 3. Obtener datos del usuario
fetch('/api/auth/me', {
  headers: { 'Authorization': `Bearer ${token}` }
})
.then(res => res.json())
.then(user => {
  console.log("Rol:", user.roles); // ["ROLE_ADMIN"] o ["ROLE_USER"]
  if (user.roles.includes('ROLE_ADMIN')) {
    showAdminPanel();
  }
});
```

### Usar el Token JWT

Incluye el token en el header `Authorization` de todas las peticiones protegidas:

```http
GET /api/admin/projects
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Ejemplo con JavaScript:**
```javascript
const token = localStorage.getItem('token');

fetch('/api/admin/projects', {
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
})
.then(response => response.json())
.then(data => console.log(data));
```

## 🌐 Endpoints Principales

### Autenticación (`/api/auth`)

| Método | Endpoint   | Descripción                   | Público |
|--------|------------|-------------------------------|---------|
| POST   | `/register`| Registro de usuario           | ✅     |
| POST   | `/login`   | Login tradicional             | ✅     |
| GET    | `/google`  | Login con Google (Server-Side)| ✅     |
| GET    | `/me`      | Obtener usuario autenticado   | 🔒 Auth|

### Proyectos Admin (`/api/admin/projects`)

| Método | Endpoint | Descripción                   | Requiere Auth |
|--------|----------|-------------------------------|---------------|
| GET    | `/`      | Listar todos los proyectos    | 🔒 Auth      |
| POST   | `/`      | Crear proyecto                | 🔒 ADMIN     |
| PUT    | `/{id}`  | Actualizar proyecto           | 🔒 ADMIN     |
| DELETE | `/{id}`  | Eliminar proyecto             | 🔒 ADMIN     |

### Educación (`/api/admin/educations`)

| Método | Endpoint | Descripción                   | Requiere Auth |
|--------|----------|-------------------------------|---------------|
| GET    | `/`      | Listar educación              | ✅            |
| POST   | `/`      | Crear registro                | ✅ ADMIN      |
| PUT    | `/{id}`  | Actualizar registro           | ✅ ADMIN      |
| DELETE | `/{id}`  | Eliminar registro             | ✅ ADMIN      |

### Experiencia (`/api/admin/experiences`)

| Método | Endpoint | Descripción                   | Requiere Auth |
|--------|----------|-------------------------------|---------------|
| GET    | `/`      | Listar experiencias           | ✅            |
| POST   | `/`      | Crear experiencia             | ✅ ADMIN      |
| PUT    | `/{id}`  | Actualizar experiencia        | ✅ ADMIN      |
| DELETE | `/{id}`  | Eliminar experiencia          | ✅ ADMIN      |

### Catálogos (`/api/admin/catalogs`)

| Método | Endpoint             | Descripción                   | Requiere Auth |
|--------|----------------------|-------------------------------|---------------|
| GET    | `/skills`            | Listar habilidades            | ✅            |
| GET    | `/proficiency-levels`| Niveles de competencia        | ✅            |
| GET    | `/project-statuses`  | Estados de proyectos          | ✅            |
| POST   | `/skills`            | Crear habilidad               | ✅ ADMIN      |

### Archivos (`/api/admin/files`)

| Método | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| POST | `/upload` | Subir archivo | ✅ ADMIN |
| GET | `/{filename}` | Descargar archivo | ✅ |

**Ejemplo de Upload:**
```javascript
const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('/api/admin/files/upload', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`
  },
  body: formData
})
.then(response => response.json())
.then(data => console.log('File URL:', data.url));
```

### Endpoints Públicos (`/api/public`)

Estos endpoints están diseñados para ser consumidos por el frontend público (portafolio) sin necesidad de autenticación.

#### 1. Perfil Público
**Obtener información del perfil principal (Admin User).**

- **Endpoint:** `GET /api/public/profile`
- **Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Profile retrieved",
  "data": {
    "fullName": "Admin User",
    "title": "Full Stack Developer",
    "description": "Desarrollador apasionado con experiencia en...",
    "profileImageUrl": "/uploads/profile.jpg",
    "email": "admin@portfolio.com"
  }
}
```

#### 2. Proyectos Públicos
**Listar todos los proyectos visibles en el portafolio.**

- **Endpoint:** `GET /api/public/projects`
- **Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Projects retrieved",
  "data": [
    {
      "id": 1,
      "name": "E-commerce Platform",
      "description": "Plataforma de comercio electrónico construida con Spring Boot y React.",
      "imageUrl": "/uploads/project1.png",
      "projectUrl": "https://mi-ecommerce.com",
      "githubUrl": "https://github.com/admin/ecommerce",
      "startDate": "2023-01-15",
      "endDate": "2023-06-20",
      "status": "Completado",
      "skills": [
        { "id": 1, "name": "Java" },
        { "id": 2, "name": "React" }
      ]
    }
  ]
}
```

#### 3. Educación
**Listar historial académico.**

- **Endpoint:** `GET /api/public/educations`
- **Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Educations retrieved",
  "data": [
    {
      "id": 1,
      "institution": "Universidad Tecnológica",
      "degree": "Ingeniería de Software",
      "startDate": "2018-03-01",
      "endDate": "2022-12-15",
      "logoUrl": "/uploads/university_logo.png"
    }
  ]
}
```

#### 4. Experiencia
**Listar experiencia laboral.**

- **Endpoint:** `GET /api/public/experiences`
- **Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Experiences retrieved",
  "data": [
    {
      "id": 1,
      "companyName": "Tech Solutions Inc.",
      "role": "Backend Developer",
      "description": "Desarrollo de APIs RESTful...",
      "startDate": "2023-01-10",
      "endDate": null, // null indica "Presente" (trabajo actual)
      "logoUrl": "/uploads/company_logo.png"
    }
  ]
}
```

**Ejemplo de consumo (JavaScript):**
```javascript
// Función helper para obtener datos
async function fetchData(endpoint) {
  try {
    const response = await fetch(`/api/public${endpoint}`);
    const result = await response.json();
    return result.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
}

// Uso
const profile = await fetchData('/profile');
const projects = await fetchData('/projects');
console.log(profile, projects);
```


## 🔑 Credenciales por Defecto

Para acceder al sistema con privilegios de administrador:

- **Email:** `admin@portfolio.com`
- **Contraseña:** `admin123`

> ⚠️ **IMPORTANTE**: Cambia estas credenciales en producción inmediatamente.

## 📁 Estructura del Proyecto

```
portafolio/
├── src/main/java/com/portfolio/michael/
│   ├── modules/
│   │   ├── auth/           # Autenticación y autorización
│   │   ├── project/        # Gestión de proyectos
│   │   ├── education/      # Historial educativo
│   │   ├── experience/     # Experiencia laboral
│   │   ├── catalog/        # Catálogos (skills, niveles)
│   │   └── file/           # Gestión de archivos
│   ├── shared/             # Componentes compartidos
│   └── validation/         # Validadores personalizados
├── src/main/resources/
│   ├── db/migration/       # Migraciones Flyway
│   └── application.properties
├── uploads/                # Archivos subidos
├── Dockerfile              # Configuración Docker
├── docker-compose.yml      # Orquestación de servicios
├── pom.xml                 # Dependencias Maven
├── README.md               # Este archivo
└── ARCHITECTURE.md         # Documentación de arquitectura
```

## 🏗️ Arquitectura

Este proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)** para asegurar:
- ✅ Separación de responsabilidades
- ✅ Testeabilidad
- ✅ Mantenibilidad
- ✅ Independencia de frameworks

Para más detalles sobre la arquitectura, consulta [ARCHITECTURE.md](./ARCHITECTURE.md).

## 🔒 Notas de Seguridad

### Producción
1. **JWT Secret**: Usa una clave secreta fuerte y aleatoria (mínimo 256 bits)
2. **Credenciales DB**: Nunca confirmes credenciales en el control de versiones
3. **HTTPS**: Siempre usa HTTPS en producción
4. **CORS**: Configura orígenes permitidos específicos
5. **Rate Limiting**: Implementa limitación de tasa para endpoints de autenticación
6. **Credenciales Admin**: Cambia las credenciales por defecto

### Desarrollo
- Los endpoints `/swagger-ui/**` y `/v3/api-docs/**` están públicos por defecto
- Considera restringirlos en producción

## 🐛 Troubleshooting

### Error: "no main manifest attribute"
**Solución**: Asegúrate de que `spring-boot-maven-plugin` está en el `pom.xml`:
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

### Error: "Access denied for user"
**Solución**: Verifica las credenciales de MySQL en `application.properties` o variables de entorno.

### Error: "Port 8080 already in use"
**Solución**: Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Docker: "Connection refused" a MySQL
**Solución**: Asegúrate de que el servicio `db` esté completamente iniciado antes que `app`. Docker Compose debería manejar esto automáticamente con `depends_on`.

## 📝 Notas Adicionales

- Todos los endpoints de administrador requieren autenticación con token JWT válido
- El token debe incluirse en el header `Authorization` como `Bearer {token}`
- Las cargas de archivos están limitadas a 10MB por defecto
- Los archivos se almacenan en el directorio `uploads` (o volumen Docker)
- Los nombres de archivos se generan con UUID para evitar conflictos
- Las migraciones de base de datos se ejecutan automáticamente al iniciar

## 🤝 Contribuir

1. Fork el repositorio
2. Crea una rama de feature (`git checkout -b feature/nueva-caracteristica`)
3. Commit tus cambios (`git commit -m 'Agregar nueva característica'`)
4. Push a la rama (`git push origin feature/nueva-caracteristica`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT.

## 📧 Contacto

Para preguntas o soporte, contacta al equipo de desarrollo.