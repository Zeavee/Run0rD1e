package ch.epfl.sdp.social.socialDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/**
 * @brief abstracts all the database table operations that can be done on the tables
 */
@Dao
public interface ChatDAO {
    //final static Date today = new Date();

    /**
     * Gets all the messages from "sender" to "owner"
     *
     * @param owner  the owner is the one receiving the messages
     * @param sender the sender is the one sending the messages
     * @return the list of messages from "sender" to "owner"
     */
    @Transaction
    @Query("SELECT * FROM message WHERE message.chat_id IN " +
            "(SELECT chat.chat_id FROM chat WHERE chat.`from` = :sender AND chat.`to` = :owner)")
    public List<Message> getMessages(String owner, String sender);

    /**
     * inserts a message m into the database table of messages
     *
     * @param m a message object (with text content and date)
     */
    @Insert
    public void sendMessage(Message m);

    /**
     * inserts a user "usr" into the table of Users
     *
     * @param usr the user to insert into the database
     */
    @Insert
    public void addUser(User usr);

    /**
     * insert a chat record into the Chat table
     *
     * @param c the chat to insert
     */
    @Insert
    public void addChat(Chat c);

    /**
     * @param friend the user that we are getting the friends of
     * @brief Get all friends of user "friend"
     */
    @Query("SELECT * FROM User WHERE user.userID IN (SELECT friendID2 FROM IsFriendsWith WHERE " +
            "IsFriendsWith.friendID2<>:friend AND " +
            "IsFriendsWith.friendID1=:friend)" +
            " UNION " +
            "SELECT * FROM User WHERE user.userID IN (SELECT friendID1 FROM IsFriendsWith WHERE " +
            "IsFriendsWith.friendID2=:friend AND " +
            "IsFriendsWith.friendID1<>:friend)")
    public List<User> areFriends(String friend);

    /**
     * Inserts a friendship into the IsFriendsWith database relation
     *
     * @param friends the friendship record to insert
     */
    @Insert
    public void addFriendship(IsFriendsWith friends);

    /**
     * Gets the chat from the current user with id "current" to the another user with id "other"
     *
     * @param current the user id of the current user
     * @param other   the user id of the other user
     * @return the chat (should be singleton list) from the current user to the other user
     */
    @Query("SELECT * FROM chat WHERE chat.`from` =:current  AND chat.`to` =:other")
    public List<Chat> getChatFromCurrentToOther(String current, String other);


}