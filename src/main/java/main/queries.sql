
--DONE!!!
--that will store information about each user. (e.g. one row - one user)
CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  age INTEGER,
  CONSTRAINT pk_username PRIMARY KEY (username)
);

--that will store information about the game participants and result (who won) - (e.g. one row - one game)
--player2 must be pcu
CREATE TABLE IF NOT EXISTS games (
  id_games INTEGER AUTO_INCREMENT PRIMARY KEY,
  player1 VARCHAR(50) NOT NULL REFERENCES users (username),
  player2 VARCHAR(50) NOT NULL REFERENCES users (username),
  result VARCHAR(100) NULL
  );
 -- player1 VARCHAR(50) NOT NULL,
  --player2 VARCHAR(50) NOT NULL,
  --foreign key (player1) REFERENCES users(username),
  --foreign key (player2) REFERENCES users(username)

--that will store information about moves for all the games. (e.g. one row - one move).
--in pcu case player will be null
CREATE TABLE IF NOT EXISTS moves (
  id_moves INTEGER AUTO_INCREMENT PRIMARY KEY,
  player VARCHAR(50) REFERENCES users (username),
  game INTEGER REFERENCES games(id_games),
  position_on_board INTEGER
  );
--  player VARCHAR(50),
 -- game INTEGER,

 -- foreign key (player) REFERENCES users(username),
  --foreign key (game) REFERENCES games(id_games)

INSERT INTO USERS (username, name, age)
VALUES ('Eva', 'Eva', 31);

INSERT INTO USERS (username, name, age)
VALUES ('CPU', 'CPU', 100);

--3 queries

INSERT INTO MOVES (player, game, position_on_board)
VALUES ((SELECT USERNAME FROM USERS WHERE USERNAME = ? ),
(SELECT id_games FROM games WHERE player1 = ?), ?);

INSERT INTO MOVES (player, game, position_on_board)
VALUES (?, ?, ?);

INSERT INTO users
(username, name, age) VALUES
(?, ?, ?);

--add results in the game. Possible options - player_win, cpu_win, tie
INSERT INTO games
(player1, player2, result) VALUES
(?, ?, ?);

--insert foreign keys..table games
INSERT INTO GAMES (PLAYER1, PLAYER2, RESULT)
VALUES ((SELECT USERNAME FROM USERS WHERE USERNAME = ? ),
(SELECT USERNAME FROM USERS WHERE USERNAME = ?), ?);

-------------------------------------------------------------

INSERT INTO MOVES (position_on_board)
VALUES (?);

-------------------------------------------

--add player in the moves table
INSERT INTO moves (player)
SELECT username
FROM users
WHERE username = ?;

--add pcu as 2nd player
INSERT INTO games (player2)
SELECT username
FROM users
WHERE username = ?

------------------------------------------
--display all existing games
SELECT * FROM games;

SELECT id_games FROM games;

SELECT player1 FROM GAMES;

--get username
SELECT username FROM users;

--display moves for certain game
SELECT player, position_on_board
FROM moves
WHERE game = ?;

--search for entered username
SELECT username
FROM users
WHERE username = ?;

--insert correct id_games values in the table
SELECT id_games
FROM games
INNER JOIN moves
ON moves.game = games.id_games;

--2nd inner join table
SELECT player1
FROM games
INNER JOIN moves
ON moves.player = games.player1;

UPDATE games SET result = 'win_loose' WHERE RESULT IS NULL;

SELECT * FROM GAMES ;

SELECT * FROM MOVES ;

SELECT id_games, POSITION_ON_BOARD
FROM games
INNER JOIN moves
ON moves.game = games.id_games
WHERE ID_GAMES = ?;


----------------
CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  age INTEGER,
  CONSTRAINT pk_username PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS games (
  id_games INTEGER AUTO_INCREMENT PRIMARY KEY,
  player1 VARCHAR(50) NOT NULL REFERENCES users (username),
  player2 VARCHAR(50) NOT NULL REFERENCES users (username),
  result VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS moves (
  id_moves INTEGER AUTO_INCREMENT PRIMARY KEY,
  player VARCHAR(50) REFERENCES users (username),
  game INTEGER REFERENCES games(id_games),
  position_on_board INTEGER
);

INSERT INTO USERS (username, name, age)
VALUES ('CPU', 'CPU', 100);
--------------------------------
INSERT INTO USERS (username, name, age)
VALUES ('Eva', 'Eva', 31);







