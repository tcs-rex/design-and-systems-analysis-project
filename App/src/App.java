import java.awt.EventQueue;

import DAO.*;

import controller.MainController;
import model.Loader;

import view.ViewManager;


/**
 * 
 * Driver/main class for the movie theatre ticket reservation application
 *
 */
public class App {

    public static void main(String[] args) {
    	
    	/**
    	 * creates initial objects to start application and form relationships
    	 * (e.g. aggregation of view and model related objects within mainController,
    	 * loader aggregation of DAO objects)  
    	 */

        LoaderDAO loaderDAO = new LoaderDAO(SingletonDBConnector.DB_URL, SingletonDBConnector.USER_ID, SingletonDBConnector.PASSWORD);
        UserDAO userDAO = new UserDAO();
        FinancialnfoDAO financialDAO = new FinancialnfoDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        SeatDAO seatDAO = new SeatDAO();
        TicketDAO ticketDAO = new TicketDAO();
        Loader loader = new Loader(loaderDAO, userDAO, financialDAO, paymentDAO, seatDAO, ticketDAO);

        ViewManager viewManager = new ViewManager();
        MainController mainController = new MainController(loader, viewManager);

		EventQueue.invokeLater(
			() -> {mainController.run();}
		);
        

    }

}
