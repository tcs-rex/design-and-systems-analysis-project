package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a registered user in our ticket reservation system.
 * Registered users have pay an annual fee and get benefits
 * such as no admin fee for cancelling tickets and receiving movies news
 * before public announcement.
 * 
 */
public class RegisteredUser extends User{
    
    /**
     * The registered users password
     */
    private String user_password;
    /**
     * THe registered users registration date
     */
    private Date registration_date;
    /**
     * The registered users annual payment information
     */
    private Payment annualPayment;
    /**
     * The registered users financial information
     */
    private FinancialInformation financialInformation = new FinancialInformation();

    //Constructors

    /**
     * 
     * @param user_id
     * @param first_name
     * @param last_name
     * @param email
     * @param user_password
     * @param is_reg_user
     * @param registration_date
     * @param annualPayment
     * @param financialInformation
     * @param tickets
     */
    public RegisteredUser (
        int user_id, 
        String first_name, 
        String last_name, 
        String email, 
        String user_password, 
        boolean is_reg_user, 
        Date registration_date, 
        Payment annualPayment,
        FinancialInformation financialInformation,
        ArrayList<Ticket> tickets) {
            super(user_id, first_name, last_name, email, is_reg_user, tickets);
            this.user_password = user_password;
            this.registration_date = registration_date;
            this.annualPayment = annualPayment;
            this.financialInformation.setCard_number(financialInformation.getCard_number());
            this.financialInformation.setCvc(financialInformation.getCvc());
            this.financialInformation.setExpiry_date(financialInformation.getExpiry_date());
    }

    /**
     * 
     * @param user_id
     * @param first_name
     * @param last_name
     * @param email
     * @param user_password
     * @param is_reg_user
     * @param registration_date
     * @param financialInformation
     * @param annualPayment
     */
    public RegisteredUser (
        int user_id, 
        String first_name, 
        String last_name, 
        String email, 
        String user_password, 
        boolean is_reg_user, 
        Date registration_date,
        FinancialInformation financialInformation,
        Payment annualPayment) {
            super(user_id, first_name, last_name, email, is_reg_user);
            this.user_password = user_password;
            this.registration_date = registration_date;
            this.annualPayment = annualPayment;
            this.financialInformation.setCard_number(financialInformation.getCard_number());
            this.financialInformation.setCvc(financialInformation.getCvc());
            this.financialInformation.setExpiry_date(financialInformation.getExpiry_date());
    }
    
    /**
     * 
     * @param first_name
     * @param last_name
     * @param email
     * @param is_reg_user
     * @param user_password
     * @param registration_date
     */
    public RegisteredUser(String first_name, String last_name, String email, 
    		boolean is_reg_user, String user_password, long card_number, int cvc, Date expiry_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.is_reg_user = true;
        this.user_password = user_password;
        this.registration_date = new Date();
        financialInformation.setCard_number(card_number);
        financialInformation.setCvc(cvc);
        financialInformation.setExpiry_date(expiry_date);
    }

    // methods

    /**
     * Composes a Payment object that represents a registered users
     * annual payment if the user is a new user. Otherwise, it updates
     * the payment date for a user that previously had a annual payment
     * and is renewing.
     * 
     * @param date
     * @param amountPaid
     * @return
     */
    public Payment makeAnnualPayment(Date date, double amountPaid) {
        if (!annualPaymentRequired()) {
            return null;
        }

        if (annualPayment != null) {
            annualPayment.setAmount_paid(amountPaid);
            annualPayment.setDate(date);
        } else {
            annualPayment = new Payment(
                Payment.PAYMENT_TYPE_ANNUAL,
                date,
                amountPaid,
                Payment.PAYMENT_STATUS_PAID,
                0,
                null
            );
        }
        sendReceiptInMail();
        return annualPayment;
    }

    /**
     * Checks if a registered users annual payment is still valid.
     * Eg if when this method is called and today's date is one year
     * after the payment date, then return true.
     * 
     * @return
     */
    public boolean annualPaymentRequired() {
        if (annualPayment == null) {
            return true;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(annualPayment.getDate());
        c.add(Calendar.YEAR, 1);
        Date today = new Date();
        if (today.after(c.getTime())) {
            return true;
        }

        return false;
    }

    /**
     * After a ticket payment is made or the ticket payment is refunded,
     * this method is called to send an email to the user associated with
     * this ticket.
     * 
     */
    public void sendReceiptInMail() {
        SendMail mailer = new SendMail(getEmail());
        String subject = "Annual" + getPayment().getPayment_status();

        StringBuffer messageBuilder = new StringBuffer();
        messageBuilder.append("Greetings " + getFirst_name() + " " + getLast_name() + ",\n\n");
        messageBuilder.append(annualPayment.toString());
        messageBuilder.append("\n\nThanks,\n");
        messageBuilder.append(News.COMPANY_NAME);

        mailer.sendMail(messageBuilder.toString(), subject);
    }

    /**
     * String representation of a payment.
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + getUser_id() +
                ", first_name='" + getFirst_name() + '\'' +
                ", last_name='" + getLast_name() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", user_password='" + getUser_password() + '\'' +
                ", is_reg_user=" + Is_reg_user() +
                ", registration_date=" + getRegistration_date() +
                '}';
    }

    // getters and setters
    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Payment getPayment() {
        return annualPayment;
    }

    public void setPayment(Payment annualPayment) {
        this.annualPayment = annualPayment;
    }

	public FinancialInformation getFinancialInformation() {
		return this.financialInformation;
	}
	
	public void setFinancialInformation(long cardNumber, int cvc, Date expiryDate) {
		this.financialInformation = new FinancialInformation(cardNumber, cvc, expiryDate);
	}
	
}
