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
            PLAYER_1 + " VARCHAR(50) NOT NULL REFERENCES " + TABLE_USERS + "(" + USERNAME + ")," +
            PLAYER_2 + " VARCHAR(50) NOT NULL REFERENCES " + TABLE_USERS + "(" + USERNAME + ")," +
            RESULT + " VARCHAR(100) NULL " +
            ");";

    //CREATE TABLE MOVES
    public static final String TABLE_MOVES = "moves";

    public static final String ID_MOVES = "id_moves";
    public static final String PLAYER = "player";
    public static final String GAME = "game";
    public static final String POSITION_ON_BOARD = "position_on_board";

    private static final String CREATE_TABLE_MOVES = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVES + " (" +
            ID_MOVES + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PLAYER + " VARCHAR(50) REFERENCES " + TABLE_USERS + "(" + USERNAME + ")," +
            GAME + " INTEGER REFERENCES " + TABLE_GAMES + "(" + ID_GAMES + ")," +
            POSITION_ON_BOARD + " INTEGER " +
            ");";

    //SEARCH FOR EXISTING USERNAME
    public static final String CHECK_FOR_USER = "SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " +
            USERNAME + "=?";

    //ADD USERS
    public static final String ADD_USER = "INSERT INTO " + TABLE_USERS + " (" + USERNAME + ", " +
            NAME + ", " + AGE + ") VALUES ( ?, ?, ?);";

    //DISPLAY ALL EXISTING GAMES
    public static final String DISPLAY_GAMES = "SELECT * FROM " + TABLE_GAMES + ";";

    public static final String DISPLAY_USERS = "SELECT * FROM " + TABLE_USERS + ";";


    public static final String INSERT_TABLE_GAMES = "INSERT INTO " + TABLE_GAMES +
            "(" + PLAYER_1 + ", " + PLAYER_2 + ", " + RESULT + ")" +
            " VALUES (( SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " +
            USERNAME + " =?), ( SELECT " + USERNAME + " FROM " + TABLE_USERS +
            " WHERE " + USERNAME + " =?), ?);";

    public static final String GET_ID_GAME = "SELECT " + ID_GAMES + " FROM " + TABLE_GAMES;

    public static final String ADD_VALUES_MOVES_TABLE = "INSERT INTO " + TABLE_MOVES + "(" +
            PLAYER + ", " + GAME + ", " + POSITION_ON_BOARD + ") VALUES (?, ?, ?)";


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {
            prepareDatabase(connection);
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

        System.out.println("*********************************");
        System.out.println("*                               *");
        System.out.println("*            WELCOME            *");
        System.out.println("*          TO THE GAME          *");
        System.out.println("*          TIC-TAC-TOE          *");
        System.out.println("*                               *");
        System.out.println("*********************************");

        System.out.println(" ");
        // Loop to allow different users to log in.
        while (true) {
            String username = login();
            playGame(username);
        }
    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_GAMES);
            statement.executeUpdate(CREATE_TABLE_MOVES);
        }
    }

    private static void playGame(String username) {

        System.out.println(" ");
        System.out.println("The game is played on the 3X3 grid.");
        System.out.println("You have an X mark on the board game.");
        System.out.println("You will play against the Computer.");
        System.out.println("The Computer has a mark O.");
        System.out.println("Possible moves are shown on the game board below.");
        System.out.println("Please take a look and start playing! Good luck!");
        System.out.println(" ");

        boolean playAgain = false;
        do {
            //prepare game
            addGameInformation(username, username, " "); //added empty row in the database

            makeTurns(username);//actual game

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
                        System.out.println("Invalid input! Please choose action between numbers 1-4");
                    }
                }
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        playAgain = true;
                        addGameInformation(username, username, " ");
                        makeTurns(username);
                        break;
                    case 2:
                        System.out.println("Logging out!");
                        username = login();
                        playGame(username);
                        break;
                    case 3:
                        Scanner scanner1 = new Scanner(System.in);
                        int check = 0;
                        Boolean test = null;
                        while (test == null) {
                            try {
                                displayAllGames();
                                System.out.println(" ");
                                System.out.println("Please choose a number from column GAME_ID to see all the moves made by the user:");
                                String input2 = scanner1.nextLine();
                                //check = Integer.parseInt(input2);
                                displaySelectedGame(Integer.parseInt(input2));

                                test = false;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input! Please choose a number from column GAME_ID!");
                                System.out.println("");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("The application has stopped working!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input! Please choose action between numbers 1-4");
                        break;
                }
            } while (!quit);
        } while (!playAgain);
    }


    private static void boardExample() {
        char[][] example = {{'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}};
        printBoard(example);
    }

    private static void makeTurns(String username) {
        boardExample();
        char[][] board = {{' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}};

        while (true) {
            playerTurn(board, scanner, username);
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
        System.out.println("\t 4 - Quit the application");
    }

    private static boolean isGameFinished(char[][] board) {
        if (hasContestantWon(board, 'X')) {
            printBoard(board);
            System.out.println(" ");
            System.out.println("You won! Congratulations!");

            String result = "PLAYER_WIN";
            updateResult(result);

            return true;
        }

        if (hasContestantWon(board, 'O')) {
            printBoard(board);
            System.out.println(" ");
            System.out.println("Computer wins!");

            String result = "CPU_WIN";
            updateResult(result);

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
        System.out.println(" ");
        System.out.println("The game ended in a tie!");

        String result = "TIE";
        updateResult(result);

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

    private static void playerTurn(char[][] board, Scanner scanner, String username) {

        String userInput;

        while (true) {
            System.out.println(" ");
            System.out.println("Where would you like to play? (1-9)");
            userInput = scanner.nextLine();
            if (isValidMove(board, userInput)) {

                addMoveTable(username, addGameId(), Integer.parseInt(userInput));

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


    private static void addGameInformation(String player1, String player2, String result) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TABLE_GAMES)) {
                preparedStatement.setString(1, player1);
                preparedStatement.setString(2, "CPU");
                preparedStatement.setString(3, result);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }


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
                System.out.println(" ");
                System.out.println("Welcome " + username + "!");
            }

        } catch (
                SQLException throwables) {
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
                        System.out.println(" ");
                        System.out.println("Welcome " + username + "!");
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

    private static void displayAllGames() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(DISPLAY_GAMES);
                try (ResultSet rs = statement.executeQuery(DISPLAY_GAMES)) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-10s", metaData.getColumnName(i));
                    }
                    System.out.println("");
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.printf("%-10s", rs.getString(i));
                        }
                        System.out.println();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //******NOT INCLUDED IN THE GAME
    private static void printColumnsUsersTable() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(DISPLAY_USERS);
                try (ResultSet rs = statement.executeQuery(DISPLAY_USERS)) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-10s", metaData.getColumnName(i));
                    }
                    System.out.println("");
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.printf("%-10s", rs.getString(i));
                        }
                        System.out.println();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static void addMoveTable(String player, int game_id, int position) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_VALUES_MOVES_TABLE)) {
                preparedStatement.setString(1, player);
                preparedStatement.setInt(2, game_id);
                preparedStatement.setInt(3, position);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static int addGameId() {
        int id = 0;
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(GET_ID_GAME);
                try (ResultSet rs = statement.executeQuery(GET_ID_GAME)) {
                    while (rs.next()) {
                        id = rs.getInt(ID_GAMES);
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
        return id;
    }


    private static final String UPDATE = "UPDATE " + TABLE_GAMES + " SET " + RESULT + " = ? WHERE " + RESULT + " = ?;";

    public static void updateResult(String firstResult) {

        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                statement.setString(1, firstResult);
                statement.setString(2, " ");
                statement.executeUpdate();
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

    }

    public static final String USER_CHOOSE_GAME = "SELECT " + PLAYER_1 + ", " + ID_GAMES + ", " + POSITION_ON_BOARD +
            " FROM " + TABLE_GAMES + " INNER JOIN " + TABLE_MOVES + " ON " + TABLE_MOVES + "." + GAME + "=" +
            TABLE_GAMES + "." + ID_GAMES + " WHERE " + ID_GAMES + " = ?;";

    private static void displaySelectedGame(int id) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(USER_CHOOSE_GAME)) {
                statement.setInt(1, id);

                try (ResultSet rs = statement.executeQuery()) {

                    if (rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.printf("%-10s", metaData.getColumnName(i));
                        }
                        System.out.println("");

                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.printf("%-10s", rs.getString(i));
                            }
                            System.out.println();
                        }
                    } else {
                        System.out.println(id + " not found on the column GAMES_ID.");
                        System.out.println("Please press 3 to choose again.");
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }


}


