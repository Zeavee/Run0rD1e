package ch.epfl.sdp.database.room.social;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Represents database table with schema Chat(String to, String from,  int chat_id, UNIQUE(to, from), FOREIGN KEY to REFERENCES User, FOREIGN KEY from REFERENCES User, PRIMARY KEY (chat_id)).
 * "to" is the user at which the chat the directed to, and "from" is the user from which the chat is directed (i.e. a message sent from A to B will reference different
 * chat than a message sent from B to A)
 */

@Entity(indices = {@Index(value = {"from", "to"},
        unique = true), @Index(value = "to")},
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "to"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "from"
                )
        })
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int chat_id;

    private String to;
    private String from;

    public Chat(String from, String to) {
        this.from = from;
        this.to = to;
    }


    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int id) {
        chat_id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}