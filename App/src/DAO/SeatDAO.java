package DAO;

import model.Seat;

import java.sql.*;

public class SeatDAO {
    /**
     * instance variable UPDATE stores prepared statement.
     */
    private static final String UPDATE = "UPDATE seat SET is_taken=? WHERE seat_id=?";

    /**
     * instance variable for accessing SingletonDBConnector instance.
     */
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();

    /**
     * Method for updating seat table in the database.
     * @param seat
     * @param stmt
     * @throws SQLException
     */
    private void forUpdate(Seat seat, PreparedStatement stmt) throws SQLException {
        stmt.setBoolean(1, seat.isIs_taken());
        stmt.setInt(2, seat.getSeat_id());
    }

    /**
     * This method creates ticket-payment and stores it in the database.
     * @param seat
     * @return
     */
    public int updateSeat(Seat seat) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(UPDATE);
            forUpdate(seat, stmt);

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
