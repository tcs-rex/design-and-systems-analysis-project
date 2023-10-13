package model;

import java.util.Date;

/**
 * Represents a registered users financial information.
 * 
 */
public class FinancialInformation {

    /**
     * The card number.
     */
    private long card_number;
    /**
     * The cvc number.
     */
    private int cvc;
    /**
     * The expiry date of the card.
     */
    private Date expiry_date;

    //Constructors

    /**
     * 
     * @param card_number
     * @param cvc
     * @param expiry_date
     */
    public FinancialInformation(long card_number, int cvc, Date expiry_date) {
        this.card_number = card_number;
        this.cvc = cvc;
        this.expiry_date = expiry_date;
    }

    /**
     * 
     */
    public FinancialInformation() {

    }

    //Getters and setters
    public long getCard_number() {
        return card_number;
    }

    public void setCard_number(long card_number) {
        this.card_number = card_number;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    /**
     * Returns a string representation of the financial information.
     */
    @Override
    public String toString() {
        return "FinancialInformation{" +
                "card_number=" + card_number +
                ", cvc=" + cvc +
                ", expiry_date=" + expiry_date +
                '}';
    }
}
