# --- First

# --- !Ups

CREATE TABLE RESERVATIONS (
  id                       serial PRIMARY KEY,
  name                      VARCHAR(55) NOT NULL,
  number                   VARCHAR(10) NOT NULL,
  status               VARCHAR(55) NOT NULL
);
INSERT INTO account (id, name, email, password, role) VALUES (1, 'Yurko', 'yurko@gp.se', 'iverson', 'Administrator');
INSERT INTO account (id, name, email, password, role) VALUES (1, 'Rozha', 'rozha@clan.ua', 'rozha23', 'NormalUser');

# --- !Downs
