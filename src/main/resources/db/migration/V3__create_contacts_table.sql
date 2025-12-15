-- ============================================================================
-- PORTFOLIO DATABASE - CONTACTS TABLE
-- ============================================================================
-- This migration creates the contacts table for portfolio contact form
-- Consolidates: V14 from original migrations
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
