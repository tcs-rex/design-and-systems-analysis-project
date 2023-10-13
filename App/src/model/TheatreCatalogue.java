package model;

import java.util.ArrayList;

/**
 * Represents a catalogue of theatres in our movie ticket reservation system.
 * 
 */
public class TheatreCatalogue {
    
    /**
     * A list of theatres.
     */
    private ArrayList<Theatre> theatres;

    // Constructors

    /**
     * 
     * @param theatres
     */
    public TheatreCatalogue (ArrayList<Theatre> theatres) {
        this.theatres = theatres;
    }

    // Methods
    
    /**
     * Searches the catalogue for a theatre by the id of the
     * theatre in the database.
     * 
     * @param theatre_id
     * @return
     */
    public Theatre searchTheatreById (int theatre_id) {
        for (Theatre theatre : theatres) {
            if (theatre.getTheatre_id() == theatre_id) {
                return theatre;
            }
        }
        return null;
    }

    /**
     * Searches the catalogue for a theatre by the name of the
     * theatre in the database.
     * 
     * @param theatreName
     * @return
     */
    public Theatre getTheatreByName(String theatreName) {
        for (Theatre theatre : theatres) {
            if (theatre.getTheatre_name().equals(theatreName)) {
                return theatre;
            }
        }
        return null;
    }

    // Getters and setters

    public ArrayList<Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(ArrayList<Theatre> theatres) {
        this.theatres = theatres;
    }

    public void addTheatre(Theatre th) {
        theatres.add(th);
    }

    public void removeTheatre(Theatre th) {
        theatres.remove(th);
    }

}
