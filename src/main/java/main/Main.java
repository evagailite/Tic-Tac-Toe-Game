package main;

public class Main {
//    // JDBC driver name and database URL
//    private static final String JDBC_DRIVER = "org.h2.Driver";
//    private static final String DB_URL = "jdbc:h2:./testdb";
//
//    //  Database credentials
//    private static final String USER = "sa";
//    private static final String PASS = "";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, USER, PASS);
//    }
//
//    private static final String CREATE_TABLE = "create table IF NOT EXISTS Book (\n" +
//            "                                     id INT NOT NULL,\n" +
//            "                                     title VARCHAR(50) NOT NULL,\n" +
//            "                                     author VARCHAR(20) NOT NULL,\n" +
//            "                                     submission_date DATE\n" +
//            "                                  );";
//
//    private static final String SELECT = "SELECT * FROM Book;";
//
//    public static void main(String[] args) {
//        try (Connection connection = getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                // When you need to SELECT from a database
//                //statement.executeQuery()
//                // When you need to changes database, but don't need to get data from database
//                //statement.executeUpdate(CREATE_TABLE);
//                try (ResultSet rs = statement.executeQuery(SELECT)) {
//                    while (rs.next()) {
//                        String title = rs.getString("title");
//                        int id = rs.getInt(0);
//                    }
//                }
//            }
//        } catch (SQLException throwables) {
//            System.out.println(throwables.getMessage());
//            throwables.printStackTrace();
//        }
//    }
//
//    public static void bestWay(String author) {
//        // Never concatenate SQL query with parameters!!!
//        //final String preparedSelect = "SELECT * FROM book WHERE author = " + author;
//        // Instead
//        final String preparedSelect = "SELECT * FROM book WHERE author = ?";
//        try (Connection connection = getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement(preparedSelect)) {
//                statement.setString(1, author); // Replace ? with value of author
//                try (ResultSet rs = statement.executeQuery()) {
//                    while (rs.next()) {
//                        String title = rs.getString("title");
//                        int id = rs.getInt(0);
//                    }
//                }
//            }
//        } catch (SQLException throwables) {
//            System.out.println(throwables.getMessage());
//            throwables.printStackTrace();
//        }
//    }
}