package DAO;

import model.RegisteredUser;
import model.RegularUser;

import java.sql.*;

/**
 * This class implements the CRUD (Create, Read, Delete, Update) operations on USERS table in the database.
 */
public class UserDAO {
    /**
     * Stores the  prepared statement for CREATING a new row in the database.
     */
    private static final String CREATE_REGULAR_USER = "INSERT INTO users( first_name, last_name, email, is_reg_user, user_password,  registration_date) VALUES(?, ?, ?, ?, ?, ?)";

    /**
     * Stores the  prepared statement for CREATING  a new registered user row in the database.
     */
    private static final String CREATE_REGISTERED_USER = "INSERT INTO users( first_name, last_name, email, is_reg_user, user_password,  registration_date) VALUES(?, ?, ?, ?, ?, ?)";

    /**
     * Stores the  prepared statement for authenticating a registered user row in the database.
     */
    private static final String AUTHENTICATE = "SELECT * FROM users where email=? and user_password=?";
    private static final String CHECK_EMAIL = "SELECT * FROM users where email=?";

    /**
     * Returns an instance of the SingletonDBConnector class..
     */
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();

    /**
     * This method creates a regular user and stores the information in the users' table in  MSQL database
     * @param odUser
     * @return
     */
    public int createRegularUser(RegularUser odUser) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_REGULAR_USER);


            stmt.setString(1, odUser.getFirst_name());
            stmt.setString(2, odUser.getLast_name());
            stmt.setString(3, odUser.getEmail());
            stmt.setBoolean(4, odUser.Is_reg_user());
            stmt.setString(5, null);
            stmt.setDate(6, null);

            int result = stmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }

    public int getUserId(String email) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();
            results.next();

            return results.getInt("user_id");
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeStmt(stmt);

        }
        return 0;
    }

    /**
     * This method inserts a new register user in the database.
     *
     * @param regUser
     * @return
     */
    public int createRegisteredUser(RegisteredUser regUser) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_REGISTERED_USER);

            stmt.setString(1, regUser.getFirst_name());
            stmt.setString(2, regUser.getLast_name());
            stmt.setString(3, regUser.getEmail());
            stmt.setBoolean(4, regUser.Is_reg_user());
            stmt.setString(5, regUser.getUser_password());
            stmt.setDate(6, new Date(regUser.getRegistration_date().getTime()));

            int result = stmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }

    /**
     * Authenticates the user login process
     *
     * @param email
     * @param user_password
     * @return true if the username and password are correct. Returns false otherwise.
     */
    public boolean AuthenticateUser(String email, String user_password) {

        boolean result = false;

        Connection conn = null;
        PreparedStatement stmt = null;


        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(AUTHENTICATE);
            stmt.setString(1, email);
            stmt.setString(2, user_password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = true;
            }

        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
        return result;
    }

    /**
     * This method checks for the email of the guest in the database.
     * @param email
     * @return true if the email is present in the database. Returns false otherwise.
     */
    public boolean checkGuestEmail(String email) {

        boolean result = false;

        Connection conn = null;
        PreparedStatement stmt = null;


        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CHECK_EMAIL);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = true;
            }

        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
        return result;
    }
}
