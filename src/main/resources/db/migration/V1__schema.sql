-- ============================================================================
-- PORTFOLIO DATABASE - INITIAL SCHEMA (CONSOLIDATED)
-- ============================================================================

-- ============================================================================
-- 1. USERS & AUTH
-- ============================================================================
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

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- ============================================================================
-- 2. EDUCATION & EXPERIENCE
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
-- 3. SKILL SYSTEM
-- ============================================================================
CREATE TABLE skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    image_url VARCHAR(500),
    category VARCHAR(50),
    INDEX idx_skills_category (category)
);

CREATE TABLE proficiency_levels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

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
-- 4. PROJECTS SYSTEM & SHOWCASE
-- ============================================================================
CREATE TABLE project_statuses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

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

CREATE TABLE project_skills (
    project_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, skill_id),
    CONSTRAINT fk_project_skills_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_skills_skill FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE
);

CREATE TABLE project_showcase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    version INT NOT NULL,
    content JSON NOT NULL,
    is_current BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_project_showcase_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- ============================================================================
-- 5. CONTACTS
-- ============================================================================
CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_contacts_status (status),
    INDEX idx_contacts_created_at (created_at)
);
