package model;
import java.util.ArrayList;

/**
 * Represents a regular user or a guest in our movie ticket reservation system.
 * A guest does not have financial information nor needs to pay an annual fee payment.
 * 
 */
public class RegularUser extends User {
    
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
        public RegularUser (
            int user_id, 
            String first_name, 
            String last_name, 
            String email,
            boolean is_reg_user, 
            ArrayList<Ticket> tickets) {
                super(user_id, first_name, last_name, email, is_reg_user, tickets);
        }
    
        /**
         * 
         * @param user_id
         * @param first_name
         * @param last_name
         * @param email
         * @param is_reg_user
         */
        public RegularUser (
            int user_id, 
            String first_name, 
            String last_name, 
            String email, 
            boolean is_reg_user) {
                super(user_id, first_name, last_name, email, is_reg_user);
                this.tickets = new ArrayList<Ticket>();
        }

        /**
         * 
         */
        public RegularUser () {
            setIs_reg_user(false);
            this.tickets = new ArrayList<Ticket>();
        }
}
