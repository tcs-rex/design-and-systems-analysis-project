package model;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;

/**
 * Represents a movie in our movie theatre ticket reservation system.
 * Has methods to obtain a showtime for the movie.
 * 
 */
public class Movie {

    /**
     * The id of the movie in the database.
     */
    private int movie_id;
    /**
     * The title of the movie.
     */
    private String title;
    /**
     * The genre of the movie.
     */
    private String genre;
    /**
     * The release date of the movie.
     */
    private Date release_date;
    /**
     * The id of the theatre this movie belongs to.
     */
    private int theatre_id;
    /**
     * The list of showtimes available for this movie.
     */
    private ArrayList<Showtime> showtimes;

    //Constructor

    /**
     * 
     * @param movie_id
     * @param title
     * @param genre
     * @param release_date
     * @param theatre_id
     * @param showtimes
     */
    public Movie(int movie_id, String title, String genre, Date release_date, int theatre_id, ArrayList<Showtime> showtimes) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.release_date = release_date;
        this.theatre_id = theatre_id;
        this.showtimes = showtimes;
    }

    /**
     * 
     */
    public Movie() {

    }

    // methods

    /**
     * Search for a showtime for this movie by id in the database.
     * 
     * @param showtime_id
     * @return
     */
    public Showtime searchShowtimeById(int showtime_id) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtime_id() == showtime_id) {
                return showtime;
            }
        }
        return null;
    }

    /**
     * Search for a showtime by date and time.
     * 
     * @param date
     * @param time
     * @return
     */
    public Showtime searchShowtimeByDateAndTime(Date date, Time time) {
        for (Showtime showtime : showtimes) {
            if (showtime.getDate().equals(date) && showtime.getTime().equals(time)) {
                return showtime;
            }
        }
        return null;
    }

    /**
     * Add a showtime to the movies list of available showtimes.
     * 
     * @param showtime
     */
    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    /**
     * Remove a showtime from the movies list of available showtimes.
     * 
     * @param showtime
     */
    public void removeShowtime(Showtime showtime) {
        showtimes.remove(showtime);
    }

    /**
     * Returns a string representation of the movie.
     * 
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Movie id: " + getMovie_id() + "\n");
        sb.append("Movie name: " + getTitle() + "\n");
        return sb.toString();
    }

    //Getters and setters

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public int getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(int theatre_id) {
        this.theatre_id = theatre_id;
    }

    public ArrayList<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(ArrayList<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
