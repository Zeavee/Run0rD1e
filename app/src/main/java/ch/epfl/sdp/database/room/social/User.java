package ch.epfl.sdp.database.room.social;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Abstracts a database table with schema User(String userID, PRIMARY KEY (email)), where userID is the email field of the class
 */
@Entity
public class User {
    @PrimaryKey
    @ColumnInfo(name = "userID")
    @NonNull
    private String email;

    @Ignore
    private String username;

    public User(String email) {
        this.email = email;
    }

    // This constructor should not be used for database related operations (hence @Ignore)
    /**
     * Creates a user in the database
     *
     * @param email    the email of the user
     * @param username the username of the user
     */
    @Ignore
    public User(@NonNull String email, String username) {
        this.email = email;
        this.username = username;
    }

    /**
     * Gets the username of the user
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @NonNull
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     *
     * @param email the email we want to set
     */
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

}