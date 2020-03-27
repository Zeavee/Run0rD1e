package ch.epfl.sdp.social;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
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
    private static boolean singletonCreated = false;
    private static ChatRepository singleton;
    public static ChatRepository createRepo(Activity contextActivity)
    {
        if (!singletonCreated) {
            singleton = new ChatRepository(contextActivity);
            singletonCreated =true;
            return singleton;
        }
        else return singleton;
    }
    private ChatRepository(Activity contextActivity) {
        chatDB = Room.inMemoryDatabaseBuilder(contextActivity.getApplicationContext(), ChatDatabase.class).allowMainThreadQueries().build();
        this.contextActivity = contextActivity;
    }

    public void setContextActivity(Activity contextActivity) {
        singleton.contextActivity = contextActivity;
    }

    public void sendMessage(String content, int chat_id) {

        Message m = new Message();
        m.setText(content);
        m.setDate(new Date());
        m.setChat_id(chat_id);
        singleton.sendMessage(m);
    }

    private void sendMessage(final Message message) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try { singleton.chatDB.daoAccess().sendMessage(message); }
                catch (SQLiteConstraintException e){}
                return null;
            }
        }.execute();
    }

    public void addChat(final Chat c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    singleton.chatDB.daoAccess().addChat(c);
                }
                catch (SQLiteConstraintException e){}
                return null;
            }
        }.execute();
    }

    public void addUser(final User usr) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    singleton.chatDB.daoAccess().addUser(usr);
                }
                catch(SQLiteConstraintException e)
                {

                }
                return null;
            }
        }.execute();
    }

    // This method must be
    public void fetchFriends(final User user)
    {
        new AsyncTask<Void, Void, List<User>>() {
            private Activity context;
            @Override
            protected List<User> doInBackground(Void... voids) {
                context = singleton.contextActivity;
                return singleton.chatDB.daoAccess().areFriends(user.email);
            }

            @Override
            protected void onPostExecute(List<User> friends)
            {
                ((WaitsOnFriendFetch)(context)).friendsFetched(friends);
            }
        }.execute();
    }

    public void addFriends(final User user1, final User user2)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    singleton.chatDB.daoAccess().addFriendship(new IsFriendsWith(user1.email, user2.email));
                    singleton.chatDB.daoAccess().addFriendship(new IsFriendsWith(user2.email, user1.email));
                }catch (SQLiteConstraintException e) {}
                return null;
            }
        }.execute();
    }

    public void getMessages(final String id_owner, final String id_rec) {

        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> msgList = new LinkedList<>();
                msgList.addAll(singleton.chatDB.daoAccess().getMessagesFromOwnerToReceiver(id_owner, id_rec));
                msgList.addAll(singleton.chatDB.daoAccess().getMessagesToOwnerFromSender(id_owner, id_rec));
                return msgList;
            }

            @Override
            protected void onPostExecute(List<String> ls)
            {
                ((AsyncResponse)singleton.contextActivity).messageFetchFinished(ls);
            }
        }.execute();

    }

    public void insertMessageFromRemote(final Map<String, Object> data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    singleton.chatDB.daoAccess().sendMessage(new Message(((Timestamp) data.get("date")).toDate(), data.get("text").toString()));
                }catch (SQLiteConstraintException e)
                { }
                return null;
            }
        }.execute();

    }

    public void getChat(String current, String other)
    {
        new AsyncTask<Void, Void, List<Chat>>() {

            @Override
            protected List<Chat> doInBackground(Void... voids) {
                return singleton.chatDB.daoAccess().getChatFromCurrentToOther(current, other);
            }

            @Override
            protected void onPostExecute(List<Chat> exists)
            {
                ((WaitOnChatRetrieval)(singleton.contextActivity)).chatFetched(exists);
            }
        }.execute();
    }
}