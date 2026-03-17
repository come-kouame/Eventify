CREATE DATABASE project_db;
\c project_db

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL
);

INSERT INTO messages (content)
VALUES ('Hello World');