-- Create users/roles database. 1. UML системы [#62660]

CREATE DATABASE job4j;
CREATE USER postgres WITH password 'postgres';
GRANT ALL privileges ON DATABASE job4j TO postgres;
