-- 1. Catalogs
CREATE TABLE skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE proficiency_levels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE project_statuses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. User Skills (Associative Entity)
CREATE TABLE user_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency_level_id BIGINT NOT NULL,
    CONSTRAINT fk_user_skills_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skills_skill FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skills_level FOREIGN KEY (proficiency_level_id) REFERENCES proficiency_levels (id) ON DELETE CASCADE
);

-- 3. Projects
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
    CONSTRAINT fk_projects_status FOREIGN KEY (status_id) REFERENCES project_statuses (id)
);

-- 4. Project Skills (Pivot Table)
CREATE TABLE project_skills (
    project_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, skill_id),
    CONSTRAINT fk_project_skills_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_skills_skill FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE
);

-- 5. Experiences
CREATE TABLE experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url VARCHAR(500),
    start_date DATE NOT NULL,
    end_date DATE,
    CONSTRAINT fk_experiences_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 6. Insert Default Data
INSERT INTO
    proficiency_levels (name)
VALUES ('Beginner'),
    ('Intermediate'),
    ('Advanced'),
    ('Expert');

INSERT INTO
    project_statuses (name)
VALUES ('In Progress'),
    ('Completed'),
    ('On Hold'),
    ('Maintenance');

INSERT INTO
    skills (name)
VALUES ('Java'),
    ('Spring Boot'),
    ('MySQL'),
    ('React'),
    ('Angular'),
    ('Docker'),
    ('AWS');