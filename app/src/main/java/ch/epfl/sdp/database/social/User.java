package ch.epfl.sdp.database.social;

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
    @Ignore
    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}