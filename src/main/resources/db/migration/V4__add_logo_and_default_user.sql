ALTER TABLE educations ADD COLUMN logo VARCHAR(255) AFTER degree;

-- Password is 'password' (BCrypt hash)
INSERT INTO users (name, last_name, email, password)
SELECT 'Admin', 'User', 'admin@miapp.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlZ6.kd.b5.O.S'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@miapp.com');
