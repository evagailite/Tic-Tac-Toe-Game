-- Alright. THat is definetely too much tables.
-- Let me give you a hint. You can complete a task with 3 tabels:
-- 1. users - that will store information about each user. (e.g. one row - one user)
-- 2. games - that will store information about the game participants and result (who won) - (e.g. one row - one game)
-- 3. moves - that will store information about moves for all the games. (e.g. one row - one move).
-- With these 3 tables you will never need to change any existing row. You will only put new rows each time when needed.
-- Please try and figure how to relate these tables.



--table for creating a user
CREATE TABLE IF NOT EXISTS users (
id_username INTEGER AUTO_INCREMENT PRIMARY KEY, -- I don't think that you need separate PRIMARY KEY in this table. 
  -- I mean you will only have unique user names, right? So the username can be a primary key. 
  -- So I suggest remove id_username column completely and make username column a PRIMARY KEY
username TEXT NOT NULL,
name TEXT NOT NULL,
age INTEGER
);

--table for game results, consist of all possible ending
-- Not clear what this table is needed for
CREATE TABLE IF NOT EXISTS results (
id_results INTEGER AUTO_INCREMENT PRIMARY KEY,
win INTEGER,
loose INTEGER,
tie INTEGER
);

-- Not clear what this table is needed for
CREATE TABLE IF NOT EXISTS moves (
id_moves INTEGER AUTO_INCREMENT PRIMARY KEY,
player_positions INTEGER,
pcu_positions INTEGER,
);


CREATE TABLE IF NOT EXISTS game (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_1 INTEGER,
player_2 INTEGER,
player_1_score INTEGER,
player_2_score INTEGER,
foreign key (player_1) REFERENCES player1(id_user)
foreign key (player_2) REFERENCES playerPcu(id_pcu),
foreign key (player_1_score) REFERENCES results(id_results),
foreign key (player_2_score) REFERENCES results(id_results)
);

--table for player1 - user
CREATE TABLE IF NOT EXISTS player1 (
id_user INTEGER AUTO_INCREMENT PRIMARY KEY,
player_username BIGINT,
player_moves BIGINT,
player_score BIGINT,
foreign key (player_username) REFERENCES users(id_username),
foreign key (player_moves) REFERENCES moves(id_moves),
foreign key (player_score) REFERENCES game(id_user)
);

--table for player2 - pcu
CREATE TABLE IF NOT EXISTS playerPcu (
id_pcu INTEGER AUTO_INCREMENT PRIMARY KEY,
pcu_moves BIGINT,
pcu_opponent BIGINT,
pcu_score BIGINT,
foreign key (pcu_moves) REFERENCES moves(id_moves),
foreign key (pcu_opponent) REFERENCES users(id_username),
foreign key (pcu_score) REFERENCES game(id_user)
);

--table played games
CREATE TABLE IF NOT EXISTS playedGames (
id_played_game INTEGER AUTO_INCREMENT PRIMARY KEY,
player_one INTEGER,
player_two INTEGER,
player_one_moves INTEGER,
player_two_moves INTEGER,
foreign key (player_one) REFERENCES username(id_username),
foreign key (player_two) REFERENCES playerPcu(id_pcu),
foreign key (player_one_moves) REFERENCES moves(id_moves),
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
SELECT * FROM playerPcu;

--display played games
SELECT * FROM playedGames;

__________________________________________
-- display all games played by specific player
SELECT * FROM player1 WHERE username = ?;

-- count total cpu score
SELECT SUM(score) FROM playerPcu;

-- count total score for specific player





