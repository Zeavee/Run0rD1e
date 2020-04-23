package ch.epfl.sdp.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leaderboard_table")
public class PlayerEntity {
    @PrimaryKey
    @NonNull
    private String email;
    private String username;
    private double healthpoint;

    public PlayerEntity(String email, String username, double healthpoint) {
        this.email = email;
        this.username = username;
        this.healthpoint = healthpoint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getHealthpoint() {
        return healthpoint;
    }

    public void setHealthpoint(double healthpoint) {
        this.healthpoint = healthpoint;
    }
}
