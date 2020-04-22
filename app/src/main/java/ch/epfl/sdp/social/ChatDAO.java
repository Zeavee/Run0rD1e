package ch.epfl.sdp.social;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ChatDAO {
    //final static Date today = new Date();

    @Transaction
    @Query("SELECT * FROM message WHERE message.chat_id IN " +
            "(SELECT chat.chat_id FROM chat WHERE chat.`from` = :sender AND chat.`to` = :owner)")
    public List<Message> getMessages(String owner, String sender);

    @Insert
    public void sendMessage(Message m);

    @Insert
    public void addUser(User usr);

    @Insert
    public void addChat(Chat c);

    // Get all friends of owner, friendID1 must be lexicographically before friendID2
    @Query("SELECT * FROM User WHERE user.userID IN (SELECT friendID2 FROM IsFriendsWith WHERE " +
            "IsFriendsWith.friendID2<>:friend AND " +
            "IsFriendsWith.friendID1=:friend)")
    public List<User> areFriends(String friend);

    @Insert
    public void addFriendship(IsFriendsWith friends);

    @Query("SELECT * FROM chat WHERE chat.`from` =:current  AND chat.`to` =:other")
    public List<Chat> getChatFromCurrentToOther(String current, String other);


}