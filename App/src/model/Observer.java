package model;

/**
 * Observer interface.
 */
public interface Observer {

    /**
     * Updates the news and news subject in the observer.
     * 
     * @param news
     * @param newsSubject
     */
    public void update(String news, String newsSubject);
}
