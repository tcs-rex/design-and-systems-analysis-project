package model;
import controller.*;

import java.util.ArrayList;
import java.util.Date;

import DAO.*;

public class Loader {

    /**
     * The theatre catalogue that contains all theatres.
     */
    private TheatreCatalogue thCat;
    /**
     * The guest or registered user using the movie theatre ticket reservation system.
     */
    private User user;
    /**
     * The ticket that is being built by the user.
     */
    private Ticket ticket;
    /**
     * Utility object to load information from the database.
     */
    private LoaderDAO loaderDAO;
    /**
     * Utility object to load and save user information to the database.
     */
    private UserDAO userDAO;
    /**
     * Utility object to load and save financial information to the database.
     */
    private FinancialnfoDAO financialDAO;
    /**
     * Utility object to load and save payment information to the database.
     */
    private PaymentDAO paymentDAO;
    /**
     * Utility object to load and save seat information to the database.
     */
    private SeatDAO seatDAO;
    /**
     * Utility object to load and save ticket information to the database.
     */
    private TicketDAO ticketDAO;
    /**
     * Subject in the Observer pattern that contains news sent to registered users.
     */
    private News news;


    /**
     * 
     * @param loaderDAO
     * @param userDAO
     * @param financialDAO
     * @param paymentDAO
     * @param seatDAO
     * @param ticketDAO
     */
    public Loader(
        LoaderDAO loaderDAO, 
        UserDAO userDAO,
        FinancialnfoDAO financialDAO,
        PaymentDAO paymentDAO,
        SeatDAO seatDAO,
        TicketDAO ticketDAO
    ) {
        ticket = new Ticket();
        news = new News();

        this.loaderDAO = loaderDAO; 
        this.userDAO = userDAO;
        this.financialDAO = financialDAO;
        this.paymentDAO = paymentDAO;
        this.seatDAO = seatDAO;
        this.ticketDAO = ticketDAO;

        news.setObservers(loaderDAO.loadRegisteredUserEmails());
        thCat = loaderDAO.loadModel();
    }

    /**
     * For saving a newly registered user to the database. Saves
     * the registered user, their financial information, annual payment,
     * and adds the users email to the news subject as an observer.
     * 
     * @return
     */
    public boolean saveNewRegisteredUserToDatabase(String firstName, String lastName, String email, String password, long cardNubmer, int cvc, Date expiryDate) {
        RegisteredUser saveUser = new RegisteredUser(firstName, lastName, email, true, password, cardNubmer, cvc, expiryDate);
        saveUser.makeAnnualPayment(new Date(), 20);
        if (userDAO.createRegisteredUser(saveUser) != 1) {
            return false;
        }
        int userId = userDAO.getUserId(saveUser.getEmail());
        saveUser.setUser_id(userId);
        
        if (financialDAO.createFinancialInformation(saveUser) != 1) {
            return false;
        }
        if (paymentDAO.ceateAnnualPayment(saveUser) != 1) {
            return false;
        }

        RegisteredUserEmail userEmail = new RegisteredUserEmail(saveUser.getEmail());
        news.registerObserver(userEmail);
        
        return true;
    }

    /**
     * For saving a guest user, their purchased ticket, ticket payment to the database.
     * Also updates the seats status in the database to taken.
     * 
     * @return
     */
    public boolean saveGuestUserAndTicketToDatabase() {
        RegularUser saveUser = (RegularUser) user;
        if (userDAO.createRegularUser(saveUser) != 1) {
            return false;
        }
        int userId = userDAO.getUserId(saveUser.getEmail());
        saveUser.setUser_id(userId);
        if (ticketDAO.creatTicket(ticket) != 1) {
            return false;
        }
        int ticketId = ticketDAO.getTicketId(ticket);
        ticket.setTicket_id(ticketId);
        if (paymentDAO.ceateTicketPayment(ticket) != 1) {
            return false;
        }
        if (seatDAO.updateSeat(ticket.getSeat()) != 1) {
            return false;
        }

        return true;
    }

    /**
     * For saving a registered user's purchased ticket and ticket payment to the database.
     * Also updates the seats status to taken in the databse.
     * 
     * @return
     */
    public boolean saveRegisteredUsersTicketToDatabase() {
        if (ticketDAO.creatTicket(ticket) != 1) {
            return false;
        }
        int ticketId = ticketDAO.getTicketId(ticket);
        ticket.setTicket_id(ticketId);
        if (paymentDAO.ceateTicketPayment(ticket) != 1) {
            return false;
        }
        if (seatDAO.updateSeat(ticket.getSeat()) != 1) {
            return false;
        }
        for (Ticket tick: user.getTickets()) {
            paymentDAO.updateTicketPayment(tick);
        }

        return true;
    }

    /**
     * For cancelling a ticket and saving it's payment to the database.
     * Updates the seat to not taken in the database.
     * 
     * @param ticketNumber
     * @return
     */
    public boolean cancelTicketAndSaveToDatabase(int ticketNumber) {
        if (user == null) {
            return false;
        }
        Ticket ticketToCancel = user.searchTicket(ticketNumber);
       
        if (ticketToCancel == null) {
            return false;
        }
        ticketToCancel.cancelTicket();
        if (paymentDAO.updateTicketPayment(ticketToCancel) != 1) {
            return false;
        }
        if (seatDAO.updateSeat(ticketToCancel.getSeat()) != 1) {
            return false;
        }

        return true;
    }
    
    /**
     * For notifying the observers in news 
     * 
     * @param newNews
     */
    public void notifyAllObservers(String newNews) {
        if (newNews == null) {
            news.notifyAllObservers();
        }
        news.setNews(newNews);
    }
    
    /**
     * For loading registered user from the database  ( pop-up on null )
     * @param email
     */
    public void loadUserInLoader(String email) {
        if (loaderDAO != null && thCat != null) {
            user = loaderDAO.loadUser(email, thCat);
        }
        ticket.setUser(user);
    }

    /**
     * For loading guest user from the database.
     */
    public void loadGuestInLoader() {
        if (loaderDAO != null && thCat != null) {
	        user = new RegularUser();
	        ticket.setUser(user);
        }
    }
    
    /**
     * Returns an array of theatre names.
     * 
     * @return
     */
    public String[] getTheatres() { 
    	ArrayList<String> theatre = new ArrayList<String>();
    	for(Theatre t: thCat.getTheatres()) {
    		theatre.add(t.getTheatre_name());
    	}
    	String[] arr = new String[theatre.size()];
        arr = theatre.toArray(arr);
    	return arr;
    }
    
    /**
     * Returns an array of movie names.
     * 
     * @return
     */
    public String[] getMovieTitles() {
    	ArrayList<String> movies = new ArrayList<String>();
    	Theatre th = thCat.getTheatres().get(0);
    	for(Movie m: th.getMovies()) {
    		movies.add(m.getTitle());
    	}
    	String[] arr = new String[movies.size()];
        arr = movies.toArray(arr);
    	return arr;
    }
    
    /**
     * Returns an array of string representation of the showtime date and times
     * 
     * @return
     */
    public String[][] getShowtimes() {
    	ArrayList<ArrayList<String>> showtime = new ArrayList<ArrayList<String>>();
    	ArrayList<String> movie = null;
    	Theatre th = thCat.getTheatres().get(0);
    	for(Movie m: th.getMovies()) {
    		movie = new ArrayList<String>();
    		for(Showtime sh: m.getShowtimes()) {
    			String date = sh.getDate().toString();
    			String time = sh.getTime().toString();
    			String datetime = date + ", " + time;
    			movie.add(datetime);
    		}
    		showtime.add(movie);
    	}
    	String[][] arr = new String[showtime.size()][];
    	for(int i=0; i<showtime.size(); i++) {
    		arr[i] = new String[showtime.get(i).size()];
    		showtime.get(i).toArray(arr[i]);
    	}
    	return arr;
    }
    
    /**
     * Return a 4d array representing the seat status for all movies
     * and showtimes.
     * 
     * @return
     */
    public Boolean[][][][] seatStatus() {
    	var status = new ArrayList<ArrayList<ArrayList<ArrayList<Boolean>>>>();
    	Theatre th = thCat.getTheatres().get(0);
    	for(Movie m: th.getMovies()) {
    		var movie = new ArrayList<ArrayList<ArrayList<Boolean>>>();
    		for(Showtime sh: m.getShowtimes()) {
    			var seat = new ArrayList<ArrayList<Boolean>>();
    			for(int i=0; i<3; i++) {
    				seat.add(new ArrayList<Boolean>());
    				for(int j=0; j<3; j++) {
    					Seat s = sh.getSeatByRowAndCol(i, j);
    					seat.get(i).add(s.isIs_taken());
    				}
    			}
    			movie.add(seat);
    		}
    		status.add(movie);
    	}
    	
    	
    	Boolean[][][][] arr = new Boolean[status.size()][][][];
    	for(int i=0; i<status.size(); i++) {
    		arr[i] = new Boolean[status.get(i).size()][][];
    		for(int j=0; j<status.get(i).size(); j++) {
    			arr[i][j] = new Boolean[status.get(i).get(j).size()][];
    			for(int k=0; k<status.get(i).get(j).size(); k++) {
    				arr[i][j][k] = new Boolean[status.get(i).get(j).get(k).size()];
    				status.get(i).get(j).get(k).toArray(arr[i][j][k]);
    			}			
    		}
    	}
    	return arr;
    	  
    }
    
    /**
     * Builds the ticket with the selected theatre name, movie name, showtime date and time string,
     * and seat selected.
     * 
     * @param theatre
     * @param movie
     * @param showtime
     * @param seat
     */
    public void buildObjects (String theatre, String movie, String showtime, int[] seat ) {
    	Theatre selectedTheatre = null;
    	for(Theatre th : thCat.getTheatres()) {
    		if (th.getTheatre_name().equals(theatre)) {
    			selectedTheatre = th;
    		}
    	}
    	ticket.setTheatre(selectedTheatre);
    	
    	
    	Movie selectedMovie = null;
    	for(Movie m: selectedTheatre.getMovies()) {
    		if (m.getTitle().equals(movie)) {
    			selectedMovie = m;
    		}
    	}
    	ticket.setMovie(selectedMovie);
    	
    	
    	Showtime selectedShowtime = null;
    	String date = showtime.split(", ")[0];
    	String time = showtime.split(", ")[1];
    	for(Showtime sh: selectedMovie.getShowtimes()) {
    		if(sh.getDate().toString().equals(date) && 
    				sh.getTime().toString().equals(time)) {
    			selectedShowtime = sh;
    		}
    	}
    	ticket.setShowtime(selectedShowtime);
    	
    	
		Seat selectedSeat = null;
		for (ArrayList<Seat> s : selectedShowtime.getSeats()) {
			for (Seat sea : s) {
				if (sea.getSeat_row() == seat[0] && sea.getSeat_column() == seat[1]) {
					selectedSeat = sea;
				}
			}
		}
		ticket.setSeat(selectedSeat);


    }

    
    // Getters and setters
    
    
    public TheatreCatalogue getTheatreCatalogue() {
        return thCat;
    }

    public User getUser() {
        return user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public LoaderDAO getLoaderDAO() {
        return loaderDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
    
    
    
}