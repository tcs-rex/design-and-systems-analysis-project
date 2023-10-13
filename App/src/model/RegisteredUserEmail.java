package model;

/**
 * Observer for the news system. When then news updates this
 * observer, an email is sent to the email in this observer
 * with the news. 
 */
public class RegisteredUserEmail implements Observer {
    
    /**
     * The email of a registered user.
     */
    private String email;

    /**
     * 
     * @param email
     */
    public RegisteredUserEmail(String email) {
        this.email = email;
    }

    /**
     * Sends an email to the email in this observer with the
     * news and the newsSubject.
     * 
     */
    public void update(String news, String newsSubject) {
        SendMail mailer = new SendMail(email);
        mailer.sendMail(news, newsSubject);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
