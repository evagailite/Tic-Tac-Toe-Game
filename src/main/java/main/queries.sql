
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
player_username INTEGER REFERENCES username(id_username),
player_moves INTEGER,
player_score INTEGER
);

--3rd table for player 2 - pcu
CREATE TABLE IF NOT EXISTS playerPcu (
id_pcu INTEGER AUTO_INCREMENT PRIMARY KEY,
pcu_moves INTEGER,
pcu_opponent INTEGER REFERENCES username(id_username),
pcu_score INTEGER
);

--4th table for game results
CREATE TABLE IF NOT EXISTS results (
id_score INTEGER AUTO_INCREMENT PRIMARY KEY,
win INTEGER,
loose INTEGER,
tie INTEGER,
);

--add constance for game results
INSERT INTO results
(win, loose, tie) VALUES
(?, ?, ?);

--display username table
SELECT * FROM username;

--display player1 table;
SELECT * FROM player1;

--display cpu table
SELECT * FROM cpu;

--add data about username
INSERT INTO username
(username, name, age) VALUES
(?, ?, ?);

--add player1 moves
INSERT INTO player1
(moves) VALUES
(?);

--add cpu moves
INSERT INTO cpu
(moves) VALUES
(?);

--add player1 score
INSERT INTO player1
(score) VALUES
(?);

--add cpu score
INSERT INTO cpu
(score) VALUES
(?);





