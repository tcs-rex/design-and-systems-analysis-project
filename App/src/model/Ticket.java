package model;
import java.util.Date;
import java.util.Calendar;

/**
 * Represents a ticket purchased by a regular or registered user in the
 * ticket reservation system.
 * 
 */
public class Ticket {

    /**
     * The id of the ticket in the database.
     */
    private int ticket_id;
    /**
     * The user that this ticket is for.
     */
    private User user;
    /**
     * The movie that this ticket is for.
     */
    private Movie movie;
    /**
     * The theatre that this ticket is for.
     */
    private Theatre theatre;
    /**
     * The showtime that this ticket is for.
     */
    private Showtime showtime;
    /**
     * The seat that this ticket is for. 
     */
    private Seat seat;
    /**
     * The payment for this ticket.
     */
    private Payment payment;

    // Contructors
    /**
     * 
     * @param ticket_id
     * @param user
     * @param movie
     * @param theatre
     * @param showtime
     * @param seat
     * @param payment
     */
    public Ticket(int ticket_id, User user, Movie movie, Theatre theatre, Showtime showtime, Seat seat, Payment payment) {
        this.ticket_id = ticket_id;
        this.user = user;
        this.movie = movie;
        this.theatre = theatre;
        this.showtime = showtime;
        this.seat = seat;
        this.payment = payment;
    }

    /**
     * 
     */
    public Ticket() {}

    // methods

    /**
     * Returns true if the ticket is fully built.
     * 
     * @return
     */
    public boolean isTicketFilled () {
        return (user != null && movie != null && theatre != null && 
                showtime != null && seat != null && payment != null
        );
    }

    /**
     * Returns a receipt representation of this ticket.
     * 
     * @return
     */
    public String getReceipt() {
        if (!isTicketFilled()) {
            return "Ticket not ready";
        }

        StringBuffer sb = new StringBuffer();

        sb.append("Ticket Information:\n");
        sb.append("Ticket id: " + ticket_id + "\n");
        sb.append(theatre.toString());
        sb.append(movie.toString());
        sb.append(showtime.toString());
        sb.append(seat.toString());

        sb.append("\n");
        sb.append("Payment Information:\n");
        sb.append(payment.toString());

        sb.append("Credit Remaining: ");
        sb.append(user.getAvailableCredit());
        
        return sb.toString();
    }

    /**
     * Return the available credit for this ticket.
     * 
     * @return
     */
    public double getAvailableCredit() {
        if (!isTicketFilled()) {
            return 0.0;
        } else {
            return payment.getCredit_amount();
        }
    }

    /**
     * Make a payment for this ticket.
     * 
     * @param date
     * @param amount_paid
     * @return
     */
    public Payment makePayment(Date date, double amount_paid) {
        if (isTicketFilled()) {
            throw new IllegalStateException("Ticket is not built yet");
        }
        if (getPayment() != null) {
            throw new IllegalStateException("Payment already exists");
        }
        if (seat.isIs_taken()) {
            throw new IllegalStateException("Seat is already taken");
        }
        
        payment = new Payment(
            Payment.PAYMENT_TYPE_TICKET,
            date, 
            amount_paid,
            Payment.PAYMENT_STATUS_PAID,
            0,
            null
        );
        makeSeatTaken();
        sendReceiptInMail();
        return payment;
    }

    /**
     * Cancel the payment for this ticket.
     * 
     * @return
     */
    public Payment cancelTicket() {
        if (!isTicketFilled()) {
            throw new IllegalStateException("Ticket is not built yet");
        }
        if (!payment.getPayment_status().equals(Payment.PAYMENT_STATUS_PAID)) {
            throw new IllegalStateException("Ticket is already refunded");
        }

        Date showtimeDate = showtime.getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(showtimeDate);
        c.add(Calendar.HOUR, -72);
        if (c.getTime().before(new Date())) {
            throw new IllegalStateException("Cannot refund ticket 72 hours before showtime");
        }

        if (user.Is_reg_user()) {
            payment.refundPayment(false);
        } else {
            payment.refundPayment(true);
        }
        makeSeatAvailable();
        sendReceiptInMail();
        return payment;
    }

    /**
     * Use a set amount of credit for this ticket if the
     * ticket was refunded prior.
     * 
     * @param amountToUse
     */
    public void useCredit(double amountToUse) {
        if (!isTicketFilled()) {
            return;
        }
        if (amountToUse > getAvailableCredit()) {
            throw new IllegalArgumentException("Only " + getAvailableCredit() + " credit available.");
        }
        payment.setCredit_amount(getAvailableCredit() - amountToUse);
    }

    /**
     * After a ticket payment is made, this method is called
     * to set the seat associated with this ticket to taken.
     * 
     */
    public void makeSeatTaken() {
        if (!isTicketFilled()) {
            throw new IllegalStateException("Ticket is not built yet");
        }
        if (getPayment() == null || !getPayment().getPayment_status().equals(Payment.PAYMENT_STATUS_PAID)) {
            throw new IllegalStateException("Payment must be made first");
        }

        seat.setIs_taken(true);
    }

    public void makeSeatAvailable() {
        if (!isTicketFilled()) {
            throw new IllegalStateException("Ticket is not built yet");
        }
        if (getPayment() == null || !getPayment().getPayment_status().equals(Payment.PAYMENT_STATUS_REFUNDED)) {
            throw new IllegalStateException("Payment must be refunded first");
        }

        seat.setIs_taken(false);
    }

    /**
     * After a ticket payment is made or the ticket payment is refunded,
     * this method is called to send an email to the user associated with
     * this ticket.
     * 
     */
    public void sendReceiptInMail() {
        SendMail mailer = new SendMail(user.getEmail());
        String receipt = getReceipt();
        String subject = "Ticket" + getPayment().getPayment_status();

        StringBuffer messageBuilder = new StringBuffer();
        messageBuilder.append("Greetings " + user.getFirst_name() + " " + user.getLast_name() + ",\n\n");
        messageBuilder.append(receipt);
        messageBuilder.append("\n\nThanks,\n");
        messageBuilder.append(News.COMPANY_NAME);

        mailer.sendMail(messageBuilder.toString(), subject);
    }

    //Getter and setters
    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
