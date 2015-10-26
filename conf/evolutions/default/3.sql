# --- First database schema

# --- !Ups

CREATE TABLE RESERVATIONS (
  id                       integer NOT NULL PRIMARY KEY,
  name                      VARCHAR(55) NOT NULL,
  number                   VARCHAR(10) NOT NULL,
  status               VARCHAR(55) NOT NULL
  );

# --- !Downs


