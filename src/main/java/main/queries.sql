
--that will store information about each user. (e.g. one row - one user)
CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50),
  name VARCHAR(50),
  age INTEGER
);

ALTER TABLE users ALTER COLUMN username VARCHAR(50) NOT NULL;

ALTER TABLE users ADD CONSTRAINT pk_username
PRIMARY KEY (username);

--that will store information about the game participants and result (who won) - (e.g. one row - one game)
--player2 must be pcu
CREATE TABLE IF NOT EXISTS games (
  id_games INTEGER AUTO_INCREMENT PRIMARY KEY,
  player1 TEXT NOT NULL,
  player2 TEXT NOT NULL,
  result TEXT NULL,
  foreign key (player1) REFERENCES users(username),
  foreign key (player2) REFERENCES users(username)
  -- I would suggest putting player2 and result as a foreign key on users table (same as player1)
  -- If you want to have second player - PCU - you can actually create this user yourself in a users table. And then always reference it.
  -- Additionally I would put player1, player2 as NOT NULL and result as NULL.
  -- Meaning that result will be either a valid user from users table or NULL, when there is a tie.
);

--that will store information about moves for all the games. (e.g. one row - one move).
--in pcu case player will be null
CREATE TABLE IF NOT EXISTS moves (
  id_moves INTEGER AUTO_INCREMENT PRIMARY KEY,
  player TEXT, 
  game INTEGER,
  position_on_board INTEGER,
  foreign key (player) REFERENCES users(username),
  foreign key (game) REFERENCES games(id_games)
);

-------------------------------------------
--add data about user
INSERT INTO username
(username, name, age) VALUES
(?, ?, ?);

--add pcu as a player
INSERT INTO username
(username, name) VALUES
"pcu", "pcu";

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

--  search for entered username
SELECT username
FROM users
WHERE username = ?;







