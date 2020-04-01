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

    public User(String email)
    {
        this.email = email;
    }

    private String firstName;

    @NonNull
    public String getEmail() {
        return email;
    }

}