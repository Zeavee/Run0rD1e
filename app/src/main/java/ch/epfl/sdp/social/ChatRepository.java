package ch.epfl.sdp.social;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import androidx.room.Room;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressLint("StaticFieldLeak")
public class ChatRepository {

    private ChatDatabase chatDB;
    private Activity contextActivity;
    private List<String> messages;
    private boolean messagesFetched;
    public ChatRepository(Activity contextActivity) {
        chatDB = Room.inMemoryDatabaseBuilder(contextActivity.getApplicationContext(), ChatDatabase.class).build();
        this.contextActivity = contextActivity;
        messagesFetched = false;
    }

    public void setContextActivity(Activity contextActivity) {
        this.contextActivity = contextActivity;
    }

    public void sendMessage(String content, int chat_id) {

        Message m = new Message();
        m.setText(content);
        m.setDate(new Date());
        m.setChat_id(chat_id);
        sendMessage(m);
    }

    private void sendMessage(final Message message) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDB.daoAccess().sendMessage(message);
                return null;
            }
        }.execute();
    }

    public void addChat(final Chat c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDB.daoAccess().addChat(c);
                return null;
            }
        }.execute();
    }

    public void addUser(final User usr) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDB.daoAccess().addUser(usr);
                return null;
            }
        }.execute();
    }

    public void fetchFriends(final User user)
    {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                return chatDB.daoAccess().areFriends(user.email);
            }

            @Override
            protected void onPostExecute(List<User> friends)
            {
                ((WaitsOnFriendFetch)contextActivity).friendsFetched(friends);
            }
        }.execute();
    }

    public void addFriends(final User user1, final User user2)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDB.daoAccess().addFriendship(new IsFriendsWith(user1.email, user2.email));
                chatDB.daoAccess().addFriendship(new IsFriendsWith(user2.email, user1.email));
                return null;
            }
        }.execute();
    }

    public void getMessages(final String id_owner, final String id_rec) {

        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                messagesFetched = false;
                List<String> msgList = new LinkedList<>();
                msgList.addAll(chatDB.daoAccess().getMessagesFromOwnerToReceiver(id_owner, id_rec));
                msgList.addAll(chatDB.daoAccess().getMessagesToOwnerFromSender(id_owner, id_rec));
                return msgList;
            }

            @Override
            protected void onPostExecute(List<String> ls)
            {
                ((AsyncResponse)contextActivity).messageFetchFinished(ls);
            }
        }.execute();

    }

    public void insertMessageFromRemote(final Map<String, Object> data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDB.daoAccess().sendMessage(new Message(((Timestamp)data.get("date")).toDate(), data.get("text").toString()));
                return null;
            }
        }.execute();

    }

    public void getChat(String current, String other)
    {
        new AsyncTask<Void, Void, List<Chat>>() {

            @Override
            protected List<Chat> doInBackground(Void... voids) {
                return chatDB.daoAccess().getChatFromCurrentToOther(current, other);
            }

            @Override
            protected void onPostExecute(List<Chat> exists)
            {
                ((WaitOnChatRetrieval)contextActivity).chatFetched(exists);
            }
        }.execute();
    }
}