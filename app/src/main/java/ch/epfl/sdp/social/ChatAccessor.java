package ch.epfl.sdp.social;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ChatAccessor {
    //final static Date today = new Date();

    @Transaction
    @Query("SELECT message.text FROM message WHERE message.chat_id IN " +
            "(SELECT chat.chat_id FROM chat WHERE chat.`from` = :sender AND chat.`to` = :owner)")
    public List<String> getChatToOwnerFromSender(String owner, String sender);

    @Transaction
    @Query("SELECT message.text FROM message WHERE message.chat_id IN " +
            "(SELECT chat.chat_id FROM chat WHERE chat.`from` = :owner AND chat.`to` = :receiver)")
    public List<String> getChatFromOwnerToReceiver(String owner, String receiver);

    @Insert
    public void sendMessage(Message m);

    @Insert
    public void addUser(User usr);

    @Insert
    public void addChat(Chat c);

    // Get all friends of owner, friendID1 must be lexicographically before friendID2
    @Query("SELECT friendID2 FROM IsFriendsWith WHERE IsFriendsWith.friendID1=:friend1 AND IsFriendsWith.friendID2=:friend2")
    public List<String> areFriends(String friend1, String friend2);



    // Get all friends of owner, friendID1 must be lexicographically before friendID2
    /*@Insert
    public void addFriendPair(IsFriendsWith friendPair);*/ // to do for next week

}