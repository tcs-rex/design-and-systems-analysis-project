package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * PaymentView class is the GUI page for the payment
 * it has input fields to receive user financial info
 * and to show the user the ticket and receipt
 */
public class PaymentPage {

	/**
	 * the main frame
	 */
	private JFrame frame;
	
	/**
	 * email input field
	 */
	private JTextField emailField;
	
	/**
	 * card number input field
	 */
	private JTextField cardField;
	
	/**
	 * cvc number input field
	 */
	private JTextField cvcField;
	
	/**
	 * the button to claim the voucher
	 */
	private JButton voucherButtom;
	
	/**
	 * the button to pay without claiming the voucher
	 */
	private JButton payButtom;
	

	/**
	 * a pane to show the ticket
	 */
	private JTextPane ticketPane;
	
	/**
	 * a pane to show the receipt
	 */
	private JTextPane receiptPane;
	
	
	/**
	 * input field to get the expiry date
	 */
	private JTextField expiryField;

	
	/**
	 *  movie page constructor
	 */
	public PaymentPage() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(250, 235, 215));
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setVisible(true);
		frame.setBounds(100, 100, 355, 590);  //255
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Payment");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(86, 11, 163, 19);
		frame.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("Email");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(24, 51, 90, 19);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("Card Number");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(24, 76, 90, 19);
		frame.getContentPane().add(label_1);

		JLabel label_1_1 = new JLabel("CVC Number");
		label_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1_1.setBounds(24, 101, 90, 19);
		frame.getContentPane().add(label_1_1);

		JLabel label_1_2 = new JLabel("Expiry Date");
		label_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1_2.setBounds(24, 131, 90, 19);
		frame.getContentPane().add(label_1_2);

		emailField = new JTextField();
		emailField.setBounds(138, 50, 173, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);

		cardField = new JTextField();
		cardField.setColumns(10);
		cardField.setBounds(138, 75, 173, 20);
		frame.getContentPane().add(cardField);

		cvcField = new JTextField();
		cvcField.setColumns(10);
		cvcField.setBounds(138, 100, 173, 20);
		frame.getContentPane().add(cvcField);

		expiryField = new JTextField();
		expiryField.setToolTipText("YYYY-MM-DD");
		expiryField.setColumns(10);
		expiryField.setBounds(138, 130, 173, 20);
		frame.getContentPane().add(expiryField);

		voucherButtom = new JButton("claim voucher");
		voucherButtom.setBounds(45, 169, 116, 23);
		frame.getContentPane().add(voucherButtom);

		payButtom = new JButton("pay");
		payButtom.setBounds(195, 169, 116, 23);
		frame.getContentPane().add(payButtom);

		JLabel lblNewLabel_1_1 = new JLabel("Ticket & Receipt");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(118, 226, 100, 14);
		frame.getContentPane().add(lblNewLabel_1_1);

		receiptPane = new JTextPane();
		receiptPane.setBounds(10, 251, 319, 300);
		receiptPane.setEditable(false);
		frame.getContentPane().add(receiptPane);

	}


	/**
	 * @param actionListener
	 * sets the action listener for voucher button
	 */
	public void setVoucherListener(ActionListener a) {
		voucherButtom.addActionListener(a);
	}


	/**
	 * @param actionListner
	 * sets the action listener for pay button
	 */
	public void setPayListener(ActionListener a) {
		payButtom.addActionListener(a);
	}

	/**
	 * @return email
	 * reads the string from email input field and returns it
	 */
	public String getEmail() {
		return emailField.getText();
	}

	/**
	 * @return card number
	 * reads the string from card number input field and returns it
	 */
	public String getCard() {
		return cardField.getText();
	}

	/**
	 * @return cvc number
	 * reads the string from card number input field and returns it
	 */
	public String getCVC() {
		return cvcField.getText();
	}

	/**
	 * @return expiry date
	 * reads the string from expiry input field and returns it
	 */
	public String getExpiry() {
		return expiryField.getText();
	}

	/**
	 * @param ticket info as a string
	 * receives ticket info as string and sets the ticket field to that
	 */
	public void setTicket( String s ) {
		ticketPane.setText(s);
	}


	/**
	 * @param receipt info as string
	 * receives receipt info as string and sets the ticket field to that
	 */
	public void setReceipt( String s ) {
		receiptPane.setText(s);
	}
	
	/**
	 * @param message
	 * receives a message as a string and shows it as pop up
	 */
	public void popUpDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * hides the page
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * makes the page visible
	 */
	public void show() {
		frame.setVisible(true);
	}
	
}
