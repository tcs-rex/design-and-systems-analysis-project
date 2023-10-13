//package DAO;
//
//import model.Showtime;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ShowTimeDAO {
//
//    private static final String DELETE = "DELETE FROM showtime WHERE showtime_id =?";
//    private static final String FIND_ALL = "SELECT * FROM showtime ORDER BY showtime_id";
//    private static final String INSERT = "INSERT INTO showtime(showtime_id, st_date, st_time, movie_id) VALUES (?, ?, ?, ?)";
//    private static final String UPDATE = "UPDATE showtime SET showtime_id=?, st_date=?, st_time=?, movie_id=?, showtime_id=? WHERE showtime_id=?";
//
//
//
//
//    //Helper class
//    private Showtime parameters(ResultSet rs) throws SQLException {
//        Showtime showtime = new Showtime();
//        showtime.setShowtime_id(rs.getInt("showtime_id"));
//        showtime.setDate(rs.getDate("st_date"));
//        showtime.setTime(rs.getTime("st_time"));
//        showtime.setMovie_id(rs.getInt("movie_id"));
//        return showtime;
//    }
//
//    private void forUpdate(Showtime showtime, PreparedStatement stmt) throws SQLException {
//        stmt.setInt(1, showtime.getMovie_id());
//        stmt.setDate(2, showtime.getDate());
//        stmt.setTime(3, showtime.getTime());
//        stmt.setInt(4, showtime.getMovie_id());
//    }
//
//    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();
//
//    /* Select all ticket from the DB.*/
//    public List<Showtime> findAllShowtime() {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        List<Showtime> list = new ArrayList<>();
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(FIND_ALL);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Showtime showtime = parameters(rs);
//                list.add(showtime);
//            }
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            connector.closeStmt(stmt);
//            connector.closeCon(conn);
//        }
//
//        return list;
//    }
//
//    /*Create new showtime in DB*/
//    public int addShowtime(Showtime showtime)  {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
//            forUpdate(showtime, stmt);
//
//
//            int result = stmt.executeUpdate();
//            ResultSet rs = stmt.getGeneratedKeys();
//
//            if (rs.next()) {
//                showtime.setShowtime_id(rs.getInt(1));
//            }
//
//            return result;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            connector.closeStmt(stmt);
//            connector.closeCon(conn);
//        }
//    }
//
//    /* Delete showtime from DB */
//    public int delete(int showtime_id) { //Tested
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(DELETE);
//            stmt.setInt(1, showtime_id);
//
//            return stmt.executeUpdate();
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            connector.closeStmt(stmt);
//            connector.closeCon(conn);
//        }
//    }
//
//    /* Update showtime */
//    public int updateShowtime(Showtime ticket) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(UPDATE);
//            forUpdate(ticket, stmt);
//
//            return stmt.executeUpdate();
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            connector.closeStmt(stmt);
//            connector.closeCon(conn);
//        }
//    }
//}
