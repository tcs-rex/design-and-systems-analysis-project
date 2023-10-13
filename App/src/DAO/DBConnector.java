package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/movie_theatre_ticket_system";

    public static final String USER_ID = "user";
    public static final String PASSWORD = "user1";

    public DBConnector() {
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Helper class
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

    //Helper class
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
