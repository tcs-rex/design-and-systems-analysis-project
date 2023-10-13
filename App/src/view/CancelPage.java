package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * HomePage class is the GUI page for cancel ticket
 */
public class CancelPage {

	/**
	 * the main frame
	 */
	private JFrame frame;
	
	
	/**
	 * field to input the ticket number
	 */
	private JTextField ticketField;
	
	
	/**
	 * field to input the email
	 */
	private JTextField emailField;
	
	/**
	 * home page button
	 */
	private JButton homePageButton;
	
	/**
	 * cancel ticket button
	 */
	private JButton cancelSubmitButton;

	/**
	 * constructor
	 */
	public CancelPage() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(230, 230, 250));
		frame.setVisible(true);
		frame.setBounds(100, 100, 396, 174);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Label label = new Label("Ticket Number");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setBounds(36, 10, 110, 28);
		frame.getContentPane().add(label);

		Label label_1 = new Label("Email Address");
		label_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1.setBounds(36, 44, 110, 14);
		frame.getContentPane().add(label_1);

		ticketField = new JTextField();
		ticketField.setBounds(152, 10, 179, 20);
		frame.getContentPane().add(ticketField);
		ticketField.setColumns(10);

		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(152, 44, 179, 20);
		frame.getContentPane().add(emailField);

		homePageButton = new JButton("Home Page");
		homePageButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		homePageButton.setBounds(36, 89, 110, 23);
		frame.getContentPane().add(homePageButton);

		cancelSubmitButton = new JButton("Submit");
		cancelSubmitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		cancelSubmitButton.setBounds(221, 89, 110, 23);
		frame.getContentPane().add(cancelSubmitButton);

	}


	/**
	 * @param actionlistener
	 * sets action listener for homepage button
	 */
	public void setHomePageListener (ActionListener a ) {
		homePageButton.addActionListener(a);
	}

	/**
	 * @param actionListener
	 * sets action listener for cancel button
	 */
	public void setSubmitListener (ActionListener a ) {
		cancelSubmitButton.addActionListener(a);
	}

	/**
	 * @return ticket number
	 */
	public String getTicketCancel() {
		return ticketField.getText();
	}

	/**
	 * @return email 
	 */
	public String getEmailCancel() {
		return emailField.getText();
	}

	/**
	 * hides the page
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * makes to page visible
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	// pop-up dialogues
		/**
		 * @param message
		 * shows the message as pop-up
		 */
		public void popUpDialog(String message) {
			JOptionPane.showMessageDialog(null, message);
		}
}
