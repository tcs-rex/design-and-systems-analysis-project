package DAO;

import model.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Represents a class that makes some models available for controllers
 */
public class LoaderDAO {
	/**
	 * ConnectionURL instance
	 */
	private String connectionURL;

	/**
	 * CoonectionUser instance
	 */
    private String connectionUser;

	/**
	 * connectionPassword instance.
	 */
    private String connectionPassword;

	/**
	 * LoaderDAO constructor.
	 * @param connectionURL
	 * @param connectionUser
	 * @param connectionPassword
	 */
    public LoaderDAO(String connectionURL, String connectionUser, String connectionPassword) {
        this.connectionURL = connectionURL;
        this.connectionUser = connectionUser;
        this.connectionPassword = connectionPassword;
    }

	/**
	 * Method to for building the theatre catalogue.
	 * @return
	 */
	public TheatreCatalogue loadModel() {

		try {
			// Create the connection and statement objects
			Connection conn = DriverManager.getConnection(
				connectionURL,
				connectionUser, 
				connectionPassword
			);
			
			// For every theatre, load it's movies
				// For every movie load it's showtimes
					// For every showtime load it's seats
			ArrayList<Theatre> theatres = getTheatres(conn);
			TheatreCatalogue thCat = new TheatreCatalogue(theatres);
			return thCat;
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return null;
	}

	/**
	 * Method that select row in the theatre table.
	 * @param conn
	 * @return theatres
	 * @throws SQLException
	 */
	public ArrayList<Theatre> getTheatres(Connection conn) throws SQLException {
		// Find all theatres
		Statement statement = conn.createStatement();
		ResultSet theatreResults = statement.executeQuery("SELECT * FROM theatre ORDER BY theatre_id ASC");
		ArrayList<Theatre> theatres = new ArrayList<Theatre>();
		while (theatreResults.next()) {
			// For every theatre load it's movies
			int theatre_id = theatreResults.getInt("theatre_id");
			ArrayList<Movie> movies = getMovies(conn, theatre_id);
			
			String theatre_name = theatreResults.getString("theatre_name");
			Theatre theatre = new Theatre(theatre_id, theatre_name, movies);
			theatres.add(theatre);
		}

		return theatres;
	}

	/**
	 * Method that selects rows from movie table based on the theatre_id.
	 * @param conn
	 * @param theatre_id
	 * @return
	 * @throws SQLException
	 */

	public ArrayList<Movie> getMovies(Connection conn, int theatre_id) throws SQLException {
		String movieQueryString = "SELECT * FROM movie WHERE theatre_id = ? ORDER BY movie_id ASC";
		PreparedStatement movieQuery = conn.prepareStatement(movieQueryString);
		movieQuery.setInt(1, theatre_id);
		ResultSet movieResults = movieQuery.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		while (movieResults.next()) {
			// For every movie find it's showtimes
			int movie_id = movieResults.getInt("movie_id");
			ArrayList<Showtime> showtimes = getShowtimes(conn, movie_id);

			String title = movieResults.getString("title");
			String genre = movieResults.getString("genre");
			Date release_date = movieResults.getDate("release_date");
			Movie movie = new Movie(movie_id, title, genre, release_date, theatre_id, showtimes);
			movies.add(movie);
		}

		return movies;
	}

	/**
	 * Method that that selects rows in showtime table based on movie_id.
	 * @param conn
	 * @param movie_id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Showtime> getShowtimes(Connection conn, int movie_id) throws SQLException {
		String showtimeQueryString = "SELECT * FROM showtime WHERE movie_id =? ORDER BY showtime_id ASC";
		PreparedStatement showtimeQuery = conn.prepareStatement(showtimeQueryString);
		showtimeQuery.setInt(1, movie_id);
		ResultSet showtimeResults = showtimeQuery.executeQuery();
		ArrayList<Showtime> showtimes = new ArrayList<>();

		while (showtimeResults.next()) {
			// For every showtime find it's seats
			int showtime_id = showtimeResults.getInt("showtime_id");
			ArrayList<ArrayList<Seat>> seats = getSeats(conn, showtime_id);

			Date st_date = showtimeResults.getDate("st_date");
			Time st_time = showtimeResults.getTime("st_time");
			Showtime showtime = new Showtime(showtime_id, st_date, st_time, movie_id, seats);
			showtimes.add(showtime);
		}

		return showtimes;
	}

	/**
	 * Method that selects rows from seat table based on the showtime_id;
	 * @param conn
	 * @param showtime_id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ArrayList<Seat>> getSeats(Connection conn, int showtime_id) throws SQLException {
		String seatQueryString = "SELECT * FROM seat WHERE showtime_id =? ORDER BY seat_row ASC, seat_column ASC";
		PreparedStatement seatQuery = conn.prepareStatement(seatQueryString);
		seatQuery.setInt(1, showtime_id);
		ResultSet seatResults = seatQuery.executeQuery();

		ArrayList<ArrayList<Seat>> seats = new ArrayList<ArrayList<Seat>>();
		seatResults.next();
		while (true) {
			int currRowNumber = seatResults.getInt("seat_row");
			ArrayList<Seat> currRowSeats = new ArrayList<Seat>();
			while (seatResults.getInt("seat_row") == currRowNumber) {
				int seat_id = seatResults.getInt("seat_id");
				int seat_row = seatResults.getInt("seat_row");
				int seat_column = seatResults.getInt("seat_column");
				// int showtime_id = seatResults.getInt("showtime_id");
				boolean is_taken = seatResults.getBoolean("is_taken");
				Seat seat = new Seat(seat_id, seat_row, seat_column, showtime_id, is_taken);
				currRowSeats.add(seat);
				seatResults.next();
				if (seatResults.isLast()) {
					seat_id = seatResults.getInt("seat_id");
					seat_row = seatResults.getInt("seat_row");
					seat_column = seatResults.getInt("seat_column");
					showtime_id = seatResults.getInt("showtime_id");
					is_taken = seatResults.getBoolean("is_taken");
					seat = new Seat(seat_id, seat_row, seat_column, showtime_id, is_taken);
					currRowSeats.add(seat);
					break;
				}
			}
			seats.add(currRowSeats);
			if (seatResults.isLast()) {
				break;
			}
		}

		return seats;
	}

	/**
	 *
	 * @param email
	 * @param thCat
	 * @return
	 */
	public User loadUser(String email, TheatreCatalogue thCat) {
		try {
			// Create the connection and statement objects
			Connection conn = DriverManager.getConnection(
				connectionURL,
				connectionUser, 
				connectionPassword
			);
			// Test if user exists already
			String userQueryString = "SELECT * FROM users WHERE email = ?";
			PreparedStatement userQuery = conn.prepareStatement(userQueryString);
			userQuery.setString(1, email);
			ResultSet userResults = userQuery.executeQuery();

			if (!userResults.next()) {
                return null;
			}

			int user_id = userResults.getInt("user_id");
			String first_name = userResults.getString("first_name");
			String last_name = userResults.getString("last_name");
			email = userResults.getString("email");
			boolean is_reg_user = userResults.getBoolean("is_reg_user");
			String user_password = userResults.getString("user_password");
			Date registration_date = userResults.getDate("registration_date");

			User user;
			if (is_reg_user) {
				// Load financial information
				String financialInformationQueryString = "SELECT * FROM financial_information WHERE user_id = ?";
				PreparedStatement financialQuery = conn.prepareStatement(financialInformationQueryString);
				financialQuery.setInt(1, user_id);
				ResultSet financialResults = financialQuery.executeQuery();
				financialResults.next();
				FinancialInformation financialInformation;

				long cardNumber = financialResults.getLong("card_number");
				int cvc = financialResults.getInt("cvc");
				Date expiry_date = financialResults.getDate("expiry_date");
				financialInformation = new FinancialInformation(cardNumber, cvc, expiry_date);


				// Load payment
				String paymentQueryString = "SELECT * FROM annual_payment WHERE user_id =?";
                PreparedStatement paymentQuery = conn.prepareStatement(paymentQueryString);
                paymentQuery.setInt(1, user_id);
                ResultSet paymentResults = paymentQuery.executeQuery();
				paymentResults.next();

				int payment_id = paymentResults.getInt("payment_id");
				String payment_type = paymentResults.getString("payment_type");
				Date payment_date = paymentResults.getDate("payment_date");
				double amount_paid = paymentResults.getDouble("amount_paid");
				String payment_status = paymentResults.getString("payment_status");
				Payment annualPayment = new Payment(payment_id, payment_type, payment_date, amount_paid, payment_status);

				user = new RegisteredUser(
					user_id,
					first_name,
					last_name,
					email,
					user_password,
					is_reg_user,
					registration_date,
					financialInformation,
					annualPayment
				);

			} else {
				user = new RegularUser(
					user_id,
					first_name,
					last_name,
					email,
					is_reg_user
				);
			}
			
			// Load all tickets for the user regardless of user type
						// For each ticket
						//	  load the payment
						//		connect them to the theatre, movie, showtime, seat
			String ticketQueryString = "SELECT * FROM ticket WHERE user_id = ?";
			PreparedStatement ticketQuery = conn.prepareStatement(ticketQueryString);
			ticketQuery.setInt(1, user_id);
			ResultSet ticketResults = ticketQuery.executeQuery();
			ArrayList<Ticket> tickets = new ArrayList<Ticket>();
			while (ticketResults.next()) {
				int ticket_id = ticketResults.getInt("ticket_id");
				int movie_id = ticketResults.getInt("movie_id");
				int theatre_id = ticketResults.getInt("theatre_id");
				int showtime_id = ticketResults.getInt("showtime_id");
				int seat_id = ticketResults.getInt("seat_id");
				
				Theatre theatre = thCat.searchTheatreById(theatre_id);
				Movie movie = theatre.searchMovieById(movie_id);
				Showtime showtime = movie.searchShowtimeById(showtime_id);
				Seat seat = showtime.searchSeatById(seat_id);

				String paymentQueryString = "SELECT * FROM ticket_payment WHERE ticket_id = ?";
				PreparedStatement paymentQuery = conn.prepareStatement(paymentQueryString);
				paymentQuery.setInt(1, ticket_id);
				ResultSet paymentResults = paymentQuery.executeQuery();
				
				paymentResults.next();
				int payment_id = paymentResults.getInt("payment_id");
				String payment_type = paymentResults.getString("payment_type");
				Date payment_date = paymentResults.getDate("payment_date");
				double amount_paid = paymentResults.getDouble("amount_paid");
				String payment_status = paymentResults.getString("payment_status");
				double credit_amount = paymentResults.getDouble("credit_amount");
				Date credit_expiry_date = paymentResults.getDate("credit_expiry_date");

				Payment payment = new Payment(
					payment_id,
					payment_type,
					payment_date,
					amount_paid,
					payment_status,
					credit_amount,
					credit_expiry_date
				);
				
				Ticket ticket = new Ticket(ticket_id, user, movie, theatre, showtime, seat, payment);
				tickets.add(ticket);
			}	
			user.setTickets(tickets);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Observer> loadRegisteredUserEmails() {

		ArrayList<Observer> userEmailsList = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(
				connectionURL,
				connectionUser,
				connectionPassword
			);

			String registeredEmailsQueryString = "SELECT * FROM users WHERE is_reg_user = true";
			Statement registeredEmailsStatement = conn.createStatement();
			ResultSet registeredEmailsResults = registeredEmailsStatement.executeQuery(registeredEmailsQueryString);

			while (registeredEmailsResults.next()) {
				String email = registeredEmailsResults.getString("email");
				RegisteredUserEmail userEmail = new RegisteredUserEmail(email);
				userEmailsList.add(userEmail);
			}

			return userEmailsList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return null;
	}

	public static void main (String[] args) {
	}
}
