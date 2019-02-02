DROP DATABASE IF EXISTS springboot_simple;
CREATE DATABASE springboot_simple;
USE springboot_simple;

DROP TABLE IF EXISTS people;
CREATE TABLE people  (
    person_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);
