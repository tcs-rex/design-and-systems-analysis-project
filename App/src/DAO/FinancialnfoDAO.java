package DAO;


import model.FinancialInformation;
import model.RegisteredUser;

import java.sql.*;

/**
 * This class performs the CRUD operations on the financial_information table in the database.
 */
public class FinancialnfoDAO {

    /**
     * instance variable for prepared statement.
     */
    private static final String CREATE_REGISTERD_USER_INFO = "INSERT INTO financial_information( user_id, card_number, cvc, expiry_date) VALUES(?, ?, ?, ?)";

    /**
     * instance variable for storing instance of SingletonDBConnector class.
     */
    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();

    /**
     * instance variable for storing instance of SingletonDBConnector class.
     */
    FinancialInformation finInfo = new FinancialInformation();


    /**
     * This method creates a financial-information for the registered user and stores into the database
     * @param user
     * @return
     */
    public int createFinancialInformation(RegisteredUser user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.getConnection();
            stmt = conn.prepareStatement(CREATE_REGISTERD_USER_INFO, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, user.getUser_id());
            stmt.setLong(2, user.getFinancialInformation().getCard_number());
            stmt.setLong(3, user.getFinancialInformation().getCvc());
            java.sql.Date sqlDate = new java.sql.Date(user.getFinancialInformation().getExpiry_date().getTime());
            stmt.setDate(4, sqlDate);

            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            return result;
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            connector.closeStmt(stmt);
            connector.closeCon(conn);
        }
    }

}
