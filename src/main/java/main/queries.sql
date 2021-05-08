
--1st table for creating a username
CREATE TABLE IF NOT EXISTS username (
id_username INTEGER AUTO_INCREMENT PRIMARY KEY,
username TEXT NOT NULL,
name TEXT NOT NULL,
age INTEGER
);

--2nd table for player 1 - user
CREATE TABLE IF NOT EXISTS player1 (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_username TEXT REFERENCES username(id_username),
player_moves INTEGER,
player_score INTEGER
);

--3rd table for player 2 - pcu
CREATE TABLE IF NOT EXISTS playerPcu (
id_pcu INTEGER AUTO_INCREMENT PRIMARY KEY,
pcu_moves INTEGER,
pcu_opponent TEXT REFERENCES username(id_username),
pcu_score INTEGER
);

