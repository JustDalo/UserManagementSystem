CREATE TABLE IF NOT EXISTS users (
    usr_id SERIAL NOT NULL PRIMARY KEY,
    usr_first_name VARCHAR,
    usr_last_name VARCHAR,
    usr_role INTEGER DEFAULT 1,
    usr_image BYTEA
);