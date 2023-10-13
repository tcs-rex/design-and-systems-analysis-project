package model;

import java.util.ArrayList;

/**
 * Represents a user in our movie ticket reservation system.
 */
public class User {

    /**
     * The user's id in the database.
     */
	protected int user_id;
    /**
     * The user's first name in the database.
     */
    protected String first_name;
    /**
     * The user's last name in the database.
     */
    protected String last_name;
    /**
     * The user's email in the database.
     */
    protected String email;
    /**
     * Represents if the user is a registered user or not.
     */
    protected boolean is_reg_user;
    /**
     * The list of tickets the user has previously purchased.
     */
    protected ArrayList<Ticket> tickets;

    //Constructors

    /**
     * 
     * @param user_id
     * @param first_name
     * @param last_name
     * @param email
     * @param is_reg_user
     * @param tickets
     */
    public User (
        int user_id, 
        String first_name, 
        String last_name, 
        String email, 
        boolean is_reg_user, 
        ArrayList<Ticket> tickets) {
            this.user_id = user_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.is_reg_user = is_reg_user;
            this.tickets = tickets;
    }

    /**
     * 
     * @param user_id
     * @param first_name
     * @param last_name
     * @param email
     * @param is_reg_user
     */
    public User (
        int user_id, 
        String first_name, 
        String last_name, 
        String email, 
        boolean is_reg_user) {
            this.user_id = user_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.is_reg_user = is_reg_user;
            tickets = new ArrayList<>();
    }

    /**
     * 
     */
    public User() {}

    // methods

    /**
     * Returns the available amount of credit in all the users tickets.
     * 
     * @return
     */
    public double getAvailableCredit() {
        double credit = 0;
        for (Ticket ticket: tickets) {
            credit += ticket.getAvailableCredit();
        }

        return credit;
    }

    /**
     * Subtracts an amount to use from the user's available credit in 
     * all the user's tickets.
     * 
     * @param amountToUse
     */
    public void useCredit(double amountToUse) {
        if (amountToUse > getAvailableCredit()) {
            throw new IllegalArgumentException("Only " + getAvailableCredit() + " credit available.");
        }
        for (Ticket ticket: tickets) {
            double minimumAmount = Math.min(amountToUse, ticket.getAvailableCredit());
            ticket.useCredit(minimumAmount);
            amountToUse -= minimumAmount;
        }
    }

    /**
     * Add a ticket to the user's list of tickets.
     * 
     * @param ticket
     */
    public void addTicket(Ticket ticket) {
        if (tickets == null) {
            tickets = new ArrayList<Ticket>();
        }
        tickets.add(ticket);
    }

    /**
     * Searches for a ticket in the user's list of tickets
     * based on the ticket id.
     * 
     * @param ticketId
     * @return
     */
    public Ticket searchTicket(int ticketId) {
        for (Ticket ticket: tickets) {
            if (ticket.getTicket_id() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the User.
     * 
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", is_reg_user=" + is_reg_user +
                '}';
    }

      //Getters and setters

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean Is_reg_user() {
        return is_reg_user;
    }

    public void setIs_reg_user(boolean is_reg_user) {
        this.is_reg_user = is_reg_user;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
}
