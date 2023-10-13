package DAO;

import java.sql.*;

/**
 * This class is responsible for connecting to the database and implements a Singleton Design pattern.
 */
public class SingletonDBConnector {

    /**
     * Instance variable for URL to the database
     */
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/movie_theatre_ticket_system";

    /**
     * Instance variable for the user id of the database.
     */
    public static final String USER_ID = "user";

    /**
     * Instance variable for the password of the database.
     */
    public static final String PASSWORD = "user1";

    /**
     * Instance variable for the SingletonDBConnector.
     */
    private static SingletonDBConnector instance = null;

    /**
     * Constructor for the SingletonDBConnector class.
     */
    private SingletonDBConnector() {
    }

    /**
     * Method for connecting to the database.
     * @return DriverManager object.
     */
    public Connection getConnection() {

        try {
            return DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for checking if an instance of SingletonDBConnector exist.
     * @return instance of the SingletonDBConnector.
     */
    public static SingletonDBConnector getOnlyInstance() {
        if (instance == null) {
            instance = new SingletonDBConnector();
        }
        return instance;
    }

    /**
     * Method for closing database statement.
     * @param stmt
     */
    public void closeStmt(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method for closing database connection.
     * @param con
     */
    public void closeCon(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
