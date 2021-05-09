
--that will store information about each user. (e.g. one row - one user)
CREATE TABLE IF NOT EXISTS users (
username TEXT NOT NULL PRIMARY KEY,
name TEXT NOT NULL,
age INTEGER
);

--that will store information about the game participants and result (who won) - (e.g. one row - one game)
--player2 must be pcu
CREATE TABLE IF NOT EXISTS games (
id INTEGER AUTO_INCREMENT PRIMARY KEY,
player1 TEXT,
player2 TEXT,
result TEXT,
foreign key (player1) REFERENCES users(username)
);

--that will store information about moves for all the games. (e.g. one row - one move).
CREATE TABLE IF NOT EXISTS moves (
id INTEGER AUTO_INCREMENT PRIMARY KEY,
player TEXT,
game INTEGER,
position_on_board INTEGER,
foreign key (player) REFERENCES users(username),
foreign key (game) REFERENCES games(id)
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





