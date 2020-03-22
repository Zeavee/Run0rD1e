package ch.epfl.sdp.database;

import java.util.Objects;

public class UserForFirebase {
    private String username;
    private String email;
    private double healthPoints;

    public UserForFirebase() {
        // need empty constructor for firebase
    }

    public UserForFirebase(String username, String email) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForFirebase that = (UserForFirebase) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
