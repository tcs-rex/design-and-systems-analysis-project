package model;

/**
 * Represents a seat in our movie registration system.
 * 
 */
public class Seat {

    /**
     * The id of the seat in the database.
     */
    private int seat_id;
    /**
     * The row of the seat.
     */
    private int seat_row;
    /**
     * The column of the seat.
     */
    private int seat_column;
    /**
     * The showtime id of the seat.
     */
    private int showtime_id;
    /**
     * A boolean representing whether the seat is available.
     */
    private boolean is_taken;

    //Constructor

    /**
     * 
     * @param seat_id
     * @param seat_row
     * @param seat_column
     * @param showtime_id
     * @param is_taken
     */
    public Seat(int seat_id, int seat_row, int seat_column, int showtime_id, boolean is_taken) {
        this.seat_id = seat_id;
        this.seat_row = seat_row;
        this.seat_column = seat_column;
        this.showtime_id = showtime_id;
        this.is_taken = is_taken;
    }

    // methods
    
    /*
     * A string representation of the seat.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Seat id: " + getSeat_id() + "\n");
        sb.append("Seat row number: " + getSeat_row() + "\n");
        sb.append("Seat column number: " + getSeat_column() + "\n");

        return sb.toString();
    }

    //Getters and Setters

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(int seat_row) {
        this.seat_row = seat_row;
    }

    public int getSeat_column() {
        return seat_column;
    }

    public void setSeat_column(int seat_column) {
        this.seat_column = seat_column;
    }

    public int getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(int showtime_id) {
        this.showtime_id = showtime_id;
    }
    
    public boolean isIs_taken() {
        return is_taken;
    }

    public void setIs_taken(boolean is_taken) {
        this.is_taken = is_taken;
    }
}
