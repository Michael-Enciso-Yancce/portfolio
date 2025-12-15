-- ============================================================================
-- PORTFOLIO DATABASE - INITIAL SCHEMA
-- ============================================================================
-- This migration creates all tables in their final state
-- Consolidates: V1-V9, V12-V13 from original migrations
-- ============================================================================

-- ============================================================================
-- USERS AND AUTHENTICATION
-- ============================================================================

-- Users table with OAuth support
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    title VARCHAR(100),
    description TEXT,
    profile_image_url VARCHAR(500),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,
    auth_provider VARCHAR(20) DEFAULT 'LOCAL',
    provider_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    INDEX idx_users_email (email),
    INDEX idx_users_provider (auth_provider, provider_id)
);

-- Roles table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- User-Roles relationship (Many-to-Many)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- ============================================================================
-- EDUCATION
-- ============================================================================

CREATE TABLE educations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    institution VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    logo_url VARCHAR(500),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CONSTRAINT fk_educations_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    INDEX idx_educations_user (user_id)
);

-- ============================================================================
-- EXPERIENCE
-- ============================================================================

CREATE TABLE experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url VARCHAR(500),
    start_date DATE NOT NULL,
    end_date DATE,
    CONSTRAINT fk_experiences_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    INDEX idx_experiences_user (user_id)
);

-- ============================================================================
-- SKILLS SYSTEM
-- ============================================================================

-- Skills catalog with categories and images
CREATE TABLE skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    image_url VARCHAR(500) COMMENT 'URL de la imagen/icono de la habilidad para mostrar en el portafolio',
    category VARCHAR(50) COMMENT 'Categoría de la habilidad: FRONTEND, BACKEND, TOOLS, DATABASE, OTHER',
    INDEX idx_skills_category (category)
);

-- Proficiency levels catalog
CREATE TABLE proficiency_levels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- User skills with proficiency level
CREATE TABLE user_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency_level_id BIGINT NOT NULL,
    CONSTRAINT fk_user_skills_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skills_skill FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skills_level FOREIGN KEY (proficiency_level_id) REFERENCES proficiency_levels (id) ON DELETE CASCADE,
    INDEX idx_user_skills_user (user_id)
);

-- ============================================================================
-- PROJECTS SYSTEM
-- ============================================================================

-- Project statuses catalog
CREATE TABLE project_statuses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Projects table
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    project_url VARCHAR(500),
    github_url VARCHAR(500),
    image_url VARCHAR(500),
    start_date DATE,
    end_date DATE,
    CONSTRAINT fk_projects_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_projects_status FOREIGN KEY (status_id) REFERENCES project_statuses (id),
    INDEX idx_projects_user (user_id),
    INDEX idx_projects_status (status_id)
);

-- Project-Skills relationship (Many-to-Many)
CREATE TABLE project_skills (
    project_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, skill_id),
    CONSTRAINT fk_project_skills_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_skills_skill FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE
);
