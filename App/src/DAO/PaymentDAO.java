package DAO;

import model.RegisteredUser;
import model.Ticket;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAO {
    /**
     * Instance variable for storing prepared statement.
     */
    private static final String CREATE_ANNUAL_PAYMENT = "INSERT INTO annual_payment(user_id, payment_type, payment_date, amount_paid, payment_status, credit_amount, credit_expiry_date) VALUES (?, ?, ?, ?, ?, ?,?)";

    /**
     * Instance variable for storing prepared statement.
     */
    private static final String CREATE_TICKET_PAYMENT = "INSERT INTO ticket_payment(ticket_id, payment_type, payment_date, amount_paid, payment_status, credit_amount, credit_expiry_date) VALUES (?, ?, ?, ?, ?, ?,?)";

    /**
     * Instance variable for storing prepared statement.
     */
    private static final String UPDATE_ANNUAL_PAYMENT = "UPDATE annual_payment SET user_id=?, payment_type=?, payment_date=?, amount_paid=?, payment_status=?, credit_amount=?, credit_expiry_date=? WHERE payment_id=?";

    /**
     * Instance variable for storing prepared statement.
     */
    private static final String UPDATE_TICKET_PAYMENT = "UPDATE ticket_payment SET ticket_id=?, payment_type=?, payment_date=?, amount_paid=?, payment_status=?, credit_amount=?, credit_expiry_date=? WHERE payment_id=?";

    /**
     * Instance variable for storing the instance of SingletonDBConnector.
     */
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();

    /**
     * This method creates ticket-payment and stores it in the database.
     *
     * @param ticket
     * @return
     */
    public int ceateTicketPayment(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_TICKET_PAYMENT);

            stmt.setInt(1, ticket.getTicket_id());
            stmt.setString(2, ticket.getPayment().getPayment_type());
            stmt.setDate(3, new Date(ticket.getPayment().getDate().getTime()));
            stmt.setDouble(4, ticket.getPayment().getAmount_paid());
            stmt.setString(5, ticket.getPayment().getPayment_status());
            stmt.setDouble(6, ticket.getPayment().getCredit_amount());


            if (ticket.getPayment().getCredit_expiry_date() == null) {
                stmt.setString(7, null);
            } else {
                stmt.setDate(7, new Date(ticket.getPayment().getCredit_expiry_date().getTime()));
            }

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
     * This method updates ticket-payment and stores it in the database.
     *
     * @param user
     * @return
     */

    public int updateTicketPayment(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(UPDATE_TICKET_PAYMENT);
            stmt.setInt(1, ticket.getTicket_id());
            stmt.setString(2, ticket.getPayment().getPayment_type());
            stmt.setDate(3, new Date(ticket.getPayment().getDate().getTime()));
            stmt.setDouble(4, ticket.getPayment().getAmount_paid());
            stmt.setString(5, ticket.getPayment().getPayment_status());
            stmt.setDouble(6, ticket.getPayment().getCredit_amount());
            stmt.setInt(8, ticket.getPayment().getPayment_id());

            if (ticket.getPayment().getCredit_expiry_date() == null) {
                stmt.setString(7, null);
            } else {
                stmt.setDate(7, new Date(ticket.getPayment().getCredit_expiry_date().getTime()));
            }

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
     * This method creates Annual-payment and stores it in the database.
     *
     * @param user
     * @return
     */
    public int ceateAnnualPayment(RegisteredUser user) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_ANNUAL_PAYMENT);

            stmt.setInt(1, user.getUser_id());
            stmt.setString(2, user.getPayment().getPayment_type());
            stmt.setDate(3, new Date(user.getPayment().getDate().getTime()));
            stmt.setDouble(4, user.getPayment().getAmount_paid());
            stmt.setString(5, user.getPayment().getPayment_status());
            stmt.setDouble(6, user.getPayment().getCredit_amount());
            stmt.setDate(7, null);

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
     * This method updates ticket-payment and stores it in the database.
     *
     * @param user
     * @return
     */
    public int updateAnnualPayment(RegisteredUser user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(UPDATE_ANNUAL_PAYMENT);
            stmt.setInt(1, user.getUser_id());
            stmt.setString(2, user.getPayment().getPayment_type());
            stmt.setDate(3, new Date(user.getPayment().getDate().getTime()));
            stmt.setDouble(4, user.getPayment().getAmount_paid());
            stmt.setString(5, user.getPayment().getPayment_status());
            stmt.setDouble(6, user.getPayment().getCredit_amount());
            stmt.setDate(7, null);
            stmt.setInt(8, user.getPayment().getPayment_id());

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
