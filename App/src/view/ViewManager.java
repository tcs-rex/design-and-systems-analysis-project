package view;

import controller.MainController;


/**
 * ViewManager class composes other view classes
 * and it used to pass reference of other view pages to 
 * the related controller class
 */
public class ViewManager {

	/**
	 * reference to mainController
	 */
	MainController mainController;
	
	/**
	 * the reference to homePage class
	 */
	HomePage homePage = null;
	
	/**
	 * the reference to cancelPage class
	 */
	CancelPage cancelPage = null;
	
	/**
	 * the reference to moviePage class
	 */
	MoviePage moviePage = null;
	
	/**
	 * the reference to paymentPage class
	 */
	PaymentPage paymentPage = null;
	
	
	//	homePage view tied to mainController
	/**
	 * @return homePage
	 * method to compose the homePage and returns it
	 */
	public HomePage loadHomePage() {
		if (homePage == null) 
			homePage = new HomePage();
			
		return homePage;
	}
	

	/**
	 * @return cancelPage
	 * composes the cancelPage and returns it
	 */
	public CancelPage loadCancelPage() {
		if (cancelPage == null)
			cancelPage = new CancelPage();
		
		return cancelPage;
	}

	
	/**
	 * @return moviePage 
	 * composes the moviePage and returns it
	 */
	public MoviePage loadMoviePage() {
		if (moviePage == null)
			moviePage = new MoviePage();
		
		return moviePage;
	}

	/**
	 * @return paymentPage
	 * composes paymentPage and returns it
	 */
	public PaymentPage loadPaymentPage() {
		if (paymentPage == null)
			paymentPage = new PaymentPage();

		return paymentPage;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}




}


