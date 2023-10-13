package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * HomePage class is the GUI page for loging, cancellation , registration
 */
public class HomePage {

	/**
	 * the main frame
	 */
	private JFrame frame;
	
	/**
	 * input field for email
	 */
	private JTextField emailField;
	
	/**
	 * input field for password
	 */
	private JPasswordField passwordField;
	
	/**
	 * input field for first name for sign up
	 */
	private JTextField FNameField;
	
	/**
	 * input field for last name for sign up
	 */
	private JTextField LNameField;
	
	
	/**
	 * input field for email for sign up
	 */
	private JTextField emailFieldSignup;
	
	/**
	 * input field for password for sign up
	 */
	private JTextField passwordFiledSignup;
	
	/**
	 * input field for card number for sign up
	 */
	private JTextField cardNumberField;
	
	/**
	 * input field for expiry date for sign up
	 */
	private JTextField expirayDateField;
	
	/**
	 * input field for cvc number for sign up
	 */
	private JTextField cvcNumberField;
	
	/**
	 * button for sign in 
	 */
	private Button signinButton;
	
	/**
	 * button for registration
	 */
	private Button registerButton;
	
	/**
	 * button for canceling the ticket
	 */
	private Button cancelTiecktButton;
	
	/**
	 * button for guest button
	 */
	private Button guestButton;
	
	/**
	 * button for sign up
	 */
	private Button signupButton;
	
	// sign-up button variable (for collapse/expand)
	private int collapse = 0;
	


	/**
	 * constructor
	 */
	public HomePage() {

		frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 360, 310);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Label label = new Label("Movie Ticket Booking App");
		label.setFont(new Font("Dialog", Font.BOLD, 15));
		label.setAlignment(Label.CENTER);
		label.setBounds(71, 10, 206, 36);
		frame.getContentPane().add(label);

		Label label_1 = new Label("email");
		label_1.setBounds(21, 65, 44, 24);
		frame.getContentPane().add(label_1);

		Label label_1_1 = new Label("password");
		label_1_1.setBounds(21, 102, 72, 24);
		frame.getContentPane().add(label_1_1);

		signinButton = new Button("Sign In");
		signinButton.setBounds(138, 142, 70, 22);
		frame.getContentPane().add(signinButton);

		cancelTiecktButton = new Button("Cancel Ticket");
		cancelTiecktButton.setBounds(29, 188, 100, 24);
		frame.getContentPane().add(cancelTiecktButton);

		guestButton = new Button("Try as a guest");
		guestButton.setBounds(210, 188, 102, 24);
		frame.getContentPane().add(guestButton);

		signupButton = new Button("Sign Up");
		signupButton.setBounds(138, 231, 70, 22);
		frame.getContentPane().add(signupButton);

		Label label_1_2 = new Label("first name");
		label_1_2.setBounds(49, 276, 81, 24);
		frame.getContentPane().add(label_1_2);

		Label label_1_2_1 = new Label("last name");
		label_1_2_1.setBounds(49, 302, 81, 24);
		frame.getContentPane().add(label_1_2_1);

		Label label_1_2_1_1 = new Label("email");
		label_1_2_1_1.setBounds(49, 332, 81, 24);
		frame.getContentPane().add(label_1_2_1_1);

		Label label_1_2_1_1_1 = new Label("password");
		label_1_2_1_1_1.setBounds(49, 362, 81, 20);
		frame.getContentPane().add(label_1_2_1_1_1);

		Label label_1_2_1_1_1_1 = new Label("card number");
		label_1_2_1_1_1_1.setBounds(49, 388, 83, 20);
		frame.getContentPane().add(label_1_2_1_1_1_1);

		Label label_1_2_1_1_1_1_1 = new Label("expiry date");
		label_1_2_1_1_1_1_1.setBounds(49, 414, 83, 20);
		frame.getContentPane().add(label_1_2_1_1_1_1_1);

		Label label_1_2_1_1_1_1_1_1 = new Label("cvc number");
		label_1_2_1_1_1_1_1_1.setBounds(49, 439, 83, 20);
		frame.getContentPane().add(label_1_2_1_1_1_1_1_1);

		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(100, 65, 200, 20);
		frame.getContentPane().add(emailField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(100, 106, 200, 20);
		frame.getContentPane().add(passwordField);

		FNameField = new JTextField();
		FNameField.setColumns(10);
		FNameField.setBounds(154, 280, 158, 20);
		frame.getContentPane().add(FNameField);

		LNameField = new JTextField();
		LNameField.setColumns(10);
		LNameField.setBounds(154, 307, 158, 20);
		frame.getContentPane().add(LNameField);

		emailFieldSignup = new JTextField();
		emailFieldSignup.setColumns(10);
		emailFieldSignup.setBounds(153, 336, 158, 20);
		frame.getContentPane().add(emailFieldSignup);

		passwordFiledSignup = new JTextField();
		passwordFiledSignup.setColumns(10);
		passwordFiledSignup.setBounds(153, 362, 158, 20);
		frame.getContentPane().add(passwordFiledSignup);

		cardNumberField = new JTextField();
		cardNumberField.setColumns(10);
		cardNumberField.setBounds(153, 388, 158, 20);
		frame.getContentPane().add(cardNumberField);

		expirayDateField = new JTextField();
		expirayDateField.setToolTipText("MM//YY");
		expirayDateField.setColumns(10);
		expirayDateField.setBounds(154, 414, 158, 20);
		frame.getContentPane().add(expirayDateField);

		cvcNumberField = new JTextField();
		cvcNumberField.setToolTipText("123");
		cvcNumberField.setColumns(10);
		cvcNumberField.setBounds(154, 439, 158, 20);
		frame.getContentPane().add(cvcNumberField);

		registerButton = new Button("Register");
		registerButton.setBounds(138, 479, 70, 22);
		frame.getContentPane().add(registerButton);
		
		setSignUpButtonActionListener();
				
	}
	
	/**
	 * extends the page to show the registration fields
	 */
	private void setSignUpButtonActionListener(){
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(collapse == 0) {
					frame.setBounds(100, 100, 360, 560);
					collapse += 1;
					}
				else {
					frame.setBounds(100, 100, 360, 310);
					collapse += (-1);
				}
			}
		});
	}
		
	
	/**
	 * @param actionListener
	 * sets action listener for sign in button
	 */
	public void setSignInListener(ActionListener a) {
		signinButton.addActionListener(a);
	}

	/**
	 * @param actionListener
	 * sets action listener for register button
	 */
	public void setRegisterListener(ActionListener a) {
		registerButton.addActionListener(a);
	}

	/**
	 * @param actionListener
	 * sets action listener for cancel button
	 */
	public void setCancelListener(ActionListener a) {
		cancelTiecktButton.addActionListener(a);
	}
	/**
	 * @param actionListener
	 * sets action listener for sign in button
	 */
	public void setGuestListener(ActionListener a) {
		guestButton.addActionListener(a);
	}

	

	/**
	 * @return sing in password
	 */
	public String getEmailSignIn() {
		return emailField.getText();
	}

	/**
	 * @return sing in password
	 */
	public String getPasswordSignIn() {
		return String.valueOf(passwordField.getPassword());
	}

	/**
	 * @return  first name
	 */
	public String getFName() {
		return FNameField.getText();
	}

	/**
	 * @return  last name
	 */
	public String getLName() {
		return LNameField.getText();
	}

	/**
	 * @return email in sign up
	 */
	public String getEmailSignUp() {
		return emailFieldSignup.getText();
	}

	/**
	 * @return password in sign up
	 */
	public String getPasswordSignUp() {
		return passwordFiledSignup.getText();
	}

	/**
	 * @return card number 
	 */
	public String getcardNumber() {
		return cardNumberField.getText();
	}

	/**
	 * @return expiry date
	 */
	public String getexpirayDate() {
		return expirayDateField.getText();
	}

	/**
	 * @return cvc number
	 */
	public String getCVCNumber() {
		return cvcNumberField.getText();
	}

	/**
	 * hides the page
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * sets the page visible
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * @param message
	 * shows the message in a pop-up
	 */
	public void popUpDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
