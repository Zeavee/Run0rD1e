package ch.epfl.sdp.social;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {
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

    @PrimaryKey
    public int chat_id;
    //public String owner;
    public String to;
    public String from;

    public Chat()
    {
        ++instanceCount;
        this.chat_id = instanceCount;
    }

    @Ignore
    public static int instanceCount = 0;

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

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}