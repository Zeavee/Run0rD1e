package ch.epfl.sdp.database.firebase;

public class UserForFirebase {
    private String email;
    private String username;
    private double score;

    //Each custom class must have a public constructor that takes no arguments.
    public UserForFirebase() {
    }

    public UserForFirebase(String email, String username, double score) {
        this.email = email;
        this.username = username;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
