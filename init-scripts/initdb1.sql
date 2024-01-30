CREATE DATABASE postgres1;

\c postgres1;

CREATE TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    login      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

INSERT INTO users (login, first_name, last_name)
VALUES ('user1', 'John', 'Doe'),
       ('user2', 'Jane', 'Smith');
