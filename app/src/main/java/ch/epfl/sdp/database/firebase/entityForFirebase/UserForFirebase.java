package ch.epfl.sdp.database.firebase.entityForFirebase;

/**
 * The long-term User entity to be stored in cloud firebase
 */
public class UserForFirebase {
    private String email;
    private String username;
    private int generalScore;

    /**
     * For Firebase each custom class must have a public constructor that takes no arguments.
     */
    public UserForFirebase() {
    }

    /**
     * Construct a UserForFirebase instance
     *
     * @param email        The email of the userForFirebase
     * @param username     The username of the userForFirebase
     * @param generalScore The generalScore of the userForFirebase
     */
    public UserForFirebase(String email, String username, int generalScore) {
        this.email = email;
        this.username = username;
        this.generalScore = generalScore;
    }

    /**
     * Get the email of the userForFirebase
     *
     * @return A string represents the email of the userForFirebase
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the username of the userForFirebase
     *
     * @return A string represents the username of the userForFirebase
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the generalScore of the userForFirebase
     *
     * @return The generalScore of the userForFirebase
     */
    public int getGeneralScore() {
        return generalScore;
    }

    /**
     * Set the email of the userForFirebase
     *
     * @param email The email of the userForFirebase
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the username of the userForFirebase
     *
     * @param username The username of the userForFirebase
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the generalScore of the UserForFirebase
     *
     * @param generalScore The generalScore of the userForFirebase
     */
    public void setGeneralScore(int generalScore) {
        this.generalScore = generalScore;
    }
}
