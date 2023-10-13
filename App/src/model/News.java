package model;

import java.util.ArrayList;

/**
 * Represents movie news and a subject in the Obesrver design pattern.
 * 
 */
public class News implements Subject {
    
    /**
     * Represents movie news.
     */
    private String news;
    /**
     * Represents the company name.
     */
    public final static String COMPANY_NAME = "SKML";
    /**
     * Represents the subject of the news.
     */
    public final static String NEWS_SUBJECT = "Exciting News From " + COMPANY_NAME + " Movies!";
    /**
     * An array list of observers.
     */
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    /**
     * 
     * @param news
     */
    public News(String news) {
        this.news = news;
    }
    
    /**
     * 
     */
    public News() {
        news = "Dummy News";
    }

    /**
     * Registers a new observer to the news system.
     * 
     */
    public void registerObserver(Observer o) {
        observers.add(o);
        o.update(news, NEWS_SUBJECT);
    }

    /**
     * Removes a observer from the news system.
     * 
     */
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies all observers in the news system.
     * 
     */
    public void notifyAllObservers() {
        for (Observer o: observers) {
            o.update(news, NEWS_SUBJECT);
        }
    }

    /**
     * Changes the news in the news system.
     * 
     * @param news
     */
    public void setNews(String news) {
        this.news = news;
        notifyAllObservers();
    }

    /**
     * Changes all the observers in the news system.
     * 
     * @param observers
     */
    public void setObservers(ArrayList<Observer> observers) {
        if (observers != null) {
            this.observers = observers;
        }
    }
}
