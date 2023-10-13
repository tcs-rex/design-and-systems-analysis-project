package model;

import java.util.ArrayList;

/**
 * Represents a theatre in our movie ticket reservation system.
 * Has methods to search for movies available in the theatre.
 * 
 */
public class Theatre {

    /**
     * The id of the theatre in the database.
     */
    private int theatre_id;
    /**
     * The name of the theatre.
     */
    private String theatre_name;
    /**
     * A list of movies available at this theatre.
     */
    private ArrayList<Movie> movies;

    //Constructor

    /**
     * 
     * @param theatre_id
     * @param theatre_name
     * @param movies
     */
    public Theatre(int theatre_id, String theatre_name, ArrayList<Movie> movies) {
        this.theatre_id = theatre_id;
        this.theatre_name = theatre_name;
        this.movies = movies;
    }

    /**
     * 
     */
    public Theatre() {
    }

    // methods

    /**
     * Search for a movie available in this theatre by the id
     * of the movie in the database.
     * 
     * @param movie_id
     * @return
     */
    public Movie searchMovieById (int movie_id) {
        for (Movie movie : movies) {
            if (movie.getMovie_id() == movie_id) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Search for a movie available in this theatre by
     * the name of the movie.
     * 
     * @param title
     * @return
     */
    public Movie searchMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of this theatre.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Theatre id: " + getTheatre_id() + "\n");
        return sb.toString();
    }

    //Getters and setters

    public int getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(int theatre_id) {
        this.theatre_id = theatre_id;
    }

    public String getTheatre_name() {
        return theatre_name;
    }

    public void setTheatre_name(String theatre_name) {
        this.theatre_name = theatre_name;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }
}
