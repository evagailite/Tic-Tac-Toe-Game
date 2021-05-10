package main;

import java.sql.*;
import java.util.Scanner;

public class SQLDatabase {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:D:\\SheGoesTech2021\\DB\\db\\testdb";

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
            USERNAME + " TEXT NOT NULL PRIMARY KEY, " +
            NAME + " TEXT NOT NULL, " +
            AGE + " INTEGER " +
            ");";

    //CREATE TABLE FOR GAMES
    public static final String TABLE_GAMES = "games";

    public static final String ID_GAMES = "id_games";
    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";
    public static final String RESULT = "result";

    private static final String CREATE_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES + " (" +
            ID_GAMES + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PLAYER_1 + " TEXT NOT NULL, " +
            PLAYER_2 + " TEXT NOT NULL, " +
            RESULT + " TEXT NULL, " +
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
            PLAYER + " TEXT, " +
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
    ///NEED TO JOIN TABLES

    private static Scanner scanner = new Scanner(System.in);

//    public static void main(String[] args) {
//        try (Connection connection = getConnection()) {
//            prepareDatabase(connection);
//            workWithConnection(connection);
//        } catch (SQLException throwables) {
//            System.out.println(throwables.getMessage());
//            throwables.printStackTrace();
//        }
//    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int update = statement.executeUpdate(CREATE_TABLE_USER);
            System.out.println("Table successfully created " + update);
            update = statement.executeUpdate(CREATE_TABLE_GAMES);
            System.out.println("Table successfully created " + update);
            update = statement.executeUpdate(CREATE_TABLE_MOVES);
            System.out.println("Table successfully created " + update);
        }
    }

//    private static void workWithConnection(Connection connection) throws SQLException {
//        while (true) {
//            Action nextAction = getActionFromUser();
//            switch (nextAction) {
//                case PRINT_COUNT_OF_SONGS:
//                    printCountOfSongs(connection);
//                    break;
//                case PRINT_COUNT_OF_ALBUMS:
//                    break;
//                case PRINT_COUNT_OF_ARTISTS:
//                    break;
//
//                case EXIT:
//                    return;
//        }
//    }

    private static void displayAllGames(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(DISPLAY_GAMES)) {
//                printAllColumns(resultSet);
            }
        }
    }

    private static void addUser() {
        try (Connection connection = getConnection()) {

            System.out.println("Enter a username: ");
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
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong " + throwables.getMessage());
            throwables.printStackTrace();
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

}
