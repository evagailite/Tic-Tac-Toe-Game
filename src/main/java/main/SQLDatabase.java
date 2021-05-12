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

    //ADD PCU AS 2ND PLAYER
    public static final String ADD_USER_PCU = "INSERT INTO " + TABLE_USERS + " (" + USERNAME + ", " +
            NAME + ") VALUES ( ?, ?);";

    //ADD MOVES. POSSIBLE NUMBERS FROM 1-9
    public static final String ADD_MOVES = "INSERT INTO " + TABLE_MOVES + " (" + POSITION_ON_BOARD + ") " +
            "VALUES ( ?);";

    //ADD RESULT IN THE GAME. POSSIBLE OPTIONS - PLAYER_WIN, CPU_WIN, TIE
    public static final String ADD_RESULT = "INSERT INTO " + TABLE_GAMES + " (" + RESULT + ") " +
            "VALUES ( ?);";

    //DISPLAY ALL EXISTING GAMES
    public static final String DISPLAY_GAMES = "SELECT * FROM " + TABLE_GAMES + ";";

    //DISPLAY MOVES FOR CERTAIN GAME
    public static final String DISPLAY_GAME_MOVES = "SELECT  " + PLAYER + ", " + POSITION_ON_BOARD +
            " FROM " + TABLE_MOVES + " WHERE " + GAME + "=?";

    //SEARCH FOR EXISTING USERNAME
    public static final String CHECK_FOR_USER = "SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " +
            USERNAME + "=?";

    //ADD PLAYER IN THE MOVES TABLE
    public static final String ADD_PLAYER_IN_THE_MOVES_TABLE = "INSERT INTO " + TABLE_MOVES + "(" + PLAYER +
            ") SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " + USERNAME + "=?";


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {
            prepareDatabase(connection);
            //  workWithConnection(connection);
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }

//        // Loop to allow different users to log in.
//        while (true) {
//            String username = login();
//            playGame(username);
//        }

        searchForUsername();

        playGame();


    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_GAMES);
            statement.executeUpdate(CREATE_TABLE_MOVES);
        }
    }

    private static void playGame() {
        TicTacToe ticTacToe = new TicTacToe();

        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, //0
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},               //2
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}};              //4

        ticTacToe.printGameBoard(gameBoard);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your placement (1-9): ");
            int playerPosition = (scanner.nextInt());
            addMoves(playerPosition);

            //while they not enter correct position, keep asking to put correct until they do
            while (ticTacToe.playerPositions.contains(playerPosition) ||
                    ticTacToe.cpuPositions.contains(playerPosition)) {
                System.out.println("Position is taken! Enter a correct position");
                playerPosition = scanner.nextInt();
                addMoves(playerPosition);
            }

            //always check a winner and the result after each player and cpu move
            String result = ticTacToe.checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }

            ticTacToe.placePiece(gameBoard, playerPosition, "player");

            //printGameBoard(gameBoard);

            result = ticTacToe.checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }

            //cpu makes move
            //store input
            //check if there is a winner
            Random random = new Random();
            int cpuPosition = random.nextInt(9) + 1;
            while (ticTacToe.playerPositions.contains(cpuPosition) ||
                    ticTacToe.cpuPositions.contains(cpuPosition)) {
                cpuPosition = random.nextInt(9) + 1;
            }

            ticTacToe.placePiece(gameBoard, cpuPosition, "cpu");

            ticTacToe.printGameBoard(gameBoard);

        }
    }


    private static void displayAllGames(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(DISPLAY_GAMES)) {
//                printAllColumns(resultSet);
            }
        }
    }


    private static void addMoves(int value) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVES)) {
                preparedStatement.setInt(1, value);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static void addPlayerInTheMovesTable(String username) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PLAYER_IN_THE_MOVES_TABLE)) {
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static void addResult(String result) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_RESULT)) {
                preparedStatement.setString(1, result);
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
                System.out.println("Welcome " + username + " to the game Tic-Tac-Toe");

                //insert player in the moves table
                addPlayerInTheMovesTable(username);
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
        }


    }

    private static void searchForUsername() {
        try (Connection connection = getConnection()) {

            System.out.print("To start playing a game, enter a username: ");
            String username = scanner.nextLine();
            //check if user exists
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_FOR_USER)) {
                preparedStatement.setString(1, username);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString(USERNAME);
                        System.out.println("Welcome " + username + " to the game Tic-Tac-Toe");

                        //insert player in the moves table
                        addPlayerInTheMovesTable(username);

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

    }
//
//    private static String login() {
//        // 1. Ask user to enter name
//        // 2. Check if user already registered
//        // 3a. Register user
//        // 3b. Proceed with the user
//
//        searchForUsername();
//
//        return null;
//
//    }

    public static String checkWinner() {

        List topRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);

        List leftColumn = Arrays.asList(1, 4, 7);
        List middleColumn = Arrays.asList(2, 5, 8);
        List rightColumn = Arrays.asList(3, 6, 9);

        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        List<List> winning = new ArrayList<List>();

        winning.add(topRow);
        winning.add(middleRow);
        winning.add(bottomRow);

        winning.add(leftColumn);
        winning.add(middleColumn);
        winning.add(rightColumn);

        winning.add(cross1);
        winning.add(cross2);

        for (List lists : winning) {
            if (TicTacToe.playerPositions.containsAll(lists)) {
                addResult("PLAYER_WINS");
                return "Congratulations you won!";
            } else if (TicTacToe.cpuPositions.containsAll(lists)) {
                addResult("CPU_WINS");
                return "CPU wins!";
            } else if (TicTacToe.playerPositions.size() + TicTacToe.cpuPositions.size() == 9) {
                addResult("TIE");
                return "TIE!";
            }
        }

        //bug
        // If you place a winning piece as the last move -
        // it will still register as a Tie,
        // since the last "else if" in the for loop
        // Need to put this check as a separate if statement outside the for loop and it solves it.

        //didn't print the game board (put the last number) after print CAT

        return "";
    }


//        //in the end of the game, print options
//        tttEndOfGame tttEndOfGame = new tttEndOfGame();
//
//        boolean quit = false;
//        while (!quit) {
//
//
//            tttEndOfGame.printActions();
//
//            int choice = 0;
//            System.out.println("Please choose next action:");
//            choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 0:
//                    tttEndOfGame.printActions();
//                    break;
//                case 1:
//                    System.out.println("The game starts from the start! New round!");
//                    playAGame();
//                    break;
//                case 2:
//                    System.out.println("Logging out!");
//                    searchForUsername();
//                    break;
//                case 3:
//                    //   listOfGames();
//                case 4:
//                    //   chooseGameToSeeMoves();
//                    break;
//                case 5:
//                   // quit = true;
//                    break;
//                default:
//                    System.out.println("Invalid input! Please choose again");
//                    tttEndOfGame.printActions();
//                    break;
//            }
//        }

}

