DROP DATABASE IF EXISTS springboot_simple;
CREATE DATABASE springboot_simple;
USE springboot_simple;

DROP TABLE IF EXISTS people;
CREATE TABLE people  (
  person_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255)
);

DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;
CREATE TABLE BATCH_JOB_INSTANCE (
  JOB_INSTANCE_ID BIGINT NOT NULL PRIMARY KEY,
  JOB_NAME VARCHAR(255),
  JOB_KEY VARCHAR(255),
  VERSION VARCHAR(255)
);

DROP TABLE IF EXISTS batch_job_seq;
CREATE TABLE batch_job_seq (
  next_val BIGINT,
  id BIGINT NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS batch_job_execution_seq;
CREATE TABLE batch_job_execution_seq (
  next_val BIGINT,
  id BIGINT NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
CREATE TABLE BATCH_JOB_EXECUTION(
  JOB_EXECUTION_ID BIGINT,
  JOB_INSTANCE_ID BIGINT,
  START_TIME DATETIME,
  END_TIME DATETIME,
  STATUS VARCHAR(255),
  EXIT_CODE VARCHAR(255),
  EXIT_MESSAGE TEXT,
  VERSION VARCHAR(255),
  CREATE_TIME DATETIME,
  LAST_UPDATED DATETIME,
  JOB_CONFIGURATION_LOCATION VARCHAR(255)
);

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS(
  JOB_EXECUTION_ID BIGINT,
  KEY_NAME VARCHAR(255),
  TYPE_CD VARCHAR(255),
  STRING_VAL VARCHAR(255),
  DATE_VAL DATETIME,
  LONG_VAL BIGINT,
  DOUBLE_VAL DOUBLE,
  IDENTIFYING VARCHAR(255)
);

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT(
  SHORT_CONTEXT VARCHAR(255),
  SERIALIZED_CONTEXT VARCHAR(255),
  JOB_EXECUTION_ID BIGINT
);

DROP TABLE IF EXISTS hibernate_sequence;
CREATE TABLE hibernate_sequence(
  next_val BIGINT,
  id BIGINT NOT NULL PRIMARY KEY
);
INSERT INTO hibernate_sequence( next_val, id ) VALUES ( 1, 1 );
