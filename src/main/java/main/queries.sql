
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
--in pcu case player will be null
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

--add pcu as player in the games table
INSERT INTO games
(player2) VALUES
"pcu";

--add moves. Possible numbers from 1-9
INSERT INTO moves
(position_on_board) VALUES
(?);

--add results in the game. Possible options - player_win, cpu_win, tie
INSERT INTO games
(result) VALUES
(?);

------------------------------------------
--display all existing games
SELECT * FROM games;

--display moves for certain game
SELECT player, position_on_board
FROM moves
WHERE game = ?;







