package DAO;

import model.Theatre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the CRUD (Create, Read, Delete, Update) operations on Theatre table in the database.
 */
public class TheatreDAO {

    /**
     *  Stores the  prepared statement for deleting a row in the database.
     */
    private static final String DELETE = "DELETE FROM theatre WHERE theatre_id=?";

    /**
     * Stores the  prepared statement for finding all the rows in the database.
     */
    private static final String FIND_ALL = "SELECT * FROM theatre ORDER BY theatre_id";

    /**
     * Stores the  prepared statement for finding a row by ID in the database.
     */
    private static final String FIND_BY_THEATRE_ID = "SELECT * FROM theatre WHERE theatre_id=?";

    /**
     * Stores the  prepared statement for INSERTING a row by ID in the database.
     */
    private static final String INSERT = "INSERT INTO theatre(theatre_id, theatre_name) VALUES(?, ?)";

    /**
     * Stores the  prepared statement for UPDATING a row by ID in the database.
     */
    private static final String UPDATE = "UPDATE theatre SET theatre_id=?, theatre_name=? WHERE theatre_id=?";

    /**
     * This is an helper method set the column names of the theatre table.
     * @param rs
     * @return
     * @throws SQLException
     */
    private Theatre parameters(ResultSet rs) throws SQLException {
        Theatre theatre  = new Theatre();
        theatre.setTheatre_id(rs.getInt("theatre_id"));
        theatre.setTheatre_name(rs.getString("theatre_name"));

        return theatre;
    }

    /**
     * This helper method is for setting the update parameters.
     * @param theatre
     * @param stmt
     * @throws SQLException
     */
    private void forUpdate(Theatre theatre, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, theatre.getTheatre_id());
        stmt.setString(2, theatre.getTheatre_name());
    }
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();

    /**
     * This methods returns the list of all the theatres persisted in the database.
     * @return
     */
    public List<Theatre> findAllTheatre() {
        Connection conn = null;
        PreparedStatement stmt = null;
        List<Theatre> list = new ArrayList<>();

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Theatre theatre = parameters(rs);
                list.add(theatre);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }

        return list;
    }

    /**
     * This method adds a Theatre object to the database.
     * @param theatre
     * @return
     */
    public int addTheatre(Theatre theatre) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            forUpdate(theatre, stmt);


            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                theatre.setTheatre_id(rs.getInt(1));
            }

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
     * This method deletes a theatre object from the database based on the theatre ID.
     * @param theatre_id
     * @return
     */
    public int delete(int theatre_id) { //Tested
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setInt(1, theatre_id);

            return stmt.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }

    /**
     * This method updates the theatre object in the database.
     * @param theatre
     * @return
     */
    public int updateMovie(Theatre theatre) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(UPDATE);
            forUpdate(theatre, stmt);

            return stmt.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }
}
