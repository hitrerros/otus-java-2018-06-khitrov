DROP TABLE IF EXISTS ages;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE IF NOT EXISTS ages
(
  id          bigint PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR,
  age        INTEGER
);

