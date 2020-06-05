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

    /**
     * Creates a relation between two friends
     *
     * @param friendID1 the first friend
     * @param friendID2 the second friend
     */
    public IsFriendsWith(String friendID1, String friendID2) {
        this.friendID1 = friendID1;
        this.friendID2 = friendID2;
    }

    /**
     * Gets the first friend
     *
     * @return the first friend
     */
    public String getFriendID1() {
        return friendID1;
    }

    /**
     * Gets the second friend
     *
     * @return the second friend
     */
    public String getFriendID2() {
        return friendID2;
    }

    /**
     * Sets the first friend
     *
     * @param friendID1 the friend we want to set
     */
    public void setFriendID1(String friendID1) {
        this.friendID1 = friendID1;
    }

    /**
     * Sets the second friend
     *
     * @param friendID2 the friend we want to set
     */
    public void setFriendID2(String friendID2) {
        this.friendID2 = friendID2;
    }
}