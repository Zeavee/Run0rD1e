package ch.epfl.sdp.database.firebase.entity;

/**
 * The long-term User entity to be stored in cloud firebase
 */
public class UserForFirebase {
    private String email;
    private String username;
    private double score;

    /**
     * For Firebase each custom class must have a public constructor that takes no arguments.
     */
    public UserForFirebase() {
    }

    /**
     * Construct a UserForFirebase instance
     *
     * @param email The email of the userForFirebase
     * @param username The username of the userForFirebase
     * @param score The socre of the userForFirebase
     */
    public UserForFirebase(String email, String username, double score) {
        this.email = email;
        this.username = username;
        this.score = score;
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
     * Get the score of the userForFirebase
     *
     * @return The score of the userForFirebase
     */
    public double getScore() {
        return score;
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
     * Set the score of the UserForfirebase
     *
     * @param score The score of the userForFirebase
     */
    public void setScore(double score) {
        this.score = score;
    }
}
