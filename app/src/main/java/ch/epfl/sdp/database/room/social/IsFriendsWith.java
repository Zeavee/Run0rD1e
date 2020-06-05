package ch.epfl.sdp.database.room.social;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * Represents a friendship relation with schema IsFriendsWith(String friendID1, String friendID2, FOREIGN KEY friendID1 REFERENCES User, FOREIGN KEY friendID2 REFERENCES User)
 */
@Entity(primaryKeys = {"friendID1", "friendID2"}, foreignKeys = {@ForeignKey(
        entity = User.class,
        parentColumns = "userID",
        childColumns = "friendID1"
),
        @ForeignKey(
                entity = User.class,
                parentColumns = "userID",
                childColumns = "friendID2"
        )}, indices = {@Index(value = "friendID2")})
public class IsFriendsWith {
    @NonNull
    private String friendID1;
    @NonNull
    private String friendID2;

    public IsFriendsWith(String friendID1, String friendID2) {
        this.friendID1 = friendID1;
        this.friendID2 = friendID2;
    }


    public String getFriendID1() {
        return friendID1;
    }

    public String getFriendID2() {
        return friendID2;
    }

    public void setFriendID1(String friendID1) {
        this.friendID1 = friendID1;
    }

    public void setFriendID2(String friendID2) {
        this.friendID2 = friendID2;
    }
}