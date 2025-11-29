-- Insert Default Admin User
-- Password is 'admin123' (BCrypt encoded)
INSERT INTO
    users (
        email,
        password,
        full_name,
        created_at,
        updated_at
    )
SELECT 'admin@portfolio.com', '$2a$10$r.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ.zZ', 'Admin User', NOW(), NOW()
WHERE
    NOT EXISTS (
        SELECT 1
        FROM users
        WHERE
            email = 'admin@portfolio.com'
    );

-- Assign ROLE_ADMIN to the default user
INSERT INTO
    user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE
    u.email = 'admin@portfolio.com'
    AND r.name = 'ROLE_ADMIN'
    AND NOT EXISTS (
        SELECT 1
        FROM user_roles ur
        WHERE
            ur.user_id = u.id
            AND ur.role_id = r.id
    );