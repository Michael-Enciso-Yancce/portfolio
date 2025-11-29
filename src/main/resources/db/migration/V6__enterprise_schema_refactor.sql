-- 1. Drop files table (no longer needed)
DROP TABLE IF EXISTS files;

-- 2. Create roles table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Create user_roles table (Many-to-Many)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- 4. Alter users table
-- Add new fields
ALTER TABLE users ADD COLUMN full_name VARCHAR(255) AFTER id;

ALTER TABLE users ADD COLUMN title VARCHAR(100) AFTER full_name;

ALTER TABLE users ADD COLUMN description TEXT AFTER title;

ALTER TABLE users
ADD COLUMN profile_image_url VARCHAR(500) AFTER description;

-- Migrate data (simple concatenation for full_name)
UPDATE users SET full_name = CONCAT(name, ' ', last_name);

-- Remove old fields
ALTER TABLE users DROP COLUMN name;

ALTER TABLE users DROP COLUMN last_name;

-- 5. Alter educations table
ALTER TABLE educations ADD COLUMN user_id BIGINT AFTER id;

ALTER TABLE educations ADD COLUMN logo_url VARCHAR(500) AFTER degree;

-- Assign default user to existing educations (assuming user with ID 1 exists, otherwise this might fail or need handling)
-- For safety, we can try to update with the first user found or leave null if allowed (but usually FKs should be valid)
-- Let's assume there is at least one user or we set it to NULL for now if we don't enforce NOT NULL immediately.
-- However, strict schema usually requires NOT NULL for ownership.
-- Let's try to set it to 1.
UPDATE educations SET user_id = ( SELECT id FROM users LIMIT 1 );

-- Now enforce FK
ALTER TABLE educations
ADD CONSTRAINT fk_educations_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

-- Insert default roles
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Assign ROLE_ADMIN to the first user (usually the default admin)
INSERT INTO
    user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
    JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE
    u.id = (
        SELECT id
        FROM users
        LIMIT 1
    );