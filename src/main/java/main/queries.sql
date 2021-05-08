
--1st table for creating a username
CREATE TABLE IF NOT EXISTS username (
id_username INTEGER AUTO_INCREMENT PRIMARY KEY,
username TEXT NOT NULL,
name TEXT NOT NULL,
age INTEGER
);

--2nd table for game results
CREATE TABLE IF NOT EXISTS results (
id_results INTEGER AUTO_INCREMENT PRIMARY KEY,
win INTEGER,
loose INTEGER,
tie INTEGER
);

--3rd table for player 1 - user
CREATE TABLE IF NOT EXISTS player1 (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_username BIGINT,
foreign key (player_username) REFERENCES username(id_username),
player_moves INTEGER,
player_score BIGINT,
foreign key (player_score) REFERENCES results(id_results)
);

--4th table for player 2 - pcu
CREATE TABLE IF NOT EXISTS playerPcu (
id_pcu INTEGER AUTO_INCREMENT PRIMARY KEY,
pcu_moves INTEGER,
pcu_opponent BIGINT,
foreign key (pcu_opponent) REFERENCES username(id_username),
pcu_score BIGINT,
foreign key (pcu_score) REFERENCES results(id_results)
);

--add constance for game results
INSERT INTO results
(win, loose, tie) VALUES
(2, 0, 1);

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

--add opponent for cpu
INSERT INTO playerPcu
(pcu_opponent) VALUES
(?);

-- display player rank(scores)

-- display all games played by specific player
SELECT * FROM player1 WHERE username = ?;

-- count total cpu score
SELECT SUM(score) FROM playerPcu;

-- count total score for specific player





