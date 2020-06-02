package ch.epfl.sdp.database.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * This class will describe the Entity (which represents the SQLite table) for the leaderBoard.
 * Each field in the class represents a column in the table.
 * Room will ultimately use these fields to both create the table and instantiate objects from rows in the database.
 */
@Entity(tableName = "leaderBoard_table")
public class GeneralLeaderBoardEntity {
    @PrimaryKey
    @NonNull
    private String email;
    private String username;
    private int generalScore;

    /**
     * Construct a leaderBoardEntity instance which represents a row in the leaderBoard_table
     *
     * @param email        The email of the leaderBoardEntity
     * @param username     The username of the leaderBoardEntity
     * @param generalScore The generalScore of the leaderBoardEntity
     */
    public GeneralLeaderBoardEntity(@NotNull String email, String username, int generalScore) {
        this.email = email;
        this.username = username;
        this.generalScore = generalScore;
    }

    /**
     * Get the email of the leaderBoardEntity
     *
     * @return The email of the leaderBoardEntity
     */
    @NotNull
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the leaderBoardEntity
     *
     * @param email The email of the leaderBoardEntity
     */
    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    /**
     * Get the username of the leaderBoardEntity
     *
     * @return The username of the leaderBoardEntity
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the leaderBoardEntity
     *
     * @param username The username of the leaderBoardEntity
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the generalScore of the leaderBoardEntity
     *
     * @return The generalScore of the leaderBoardEntity
     */
    public int getGeneralScore() {
        return generalScore;
    }

    /**
     * Set the generalScore of the leaderBoardEntity
     *
     * @param generalScore The generalScore of the leaderBoardEntity
     */
    public void setGeneralScore(int generalScore) {
        this.generalScore = generalScore;
    }
}
