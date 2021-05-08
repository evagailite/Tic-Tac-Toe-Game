package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    //CREATE TABLE FOR USERNAME
    public static final String TABLE_USERNAME = "username";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    private static final String CREATE_TABLE_USERNAME = "CREATE TABLE IF NOT EXISTS " + TABLE_USERNAME + " (" +
            COLUMN_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            COLUMN_USERNAME + " TEXT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_AGE + " INTEGER, " +
            ");";

    //CREATE TABLE FOR
    public static final String TABLE_PLAYER_1 = "player1";

    public static final String PLAYER_1_ID = "id";
    public static final String PLAYER_USERNAME = "username";
    public static final String PLAYER_1_MOVES = "moves";
    public static final String PLAYER_SCORE = "score";

    private static final String CREATE_TABLE_PLAYER_1 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER_1 + " (" +
            PLAYER_1_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PLAYER_1_MOVES + " INTEGER, " +
            PLAYER_USERNAME + " TEXT REFERENCES " + TABLE_USERNAME + "(" + COLUMN_ID + ")," +
            PLAYER_SCORE + " INTEGER " +
            ");";

    public static final String TABLE_PCU = "pcu";

    public static final String PCU_ID = "id";
    public static final String PCU_OPPONENT = "opponent";
    public static final String PCU_MOVES = "moves";
    public static final String PCU_SCORE = "score";

    private static final String CREATE_TABLE_PCU = "CREATE TABLE IF NOT EXISTS " + TABLE_PCU + " (" +
            PCU_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            PCU_MOVES + " INTEGER, " +
            PCU_OPPONENT + " TEXT REFERENCES " + TABLE_USERNAME + "(" + COLUMN_ID + ")," +
            PCU_SCORE + " INTEGER " +
            ");";

    public static final String DISPLAY_TABLE_USERNAME = "SELECT * FROM " + TABLE_USERNAME + ";";

    public static final String DISPLAY_TABLE_PLAYER_1 = "SELECT * FROM " + TABLE_PLAYER_1 + ";";
    ///NEED TO JOIN TABLES WHERE USERNAME IS ?

    public static final String DISPLAY_TABLE_PCU = "SELECT * FROM " + TABLE_PCU + ";";
    ///NEED TO JOIN TABLES

    public static final String ADD_USERNAME = "INSERT INTO " + TABLE_USERNAME + " (" + COLUMN_USERNAME + ", " +
            COLUMN_NAME + ", " + COLUMN_AGE + ") VALUES ( ?, ?, ?);";

    public static final String ADD_MOVES_PLAYER_1 = "INSERT INTO " + TABLE_PLAYER_1 + " (" + PLAYER_1_MOVES + ") VALUES ( ? );";

    public static final String ADD_MOVES_PCU = "INSERT INTO " + TABLE_PCU + " (" + PCU_MOVES + ") VALUES ( ? );";

    public static final String ADD_SCORE_PLAYER_1 = "INSERT INTO " + TABLE_PLAYER_1 + " (" + PLAYER_SCORE + ") VALUES ( ? );";

    public static final String ADD_SCORE_PCU = "INSERT INTO " + TABLE_PCU + " (" + PCU_SCORE + ") VALUES ( ? );";

    //COUNT? FOR SCORES TO DISPLAY
}
