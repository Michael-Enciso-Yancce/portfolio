-- ============================================================================
-- PORTFOLIO DATABASE - INITIAL DATA
-- ============================================================================
-- This migration inserts all initial/seed data
-- Consolidates: V6, V7, V8, V10 from original migrations
-- ============================================================================

-- ============================================================================
-- ROLES
-- ============================================================================

INSERT INTO roles (name) VALUES 
    ('ROLE_USER'),
    ('ROLE_ADMIN');

-- ============================================================================
-- DEFAULT ADMIN USER
-- ============================================================================
-- Email: admin@portfolio.com
-- Password: admin123 (BCrypt hash)

INSERT INTO users (
    email,
    password,
    full_name,
    auth_provider,
    created_at,
    updated_at
) VALUES (
    'admin@portfolio.com',
    '$2a$10$tw/oaIxSX5PWQ98Klk2LzuC.RccdDmuyrD6yxoVV75csCnal6msPu',
    'Admin User',
    'LOCAL',
    NOW(),
    NOW()
);

-- Assign ROLE_ADMIN to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@portfolio.com'
  AND r.name = 'ROLE_ADMIN';

-- ============================================================================
-- PROFICIENCY LEVELS
-- ============================================================================

INSERT INTO proficiency_levels (name) VALUES 
    ('Beginner'),
    ('Intermediate'),
    ('Advanced'),
    ('Expert');

-- ============================================================================
-- PROJECT STATUSES
-- ============================================================================

INSERT INTO project_statuses (name) VALUES 
    ('In Progress'),
    ('Completed'),
    ('On Hold'),
    ('Maintenance');

-- ============================================================================
-- INITIAL SKILLS
-- ============================================================================

INSERT INTO skills (name) VALUES 
    ('Java'),
    ('Spring Boot'),
    ('MySQL'),
    ('React'),
    ('Docker');
