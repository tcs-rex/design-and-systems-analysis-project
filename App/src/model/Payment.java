package model;


import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


/**
 * Represents a payment object. A payment can be either for a ticket or an annual
 * payment for a registered user.
 * 
 */
public class Payment {

    /**
     * The payment's id in the database.
     */
    private int payment_id;
    /**
     * The type of payment.
     */
    private String payment_type;
    /**
     * The date the payment was made.
     */
    private Date payment_date;
    /**
     * The amound paid.
     */
    private double amount_paid;
    /**
     * The status of the payment (paid or refunded)
     */
    private String payment_status;
    /**
     * The amount of credit available in the case of a refund.
     */
    private double credit_amount;
    /**
     * The expiry date of the credit in the case of a refund.
     */
    private Date credit_expiry_date;
    /**
     * Type of an annual payment.
     */
    public static final String PAYMENT_TYPE_ANNUAL = "Annual";
    /**
     * Type of a ticket payment.
     */
    public static final String PAYMENT_TYPE_TICKET = "Ticket";
    /**
     * Status of a payment that was paid.
     */
    public static final String PAYMENT_STATUS_PAID = "Paid";
    /**
     * Status of a payment that was refunded.
     */
    public static final String PAYMENT_STATUS_REFUNDED = "Refunded";

    //Constructor

    /**
     * 
     * @param payment_id
     * @param payment_type
     * @param payment_date
     * @param amount_paid
     * @param payment_status
     * @param credit_amount
     * @param credit_expiry_date
     */
    public Payment(int payment_id, String payment_type, Date payment_date, double amount_paid, String payment_status, double credit_amount, Date credit_expiry_date) {
        this.payment_id = payment_id;
        this.payment_type = payment_type;
        this.payment_date = payment_date;
        this.amount_paid = amount_paid;
        this.payment_status = payment_status;
        this.credit_amount = credit_amount;
        this.credit_expiry_date = credit_expiry_date;
    }


    // Constructor for creating since we don't know what the id is before inserting into database
    /**
     * 
     * @param payment_type
     * @param payment_date
     * @param amount_paid
     * @param payment_status
     * @param credit_amount
     * @param credit_expiry_date
     */
    public Payment(String payment_type, Date payment_date, double amount_paid, String payment_status, double credit_amount, Date credit_expiry_date) {
        this.payment_type = payment_type;
        this.payment_date = payment_date;
        this.amount_paid = amount_paid;
        this.payment_status = payment_status;
        this.credit_amount = credit_amount;
        this.credit_expiry_date = credit_expiry_date;
    }

    // Constructor for annual payment type 
    /**
     * 
     * @param payment_id
     * @param payment_type
     * @param payment_date
     * @param amount_paid
     * @param payment_status
     */
    public Payment(int payment_id, String payment_type, Date payment_date, double amount_paid, String payment_status) {
        this.payment_id = payment_id;
        this.payment_type = payment_type;
        this.payment_date = payment_date;
        this.amount_paid = amount_paid;
        this.payment_status = payment_status;
        credit_amount = 0;
        credit_expiry_date = null;
    }

    /**
     * 
     */
    public Payment() {
    }
    
    // methods

    /**
     * String representation of a payment.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Payment id: " + getPayment_id() + "\n");
        sb.append("Payment type: " + getPayment_type() + "\n");
        sb.append("Payment status: " + getPayment_status() + "\n");

        if (getPayment_status().equals(Payment.PAYMENT_STATUS_REFUNDED)) {
            sb.append("Credit remaining: " + getCredit_amount() + "\n");
            sb.append("Credit expiry date: " + getCredit_expiry_date() +  "\n");
        } else {
            sb.append("Payment amount: " + getAmount_paid() + "\n");
            sb.append("Payment date: " + getDate() + "\n");
        }

        return sb.toString();
    }

    /**
     * Refunds a ticket payment by updating the credit and the expiry date
     * and changing the payment's status.
     * 
     * @param adminFee
     */
    public void refundPayment(boolean adminFee) {
        if (payment_type.equals(PAYMENT_TYPE_ANNUAL)) {
            return;
        }
        payment_status = Payment.PAYMENT_STATUS_REFUNDED;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        credit_expiry_date = c.getTime();
        if (adminFee) {
            credit_amount = amount_paid * 0.85;
        } else {
            credit_amount = amount_paid;
        }
    }

    // Getters and Setters


    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public Date getDate() {
        return payment_date;
    }

    public void setDate(Date payment_date) {
        this.payment_date = payment_date;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public double getCredit_amount() {
        if (credit_expiry_date == null || credit_expiry_date.before(Date.from(Instant.now()))) {
            return 0.0;
        }
        return credit_amount;
    }

    public void setCredit_amount(double credit_amount) {
        this.credit_amount = credit_amount;
    }

    public Date getCredit_expiry_date() {
        return credit_expiry_date;
    }

    public void setCredit_expiry_date(Date credit_expiry_date) {
        this.credit_expiry_date = credit_expiry_date;
    }
}
