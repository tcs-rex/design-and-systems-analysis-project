package controller;
import view.*;
import model.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * 
 * MovieController class - handles inputs from MoviePage, 
 * and some view switching
 *
 */
public class MovieController {

	/**
	 * views
	 */
	MoviePage moviePage;
	HomePage homePage;
	
	/**
	 * moviePage member variables
	 */
	String[] theatres;
	String[] movieTitles;
	String[][] showtimes;
	Boolean[][][][] seatStatus;
	
	/**
	 * main controller ref
	 */
	MainController mainController;
	
	/**
	 * model object ref
	 */
	Loader loader;
	
	
	public MovieController(MoviePage moviePage, Loader loader) {
		
		this.moviePage = moviePage;
		this.loader = loader;
		this.theatres = loader.getTheatres(); 
		this.movieTitles = loader.getMovieTitles();
		this.showtimes = loader.getShowtimes();
		this.seatStatus = loader.seatStatus();
		
		moviePage.setMoviePageComponents(theatres, movieTitles, showtimes, seatStatus);
		setMoviePageListeners();
	}
	
	/**
	 * setup of movie page listeners 
	 * and definition of triggered actions
	 */
	private void setMoviePageListeners() {
		// back to homePage 
		moviePage.setHomePageListener((ActionEvent e) -> {
			moviePage.hide();
			homePage.show();
		});
		
		// proceed to checkout
		moviePage.setCheckoutListener((ActionEvent e) -> {

			if(moviePage.getSelectedSeats().size() != 1 
					|| moviePage.getTheatre() == null
					|| moviePage.getMovie() == null
					|| moviePage.getShowtime() == null
					|| moviePage.getTheatre().equals("")){
				moviePage.popUpDialog("You must select a theatre, movie, showtime\n"
						+ "and select a single seat before proceeding.");
			}
			
			else {
				// build ticket
				loader.buildObjects(
						moviePage.getTheatre(), 
						moviePage.getMovie(),
						moviePage.getShowtime(),
						moviePage.getSeat());
				
				System.out.println(loader.getTicket());
				System.out.println(loader.getTicket());
				
				moviePage.hide();
				mainController.getPaymentController();
			}
		});
	}

	/**
	 * setter for setting homePage ref 
	 * (for navigation back)
	 * @param homePage
	 */
	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
	}

	/**
	 * setting mainController ref
	 * @param mainController
	 */
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
