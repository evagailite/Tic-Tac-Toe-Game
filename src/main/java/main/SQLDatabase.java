package main;

import java.sql.*;
import java.util.*;

public class SQLDatabase {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:D:\\SheGoesTech2021\\TicTacToe\\final_project_team_1";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    //CREATE TABLE FOR USER
    public static final String TABLE_USERS = "users";

    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String AGE = "age";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
            USERNAME + " VARCHAR(50) NOT NULL, " +
            NAME + " VARCHAR(50) NOT NULL, " +
            AGE + " INTEGER, " +
            "CONSTRAINT pk_username PRIMARY KEY (" + USERNAME + ")" +
            ");";

    //CREATE TABLE FOR GAMES
    public static final String TABLE_GAMES = "games";

    public static final String ID_GAMES = "id_games";
    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";
    public static final String RESULT = "result";

    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES + " (" +
            ID_GAMES + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PLAYER_1 + " VARCHAR(50) NOT NULL, " +
            PLAYER_2 + " VARCHAR(50) NOT NULL, " +
            RESULT + " VARCHAR(100) NULL, " +
            " foreign key " + "(" + PLAYER_1 + ")" + " REFERENCES " + TABLE_USERS + "(" + USERNAME + ")," +
            " foreign key " + "(" + PLAYER_2 + ")" + " REFERENCES " + TABLE_USERS + "(" + USERNAME + ")" +
            ");";


    //CREATE TABLE MOVES
    public static final String TABLE_MOVES = "moves";

    public static final String ID_MOVES = "id_moves";
    public static final String PLAYER = "player";
    public static final String GAME = "game";
    public static final String POSITION_ON_BOARD = "position_on_board";

    private static final String CREATE_TABLE_MOVES = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVES + " (" +
            ID_MOVES + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PLAYER + " VARCHAR(50), " +
            GAME + " INTEGER, " +
            POSITION_ON_BOARD + " INTEGER, " +
            " foreign key " + "(" + PLAYER + ")" + " REFERENCES " + TABLE_USERS + "(" + USERNAME + ")," +
            " foreign key " + "(" + GAME + ")" + " REFERENCES " + TABLE_GAMES + "(" + ID_GAMES + ")" +
            ");";

    //ADD USERS
    public static final String ADD_USER = "INSERT INTO " + TABLE_USERS + " (" + USERNAME + ", " +
            NAME + ", " + AGE + ") VALUES ( ?, ?, ?);";


    //ADD MOVES. POSSIBLE NUMBERS FROM 1-9
    public static final String ADD_MOVES = "INSERT INTO " + TABLE_MOVES + " (" + PLAYER + ", " + POSITION_ON_BOARD + ") " +
            "VALUES ( ?, ?);";


    //DISPLAY ALL EXISTING GAMES
    public static final String DISPLAY_GAMES = "SELECT * FROM " + TABLE_GAMES + ";";

    //DISPLAY MOVES FOR CERTAIN GAME
    public static final String DISPLAY_GAME_MOVES = "SELECT  " + PLAYER + ", " + POSITION_ON_BOARD +
            " FROM " + TABLE_MOVES + " WHERE " + GAME + "=?";

    //SEARCH FOR EXISTING USERNAME
    public static final String CHECK_FOR_USER = "SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " +
            USERNAME + "=?";

//    //????ADD PLAYER IN THE MOVES TABLE
//    public static final String ADD_PLAYER_IN_THE_MOVES_TABLE = "INSERT INTO " + TABLE_MOVES + "(" + PLAYER +
//            ") SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " + USERNAME + "=?";

    //*****************************************
    //ADD PLAYERS TO THE GAME IN THE START
    private static final String ADD_PLAYERS = " INSERT INTO " + TABLE_GAMES + "(" + PLAYER_1 + ", " + PLAYER_2 + ")"
            + " VALUES (?, ?)";

    //ADD RESULT IN THE GAME. POSSIBLE OPTIONS - PLAYER_WIN, CPU_WIN, TIE
    public static final String ADD_RESULT = "INSERT INTO " + TABLE_GAMES + " (" + RESULT + ") " +
            "VALUES ( ?);";

    //?????--add pcu as 2nd player
    public static final String ADD_PCU_AS_PLAYER2 = "INSERT INTO " + TABLE_MOVES + "(" + PLAYER + ")" +
            "SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " + USERNAME + "=?";

    //add id_games into game
    public static final String ADD_GAMES_ID = "INSERT INTO " + TABLE_MOVES + "(" + GAME + ")" +
            " SELECT " + ID_GAMES + " FROM " + TABLE_GAMES + " WHERE " + PLAYER_1 + "=?";


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {
            prepareDatabase(connection);
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

        // Loop to allow different users to log in.
        while (true) {
            String username = login();
            playGame();
        }

    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_GAMES);
            statement.executeUpdate(CREATE_TABLE_MOVES);
        }
    }

    private static void playGame() {
        boolean playAgain = false;
        do {

            //prepare game. add all values in the tables


            //actual game
            makeTurns();

            boolean quit = false;
            do {

                printActions();

                int choice = 0;

                Boolean retry = null;
                while (retry == null) {
                    try {
                        String input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        retry = false;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please choose action between numbers 1-5");
                    }
                }

                switch (choice) {

                    //maybe include a case with game template, example of numbers in the table and game rules!!!!

                    //!!!!!!!!!!prints display twice
                    case 0:
                        printActions();
                        break;
                    case 1:
                        playAgain = true;
                        makeTurns();
                        break;
                    case 2:
                        System.out.println("Logging out!");
                        quit = true;
                        break;
                    case 3:
                        //listOfGames();
                        break;
                    case 4:
                        //chooseGameToSeeMoves();
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input! Please choose action between numbers 1-5");
                        break;
                }

            } while (!quit);

        } while (!playAgain);

    }


    private static void makeTurns() {
        char[][] board = {{' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}};

        printBoard(board);

        while (true) {
            playerTurn(board, scanner);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);

            computerTurn(board);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);
        }

    }

    public static void printActions() {
        System.out.println("\nPress");
        System.out.println("\t 0 - Print actions");
        System.out.println("\t 1 - Play again");
        System.out.println("\t 2 - Log out");
        System.out.println("\t 3 - View list of games");
        System.out.println("\t 4 - Display moves of the game");
        System.out.println("\t 5 - Quit the application");
    }

    private static boolean isGameFinished(char[][] board) {

        if (hasContestantWon(board, 'X')) {
            printBoard(board);
            System.out.println("Player wins!");
            return true;
        }

        if (hasContestantWon(board, 'O')) {
            printBoard(board);
            System.out.println("Computer wins!");
            return true;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        printBoard(board);
        System.out.println("The game ended in a tie!");
        return true;
    }


    private static boolean hasContestantWon(char[][] board, char symbol) {
        if ((board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) ||
                (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) ||
                (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) ||

                (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) ||
                (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) ||
                (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) ||

                (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }
        return false;
    }


    private static void computerTurn(char[][] board) {
        Random rand = new Random();
        int computerMove;
        while (true) {
            computerMove = rand.nextInt(9) + 1;
            if (isValidMove(board, Integer.toString(computerMove))) {
                break;
            }
        }
        System.out.println("Computer chose " + computerMove);
        placeMove(board, Integer.toString(computerMove), 'O');
    }


    private static boolean isValidMove(char[][] board, String position) {
        //put numbers in this place to see numbers in table

        switch (position) {
            case "1":
                return (board[0][0] == ' ');
            case "2":
                return (board[0][1] == ' ');
            case "3":
                return (board[0][2] == ' ');
            case "4":
                return (board[1][0] == ' ');
            case "5":
                return (board[1][1] == ' ');
            case "6":
                return (board[1][2] == ' ');
            case "7":
                return (board[2][0] == ' ');
            case "8":
                return (board[2][1] == ' ');
            case "9":
                return (board[2][2] == ' ');
            default:
                return false;
        }
    }

    private static void playerTurn(char[][] board, Scanner scanner) {
        String userInput;
        while (true) {
            System.out.println("Where would you like to play? (1-9)");
            userInput = scanner.nextLine();
            if (isValidMove(board, userInput)) {
                break;
            } else {
                System.out.println(userInput + " is not a valid move.");
            }
        }
        placeMove(board, userInput, 'X');
    }


    private static void placeMove(char[][] board, String position, char symbol) {
        switch (position) {
            case "1":
                board[0][0] = symbol;
                break;
            case "2":
                board[0][1] = symbol;
                break;
            case "3":
                board[0][2] = symbol;
                break;
            case "4":
                board[1][0] = symbol;
                break;
            case "5":
                board[1][1] = symbol;
                break;
            case "6":
                board[1][2] = symbol;
                break;
            case "7":
                board[2][0] = symbol;
                break;
            case "8":
                board[2][1] = symbol;
                break;
            case "9":
                board[2][2] = symbol;
                break;
            default:
                System.out.println(":(");
        }
    }


    private static void printBoard(char[][] board) {
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("-+-+-");
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("-+-+-");
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
    }

    //**********************************************

    //insert into game player 1, player 2
    private static void addPlayers(String player1, String player2) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_PLAYERS)) {
                statement.setString(1, player1);
                statement.setString(2, player2);
                statement.executeUpdate();
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }


    private static String addPcuAsPlayer2() {
        String user = "cpu";
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PCU_AS_PLAYER2)) {
                String pcu;
                preparedStatement.setString(1, user);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

        return user;
    }

    //    public static final String ADD_GAMES_NR = "INSERT INTO " + TABLE_MOVES + "(" + GAME + ")" +
    //            "SELECT " + ID_GAMES + " FROM " + TABLE_GAMES + " WHERE " + PLAYER_1 + "=?";

    // INSERT INTO MOVES (GAME)
    //SELECT ID_GAMES FROM GAMES
    //WHERE PLAYER1=?;
//    private static int addGamesId(String username) {
//
//        int id = 0;
//        try (Connection connection = getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GAMES_ID)) {
//                preparedStatement.setString(1, username);
//                //preparedStatement.executeUpdate();
//
//                try (ResultSet rs = preparedStatement.executeQuery()) {
//                    while (rs.next()) {
//                        id = rs.getInt(ID_GAMES);
//                        System.out.println(id + "add_games_id");
//                    }
//                }
//            } catch (SQLException throwables) {
//                System.out.println("Something went wrong " + throwables.getMessage());
//                throwables.printStackTrace();
//
//            }
//        } catch (SQLException throwables) {
//            System.out.println("Something went wrong " + throwables.getMessage());
//            throwables.printStackTrace();
//        }
//        return id;
//    }


    private static String addResult(String result) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_RESULT)) {
                preparedStatement.setString(1, result);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

        return result;
    }

    private static void displayAllGames(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(DISPLAY_GAMES)) {
//                printAllColumns(resultSet);
            }
        }
    }


    private static void addMoves(String player, int position) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVES)) {
                preparedStatement.setString(1, player);
//                preparedStatement.setInt(2, game);
                preparedStatement.setInt(2, position);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
//
//    private static void addPlayerInTheMovesTable(String username) {
//        try (Connection connection = getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PLAYER_IN_THE_MOVES_TABLE)) {
//                preparedStatement.setString(1, username);
//                preparedStatement.executeUpdate();
//            }
//        } catch (SQLException throwables) {
//            System.out.println("Something went wrong " + throwables.getMessage());
//            throwables.printStackTrace();
//        }
//    }


    private static void addUser() {
        try (Connection connection = getConnection()) {

            System.out.print("Enter a username: ");
            String username = scanner.nextLine();

            System.out.print("Enter a name: ");
            String name = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();
                System.out.println("Welcome " + username + " to the game Tic-Tac-Toe");

            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }


    }

    private static String login() {
        String username = null;
        try (Connection connection = getConnection()) {

            System.out.print("To start playing a game, enter a username: ");
            username = scanner.nextLine();
            //check if user exists
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_FOR_USER)) {
                preparedStatement.setString(1, username);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString(USERNAME);
                        System.out.println("Welcome " + username + " to the game Tic-Tac-Toe");

                    } else {
                        //if username doesn't exists, add new
                        System.out.println(username + " not found. Please create a new user to play");

                        addUser();

                    }
                }
            } catch (SQLException throwables) {
                System.out.println("Something went wrong " + throwables.getMessage());
                throwables.printStackTrace();
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

        return username;
    }

}
    

