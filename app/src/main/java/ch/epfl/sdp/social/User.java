package ch.epfl.sdp.social;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User {
    @PrimaryKey
    @ColumnInfo(name = "userID")
    @NonNull
    public String email;

    public User(String email, String firstName, String lastName)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName;
    public String lastName;

    @NonNull
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Ignore
    private List<User> friends;
    public List<User> getFriends()
    {
        return friends;
    }

}