CREATE DATABASE postgres2;

\c postgres2;

CREATE TABLE user_table
(
    ldap_id    SERIAL PRIMARY KEY,
    ldap_login      VARCHAR(255),
    ldap_name VARCHAR(255),
    ldap_surname  VARCHAR(255)
);

INSERT INTO user_table (ldap_login, ldap_name, ldap_surname)
VALUES ('user3', 'John', 'Doe'),
       ('user4', 'Jane', 'Smith');
