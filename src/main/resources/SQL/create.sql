CREATE TABLE Users (
user_id SERIAL Primary Key,
user_username STRING NOT NULL,
user_password STRING NOT NULL UNIQUE,
user_role STRING NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
