package controller;
import model.Loader;
import view.*;
import DAO.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * MainController class - handles actions from homePage (including drop-down
 * registration fields) and cancelPage. Switches views, manages sub-contollers,
 * and handles some data validation.
 *
 */
public class MainController {

	/**
	 * views and view manager
	 */
	ViewManager viewManager;
	HomePage homePage;
	CancelPage cancelPage;
	MoviePage moviePage;
	
	/**
	 * controllers
	 */
	MovieController movieController;
	PaymentController paymentController;
	
	/**
	 * Model
	 */
	Loader loader;
	
	/**
	 * temporary holder variable
	 */
	String email;
	

	public MainController(Loader loader, ViewManager viewManager) {
		this.loader = loader;
		this.viewManager = viewManager;
	}
	
	/**
	 * run method loads views and controllers, and sets action listeners
	 */
	public void run() {
		viewManager.setMainController(this);
		homePage = viewManager.loadHomePage();
		
		homePage.setCancelListener((ActionEvent e) -> {
			cancelPage = viewManager.loadCancelPage();
			setCancelPageListeners();
			homePage.hide();
			cancelPage.show();
			
		});
		
		homePage.setGuestListener((ActionEvent e) -> {
			// load guest user
			loader.loadGuestInLoader();
			moviePage = viewManager.loadMoviePage();
			getMovieController();
			homePage.hide();
			moviePage.show();
		});
		
		homePage.setSignInListener((ActionEvent e) -> {
			if(checkUser() == true) {
				// load registered user in Loader
				loader.loadUserInLoader(email);
				moviePage = viewManager.loadMoviePage();
				getMovieController();
				homePage.hide();
				moviePage.show();
			}
			else
				homePage.popUpDialog("User not found. Please try again, or sign-up below...");
		});


		homePage.setRegisterListener((ActionEvent e) -> {
			
			// new user register fields
			String firstName = homePage.getFName();
			String lastName = homePage.getLName();
			String newEmail = homePage.getEmailSignUp();
			String newPass = homePage.getPasswordSignUp(); 
			String card = homePage.getcardNumber();
			String exp = homePage.getexpirayDate();
			String cvc = homePage.getCVCNumber();
			
			
			/**
			 * check if registered user is trying to re-register	
			 */
			boolean existingUser = loader.getUserDAO().checkGuestEmail(newEmail);
			
			if(existingUser)
				homePage.popUpDialog("User already exists. Please log-in above.");
			
			/**
			 * registration data format validation via helper method "checkInput" below 	
			 */
			boolean validFirstName = checkInput(firstName, "^[A-Z][a-z]{1,28}$");	
			if(!validFirstName)
				homePage.popUpDialog("Invalid first name - please re-enter (no spaces, numbers, or special chars)");
			
			boolean validLastName = checkInput(lastName, "^[A-Z][a-z]{1,28}$");	
			if(!validLastName)
				homePage.popUpDialog("Invalid last name - please re-enter (no spaces, numbers, or special chars)");
			
			boolean validEmail = checkInput(newEmail, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");	
			if(!validEmail)
				homePage.popUpDialog("Invalid email - please re-enter");
			
			boolean validPass = checkInput(newPass, "^[a-zA-Z0-9_]{1,28}$");	
			if(!validPass)
				homePage.popUpDialog("Invalid password - please re-enter");
				
			boolean validCard = checkInput(card, "\\d{16}");
			if(!validCard)
				homePage.popUpDialog("Invalid card - please enter a 16-digit card number");
			
			boolean validCvc = checkInput(cvc, "\\d{3}");
			if(!validCvc)
				homePage.popUpDialog("Invalid cvc - please enter a 3-digit cvc");
			
			boolean validExp = checkInput(exp, "\\d{2}/\\d{2}");
			if(!validExp)
				homePage.popUpDialog("Invalid expiry, please enter a 4-digit expiry (e.g. 12/23 for Dec 2023)");
			
			/**
			 * attempt to register new user if above checks pass			
			 */
			if(!existingUser && validFirstName && validLastName && validEmail && validPass && validCard && validCvc && validExp) {
				
				Long cardNum = Long.parseLong(card);
				int cvcNum = Integer.parseInt(cvc);
				int MM = Integer.parseInt(exp.substring(0, 2));
				int YY = Integer.parseInt(exp.substring(3, 5));	
				Date expDate = new Date(YY, MM, 01);
				
				try {
					if(loader.saveNewRegisteredUserToDatabase(firstName, lastName, newEmail, newPass, cardNum, cvcNum, expDate)) {
						homePage.popUpDialog("Success! Please login in with your credentials.");
					}
				}
				catch(RuntimeException except) {
					homePage.popUpDialog("Credit card number already exists. Please log-in above.");
					return;
				}
			}	
		});
	}
	
	/**
	 * checkUser() - registered user login authentication
	 * @return true / false
	 */
	private boolean checkUser() {
		email = homePage.getEmailSignIn();
		String pass = homePage.getPasswordSignIn();
		return loader.getUserDAO().AuthenticateUser(email, pass);
	}
	
	/**
	 * main controller to handles actions on cancel page
	 */
	private void setCancelPageListeners() {
		cancelPage.setHomePageListener((ActionEvent e) -> {
			cancelPage.hide();
			homePage.show();
		});
		
		cancelPage.setSubmitListener((ActionEvent e) -> {
			
			loader.loadUserInLoader(cancelPage.getEmailCancel());
			
			if(loader.getUser() == null) {
				cancelPage.popUpDialog("no user found. Please try again.");
				return;
			}
			
			Boolean check = false;
			try {
				Integer temp =Integer.parseInt(cancelPage.getTicketCancel());
				check = loader.cancelTicketAndSaveToDatabase(temp);
			} catch(NumberFormatException e1) {
				cancelPage.popUpDialog("Please enter ticket number as an integer.");
				return;
			}
			catch (IllegalStateException e2) {
				cancelPage.popUpDialog("Ticket is already refunded.");
				return;
			}
			catch (Exception e3) {
				cancelPage.popUpDialog("Wrong input, please try again.");
				e3.printStackTrace();
				return;
			} 
			
			if(check) {

				if (loader.getUser().Is_reg_user()) {
					cancelPage.popUpDialog("your ticket has been canceled. you have 100% of ticket price as a credit");
				}
				else {
					cancelPage.popUpDialog("your ticket has been canceled. you have 85% of ticket price as a credit");
				}

			}
			else {
				cancelPage.popUpDialog("you cannot cancel the ticket: no ticket found or you have passed the cancellation time.");
			}

		});
		
	}
	
	/**
	 * creates MovieController if it doesn't already exist
	 * and sets refs
	 */
	private void getMovieController() {
		if (movieController == null) {
			movieController = new MovieController(moviePage, loader);
			movieController.setHomePage(homePage);
			movieController.setMainController(this);
		}
	}
	
	/**
	 * creates PaymentController if it doesn't already exist
	 * and sets refs
	 */
	public void getPaymentController() {
		if (paymentController == null) 
			paymentController = new PaymentController(viewManager.loadPaymentPage(), loader, homePage);
			paymentController.setMainController(this);
			
	}
	
	/**
	 * helper to check user registration field inputs
	 * @param input : String
	 * @param pattern : String
	 * @return true/false
	 */
		private boolean checkInput(String input, String pattern) {
			// check email format (ref. w3 schools & https://regexr.com)
			Pattern pat = Pattern.compile(pattern);
		    Matcher matcher = pat.matcher(input);
		    boolean matchFound = matcher.find();
		    if(matchFound) {
		    	System.out.println(input + " is valid");
		    	return true;
		    } else {
		    	System.out.println(input + " invalid");
		    	return false;
		    }
		}
	

}
