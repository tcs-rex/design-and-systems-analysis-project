package DAO;

import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the CRUD (Create, Read, Delete, Update) operations on Ticket table in the database.
 */
public class TicketDAO {

    /**
     * Stores the  prepared statement for CREATING a row in the database.
     */
    private static final String CREATE_TICKET = "INSERT INTO ticket(user_id, movie_id, theatre_id, showtime_id, seat_id) VALUES (?, ?, ?, ?, ?)";

    /**
     * An instance that returns a SingletonDBConnector class.
     */
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();


    /**
     * This method creates a Ticket object and stores it in th database.
     * @param ticket
     * @return
     */
    public int creatTicket(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_TICKET);

            stmt.setInt(1, ticket.getUser().getUser_id());
            stmt.setInt(2, ticket.getMovie().getMovie_id());
            stmt.setInt(3, ticket.getTheatre().getTheatre_id());
            stmt.setInt(4, ticket.getShowtime().getShowtime_id());
            stmt.setInt(5, ticket.getSeat().getSeat_id());

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

    public int getTicketId(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND movie_id = ? AND theatre_id = ? AND showtime_id = ? AND seat_id = ?");

            stmt.setInt(1, ticket.getUser().getUser_id());
            stmt.setInt(2, ticket.getMovie().getMovie_id());
            stmt.setInt(3, ticket.getTheatre().getTheatre_id());
            stmt.setInt(4, ticket.getShowtime().getShowtime_id());
            stmt.setInt(5, ticket.getSeat().getSeat_id());


            ResultSet result = stmt.executeQuery();
            result.next();


            return result.getInt("ticket_id");
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }


}
