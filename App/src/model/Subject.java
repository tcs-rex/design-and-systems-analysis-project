package model;

/**
 * A subject interface.
 * 
 */
public interface Subject {
    
    /**
     * Register a new observer to this subject.
     * 
     * @param o
     */
    public void registerObserver(Observer o);
    /**
     * Remove a observer from this subject.
     * 
     * @param o
     */
    public void removeObserver(Observer o);
    /**
     * Notify all observers of this subject.
     */
    public void notifyAllObservers();
}
