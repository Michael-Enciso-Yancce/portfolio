CREATE TABLE IF NOT EXISTS educations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    institution VARCHAR(255),
    degree      VARCHAR(255),
    start_date  DATE NOT NULL,
    end_date    DATE NOT NULL
);