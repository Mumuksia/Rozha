# --- First database schema

# --- !Ups

set ignorecase true;

CREATE TABLE reservation (
  id                       integer NOT NULL PRIMARY KEY,
  name                      VARCHAR(55) NOT NULL,
  number                   VARCHAR(10) NOT NULL,
  status               VARCHAR(55) NOT NULL
  );

# --- !Downs

drop table if exists reservation;
