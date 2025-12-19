-- ============================================================================
-- PORTFOLIO DATABASE - SEED DATA
-- ============================================================================

-- ROLES
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- DEFAULT ADMIN (admin@portfolio.com / admin123)
INSERT INTO users (email, password, full_name, auth_provider, created_at, updated_at) 
VALUES ('admin@portfolio.com', '$2a$10$tw/oaIxSX5PWQ98Klk2LzuC.RccdDmuyrD6yxoVV75csCnal6msPu', 'Admin User', 'LOCAL', NOW(), NOW());

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'admin@portfolio.com' AND r.name = 'ROLE_ADMIN';

-- PROFICIENCY LEVELS
INSERT INTO proficiency_levels (name) VALUES ('Principiante'), ('Intermedio'), ('Avanzado'), ('Experto');

-- PROJECT STATUSES
INSERT INTO project_statuses (name) VALUES ('En curso'), ('Completado'), ('En espera'), ('Mantenimiento');

-- INITIAL SKILLS
INSERT INTO skills (name, category) VALUES 
('Java', 'BACKEND'), 
('Spring Boot', 'BACKEND'), 
('MySQL', 'DATABASE'), 
('React', 'FRONTEND'), 
('Docker', 'TOOLS');
