//package DAO;
//
//import model.Movie;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MovieDAO {
//
//    private static final String DELETE = "DELETE FROM movie WHERE movie_id=?";
//    private static final String FIND_ALL = "SELECT * FROM movie ORDER BY movie_id";
//    private static final String FIND_BY_MOVIE_ID = "SELECT * FROM movie WHERE movie_id=?";
//    private static final String INSERT = "INSERT INTO movie(movie_id, title, genre, release_date, theatre_id) VALUES(?, ?, ?, ?, ?)";
//    private static final String UPDATE = "UPDATE movie SET movie_id=?, title=?, genre=?, release_date=?, theatre_id=? WHERE movie_id=?";
//
//    //Helper class
//    private Movie parameters(ResultSet rs) throws SQLException {
//        Movie movie  = new Movie();
//        movie.setMovie_id(rs.getInt("movie_id"));
//        movie.setTitle(rs.getString("title"));
//        movie.setGenre(rs.getString("genre"));
//        movie.setRelease_date(rs.getDate("genre"));
//        movie.setTheatre_id(rs.getInt("theatre_id"));
//        return movie;
//    }
//
//    private void forUpdate(Movie movie, PreparedStatement stmt) throws SQLException {
//        stmt.setInt(1, movie.getMovie_id());
//        stmt.setString(2, movie.getTitle());
//        stmt.setString(3, movie.getGenre());
//        stmt.setDate(4, movie.getRelease_date());
//        stmt.setInt(5, movie.getTheatre_id());
//
//    }
//
//    SingletonDBConnector connector = SingletonDBConnector.getOnlyInstance();
//
//    /* Select all movies from the DB.*/
//    public List<Movie> findAllMovie() {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        List<Movie> list = new ArrayList<>();
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(FIND_ALL);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Movie movie = parameters(rs);
//                list.add(movie);
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
//    /*Create new movie in DB*/
//    public int addMovie(Movie movie) {   // Tested
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
//            forUpdate(movie, stmt);
//
//
//            int result = stmt.executeUpdate();
//            ResultSet rs = stmt.getGeneratedKeys();
//
//            if (rs.next()) {
//                movie.setMovie_id(rs.getInt(1));
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
//    /* Delete movie from DB */
//    public int delete(int movie_id) { //Tested
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(DELETE);
//            stmt.setInt(1, movie_id);
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
//    /* Update Movie */
//    public int updateMovie(Movie movie) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = connector.getConnection();
//            stmt = conn.prepareStatement(UPDATE);
//            forUpdate(movie, stmt);
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
