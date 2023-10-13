package controller;
import view.HomePage;
import view.PaymentPage;
import model.Loader;
import model.Payment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.util.Date;

public class PaymentController {

	/**
	 * view, mode, and controller refs
	 */
	PaymentPage paymentPage;
	Loader loader;
	MainController mainController;
	HomePage homePage;
	
	/**
	 * variables to faciliate pay amount calculation
	 * and credit use. ticket price is fixed at $15.00
	 */
	Double ticketPrice = 15.0;
	Double credit = 0.0;
	Double payAmt = 0.0;
	
	/**
	 * helper variable to prevent program crash
	 * on subsequent pay button clicks after successful 
	 * purchase. (And would be used to implement routing
	 * customer after successful payment)
	 */
	Payment sessionPayment = null; 
	
	/**
	 * button counters, to track usage and path
	 */
	private int voucherButtonCount = 0;
	private int paymentButtonCount = 0;


	public PaymentController(PaymentPage paymentPage, Loader loader, HomePage homePage) {
		this.paymentPage = paymentPage;
		this.loader = loader;
		this.homePage = homePage;
	
		voucherMsg();
		setVoucherButtonListener();
		setPayButtonListener();
		
	}
	
	/**
	 * method listens to pay button and triggers action(s).
	 * actions depend on variables (e.g. Registered user vs. guest,
	 * credit or no-credit, etc.)
	 */
	private void setPayButtonListener() {
		paymentPage.setPayListener((ActionEvent e) -> {
			
			// feature to return to homePage and start process again - work-in-progress
//			 if(sessionPayment != null) {
//				 paymentPage.popUpDialog("Your payment was successful...Press ok and you will be redirected to homePage... ");
//				 try {
//					Thread.sleep(2000);
//					homePage.show();
//					paymentPage.hide();
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}		
//			 }
			
			
			// Registered User
			if(loader.getUser().Is_reg_user() && sessionPayment == null) {
				
				if(voucherButtonCount == 0) {
					System.out.println("Voucher");
					payAmt = ticketPrice;
					this.sessionPayment = loader.getTicket().makePayment(new Date(), payAmt);
				}
				
				if(voucherButtonCount > 0) {
					if(payAmt == 0.0) {
						loader.getTicket().makePayment(new Date(), payAmt);
						loader.getUser().useCredit(ticketPrice);
					}
					else {
						payAmt = ticketPrice - credit;
						loader.getTicket().makePayment(new Date(), payAmt);
						loader.getUser().useCredit(credit);
					}
				}
				loader.saveRegisteredUsersTicketToDatabase();
				
				// display ticket and receipt
				paymentPage.setReceipt(loader.getTicket().getReceipt());
				
				// email and pop-up 
				paymentPage.popUpDialog("Thank-you for your business!\nYour ticket has been paid.\nCheck your "
						+ "email for your ticket and receipt.");
			}
			
			
			// Guest path (or registered user posing as guest)
			if(!loader.getUser().Is_reg_user() && sessionPayment == null) {
				// get user information
				String email = paymentPage.getEmail();
				String card = paymentPage.getCard();
				String cvc = paymentPage.getCVC();
				String exp = paymentPage.getExpiry();
				
				// check for valid and existing use email
				Boolean validEmail = checkInput(email, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
				Boolean existingUser = loader.getUserDAO().checkGuestEmail(email);
				
				if (validEmail) {
					if(existingUser) {	
						loader.loadUserInLoader(email);
						credit = loader.getUser().getAvailableCredit();
						if(credit == null)
							credit = 0.0;
					}
					else {
						loader.getUser().setEmail(email);
					}
				}
				else {
					paymentPage.popUpDialog("Invalid email - please re-enter");
				}
					
				// check payment information
				boolean cardOk = checkInput(card, "\\d{16}");
				boolean cvcOk = checkInput(cvc, "\\d{3}");
				boolean expOk = checkInput(exp, "\\d{2}/\\d{2}");
		
				if (!cardOk)				
					paymentPage.popUpDialog("Invalid - please enter 16-digit visa #");				
				if (!cvcOk)
					paymentPage.popUpDialog("Invalid - please enter a 3-digit cvc");		
				if (!expOk)
					paymentPage.popUpDialog("Invalid - please enter a valid 4-digit expiry\n"
							+ " (e.g. 12/23 for Dec 2023)");
				
				if(credit >= ticketPrice) {
					payAmt = 0.0;
				}
				else {
					payAmt = ticketPrice - credit;
				}
				
				// make payment & fill ticket
				if(validEmail && cardOk && cvcOk && expOk) {
					this.sessionPayment = loader.getTicket().makePayment(new Date(), payAmt);
					// update credit (upon confirmation, assuming adequate funds)
					if(payAmt == 0.0)
						loader.getUser().useCredit(0.0);
					else
						loader.getUser().useCredit(credit);
					
					if(existingUser)
						loader.saveRegisteredUsersTicketToDatabase();
					else	
						loader.saveGuestUserAndTicketToDatabase();
					
					// display ticket and receipt
					paymentPage.setReceipt(loader.getTicket().getReceipt());
					
					// pop-up 
					paymentPage.popUpDialog("Thank-you for your business!\nYour ticket has been paid.\nCheck your "
							+ "email for your ticket and receipt.");
				}
				else
					paymentPage.popUpDialog("Invalid entry(ies) - please recheck your inputs.");
			
				}
		});
	}
	
	/**
	 * method listens to voucher button.
	 * method adjusts payment for registered user.
	 * for guest, it just messages (via voucherMsg()) that we will apply credit if there is any
	 * in system (based on email) at time of payment
	 */
	private void setVoucherButtonListener() {
		// claim voucher 
		paymentPage.setVoucherListener((ActionEvent e) -> {
			if(voucherButtonCount == 0) {
				if(loader.getUser().Is_reg_user()) {
					if(credit >= ticketPrice) {
						paymentPage.popUpDialog("Credit of $" + ticketPrice + " will be applied at payment!");
						payAmt = 0.0;
					}
					else {
						paymentPage.popUpDialog("Credit of $" + credit + " will be applied at payment!");
						payAmt = ticketPrice - credit;
					}
				}
				else
					voucherMsg();
				
				voucherButtonCount += 1;
			}
			else
				paymentPage.popUpDialog("Voucher already applied (if available).");
			
		});
	}
	
	/**
	 * method fetches credit for registered user and lets them know how much.
	 * And lets guest know we will apply credit if available
	 */
	private void voucherMsg() {
		
		if(loader.getUser().Is_reg_user()) {
			this.credit = loader.getUser().getAvailableCredit();
			paymentPage.popUpDialog("You have a credit on file of $" + 
					this.credit + ". Click below to claim!");
		}
		if(!loader.getUser().Is_reg_user())
			paymentPage.popUpDialog("Guest log-in: any credit on file is applied automatically to purchase.");
	}
	
	/**
	 * helper to check user inputs
	 * @param input
	 * @param pattern
	 * @return
	 */
	private Boolean checkInput(String input, String pattern) {
		// check email format (ref. w3 schools & https://regexr.com)
		Pattern emailPattern = Pattern.compile(pattern,
				Pattern.CASE_INSENSITIVE);
	    Matcher matcher = emailPattern.matcher(input);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	    	System.out.println(input + " is valid");
	    	return true;
	    } else {
	    	System.out.println(input + " invalid");
	    	return false;
	    }
	}
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
}
