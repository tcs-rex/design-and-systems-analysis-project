package model;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a showtime of a movie in our movie ticket reservation system.
 * 
 */
public class Showtime {

    /**
     * The id of the showtime in the database.
     */
    private int showtime_id;
    /**
     * The date of the showtime.
     */
    private Date date;
    /**
     * The time of the showtime.
     */
    private Time time;
    /**
     * The id of the movie for this showtime.
     */
    private int movie_id;
    /**
     * List of seats for this showtime.
     */
    private ArrayList<ArrayList<Seat>> seats;

    //Constructors

    /**
     * 
     * @param showtime_id
     * @param date
     * @param time
     * @param movie_id
     * @param seats
     */
    public Showtime(int showtime_id, Date date, Time time, int movie_id, ArrayList<ArrayList<Seat>> seats) {
        this.showtime_id = showtime_id;
        this.date = date;
        this.time = time;
        this.movie_id = movie_id;
        this.seats = seats;
    }

    /**
     * 
     */
    public Showtime() {
    }

    // methods
    
    /**
     * Search for a seat in this showtime by id.
     * 
     * @param seat_id
     * @return
     */
    public Seat searchSeatById(int seat_id) {
        for (ArrayList<Seat> row : seats) {
            for (Seat seat: row) {
                if (seat.getSeat_id() == seat_id) {
                    return seat;
                }
            }
        }
        return null;
    }

    /**
     * Search for a seat by row and column in this showtime.
     * 
     * @param rowNumber
     * @param colNumber
     * @return
     */
    public Seat getSeatByRowAndCol(int rowNumber, int colNumber) {
        if (rowNumber > seats.size()) {
            throw new IndexOutOfBoundsException("Row does not exist.");
        }
        ArrayList<Seat> row = seats.get(rowNumber);
        if (colNumber > row.size()) {
            throw new IndexOutOfBoundsException("Column does not exist.");
        }
        Seat seat = row.get(colNumber);
        return seat;
    }

    /**
     * Add a seat to this showtime.
     * 
     * @param seat
     * @param rowNumber
     * @param colNumber
     */
    public void addSeat(Seat seat, int rowNumber, int colNumber) {
        if (rowNumber > seats.size()) {
            throw new IndexOutOfBoundsException("Row does not exist.");
        }
        seats.get(rowNumber).add(seat);
    }

    /**
     * Remove a seat from this showtime.
     * 
     * @param rowNumber
     * @param colNumber
     */
    public void removeSeat(int rowNumber, int colNumber) {
        if (rowNumber > seats.size()) {
            throw new IndexOutOfBoundsException("Row does not exist.");
        }
        if (colNumber > seats.get(rowNumber).size()) {
            throw new IndexOutOfBoundsException("Column does not exist.");
        }
        seats.get(rowNumber ).remove(colNumber);
    }

    /**
     * A string representation of this showtime.
     * 
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Showtime id: " + getShowtime_id() + "\n");
        sb.append("Showtime date and time: " + getDate() + " " + getTime() + "\n");
        return sb.toString();
    }

    //Getters and setters
    public int getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(int showtime_id) {
        this.showtime_id = showtime_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public ArrayList<ArrayList<Seat>> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<ArrayList<Seat>> seats) {
        this.seats = seats;
    }

}
