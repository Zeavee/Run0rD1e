package ch.epfl.sdp.social;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = {"from", "to"},
        unique = true)},
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
    public int chat_id;

    // TODO make more restrictive access modifiers later
    public String to;
    public String from;

    public Chat(String from, String to)
    {
        this.from = from;
        this.to = to;
    }


    public int getChat_id()
    {
        return chat_id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo()
    {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}