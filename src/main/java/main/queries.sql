
--table for creating a user
CREATE TABLE IF NOT EXISTS users (
id_username INTEGER AUTO_INCREMENT PRIMARY KEY,
username TEXT NOT NULL,
name TEXT NOT NULL,
age INTEGER
);

--table for game results, consist of all possible ending
CREATE TABLE IF NOT EXISTS results (
id_results INTEGER AUTO_INCREMENT PRIMARY KEY,
win INTEGER,
loose INTEGER,
tie INTEGER
);

CREATE TABLE IF NOT EXISTS moves (
id_moves INTEGER AUTO_INCREMENT PRIMARY KEY,
player_positions INTEGER,
pcu_positions INTEGER,
);

CREATE TABLE IF NOT EXISTS game (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_1 INTEGER,
foreign key (player_1) REFERENCES player1(id_user)
player_2 INTEGER,
foreign key (player_2) REFERENCES playerPcu(id_pcu),
player_1_score INTEGER,
foreign key (player_1_score) REFERENCES results(id_results),
player_2_score INTEGER,
foreign key (player_2_score) REFERENCES results(id_results)
);

--table for player1 - user
CREATE TABLE IF NOT EXISTS player1 (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_username BIGINT,
foreign key (player_username) REFERENCES users(id_username),
player_moves BIGINT,
foreign key (player_moves) REFERENCES moves(id_moves),
player_score BIGINT,
foreign key (player_score) REFERENCES game(id_user)
);

--table for player2 - pcu
CREATE TABLE IF NOT EXISTS playerPcu (
id_pcu INTEGER AUTO_INCREMENT PRIMARY KEY,
pcu_moves BIGINT,
foreign key (pcu_moves) REFERENCES moves(id_moves),
pcu_opponent BIGINT,
foreign key (pcu_opponent) REFERENCES users(id_username),
pcu_score BIGINT,
foreign key (pcu_score) REFERENCES game(id_user)
);

--table played games
CREATE TABLE IF NOT EXISTS playedGames (
id_played_game INTEGER AUTO_INCREMENT PRIMARY KEY,
player_one INTEGER,
foreign key (player_one) REFERENCES username(id_username),
player_two INTEGER,
foreign key (player_two) REFERENCES playerPcu(id_pcu),
player_one_moves INTEGER,
foreign key (player_one_moves) REFERENCES moves(id_moves),
player_two_moves INTEGER,
foreign key (player_two_moves) REFERENCES moves(id_moves),
);

-------------------------------------------
--add data about username
INSERT INTO username
(username, name, age) VALUES
(?, ?, ?);

--add constance for game results
INSERT INTO results
(win, loose, tie) VALUES
(2, 0, 1);

--add player1 position
INSERT INTO moves
(player_positions) VALUES
(?);

--add pcu positions
INSERT INTO moves
(pcu_positions) VALUES
(?);

------------------------------------------


--display username table
SELECT * FROM username;

--display player1 table;
SELECT * FROM player1;

--display cpu table
SELECT * FROM cpu;





-- display all games played by specific player
SELECT * FROM player1 WHERE username = ?;

-- count total cpu score
SELECT SUM(score) FROM playerPcu;

-- count total score for specific player





