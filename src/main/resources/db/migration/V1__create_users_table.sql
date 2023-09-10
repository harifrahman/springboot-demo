-- V1__create_users_table.sql

CREATE TABLE IF NOT EXISTS users (
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    name                VARCHAR(100) NOT NULL,
    token               VARCHAR(100),
    token_expired_at    BIGINT,
    PRIMARY KEY (username),
    CONSTRAINT unique_token UNIQUE (token)
    );
