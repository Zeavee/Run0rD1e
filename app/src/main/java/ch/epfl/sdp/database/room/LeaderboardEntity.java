package ch.epfl.sdp.database.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leaderboard_table")
public class LeaderboardEntity {
    @PrimaryKey
    @NonNull
    private String email;
    private String username;
    private double score;

    public LeaderboardEntity(String email, String username, double score) {
        this.email = email;
        this.username = username;
        this.score = score;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
