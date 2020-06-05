package ch.epfl.sdp.database.room.social;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Date;

/**
 * Abstracts a database relation with schema Message(String text, Date date, Int Chat_id, FOREIGN KEY Chat_id REFERENCES Chat)
 */
@Entity(primaryKeys = {"text", "chat_id"}, foreignKeys = {
        @ForeignKey(
                entity = Chat.class,
                parentColumns = "chat_id",
                childColumns = "chat_id"
        )}, indices = @Index(value = "chat_id"))
public class Message {

    private Date date;
    @NonNull
    private String text;

    private int chat_id;

    /**
     * Creates a message
     *
     * @param date    the date of the message
     * @param text    the text of the message
     * @param chat_id the chat id the message is related to
     */
    public Message(Date date, @NonNull String text, int chat_id) {
        this.date = date;
        this.text = text;
        this.chat_id = chat_id;
    }

    /**
     * Sets the date of the message
     *
     * @param date the date we want to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the chat id of the message
     *
     * @param chat_id the chat id we want to set
     */
    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    /**
     * Sets the text of the message
     *
     * @param text the text we want to set
     */
    public void setText(@NonNull String text) {
        this.text = text;
    }

    /**
     * Gets the date of the message
     *
     * @return the date of the message
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the text of the message
     *
     * @return the text of the message
     */
    @NonNull
    public String getText() {
        return text;
    }

    /**
     * Gets the chat id of the message
     *
     * @return the chat id of the message
     */
    public int getChat_id() {
        return chat_id;
    }
}