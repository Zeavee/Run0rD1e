package ch.epfl.sdp;

public class User {
    private String username;
    private String email;
    private double healthPoints;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.healthPoints = 100.0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }
}
