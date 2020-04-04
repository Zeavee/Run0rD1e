package ch.epfl.sdp.social;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(primaryKeys = {"text", "chat_id"}, foreignKeys = {
        @ForeignKey(
                entity = Chat.class,
                parentColumns = "chat_id",
                childColumns = "chat_id"
        )})
public class Message {

    private Date date;
    @NonNull
    private String text;

    private int chat_id;

    public Message(Date date, String text, int chat_id)
    {
        this.date = date;
        this.text = text;
        this.chat_id = chat_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public void setText(String text) {
        this.text = text;
    }
    public Date getDate()
    {
        return date;
    }
    public String getText()
    {
        return text;
    }
    public int getChat_id() {
        return chat_id;
    }
}