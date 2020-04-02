package ch.epfl.sdp.social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public final class ChatRepository {

    private ChatDatabase chatDB;
    private Context contextActivity;
    private List<String> messages;
    private static boolean singletonCreated = false;
    private static ChatRepository singleton;
    public static ChatRepository createRepo(Context contextActivity)
    {
        if (!singletonCreated) {
            singleton = new ChatRepository(contextActivity);
            singletonCreated =true;
            return singleton;
        }
        else return singleton;
    }
    private ChatRepository(Context contextActivity) {
        //chatDB = Room.inMemoryDatabaseBuilder(databaseBuilder(contextActivity, ChatDatabase.class, "hello").build();
        chatDB = Room.inMemoryDatabaseBuilder(contextActivity, ChatDatabase.class).allowMainThreadQueries().build();
        this.contextActivity = contextActivity;
    }

    public static void setContextActivity(Context contextActivity) {
        singleton.contextActivity = contextActivity;
    }

    public static void sendMessage(String content, int chat_id) {

        Message m = new Message(new Date(), content);
        m.setChat_id(chat_id);
        singleton.sendMessage(m);
    }

    private static void sendMessage(final Message message) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //try {
                    singleton.chatDB.daoAccess().sendMessage(message);
                //}
                //catch (SQLiteConstraintException e){}
                return null;
            }
        }.execute();
    }

    public static void addChat(final Chat c) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //try {
                    singleton.chatDB.daoAccess().addChat(c);
                //}
                //catch (SQLiteConstraintException e)
                //{
                    // already added, do nothing
                //}
                return null;
            }
        }.execute();
    }

    public static void addUser(final User usr) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //try {
                    singleton.chatDB.daoAccess().addUser(usr);
                //}
                //catch(SQLiteConstraintException e)
                //{
                   //User already added to the database, do nothing
                //}
                return null;
            }
        }.execute();
    }

    // This method must be
    public static void fetchFriends(final User user)
    {
        new AsyncTask<Void, Void, List<User>>() {
            private Context context;
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

    public static void addFriends(final User user1, final User user2)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //try {
                    singleton.chatDB.daoAccess().addFriendship(new IsFriendsWith(user1.email, user2.email));
                    singleton.chatDB.daoAccess().addFriendship(new IsFriendsWith(user2.email, user1.email)); // friendship is symmetric
                //}catch (SQLiteConstraintException e) {
                     // foreign key was not found so add both users to user db (done in caller)
                  //  Log.d("ChatRepo Exception", "Caller must ensure users added to DB");
                //}
                return null;
            }
        }.execute();
    }

    public static void getMessages(final String id_owner, final String id_rec) {

        new AsyncTask<Void, Void, List<Message>>() {

            @Override
            protected List<Message> doInBackground(Void... voids) {
                List<Message> msgList = new LinkedList<>();
                msgList.addAll(singleton.chatDB.daoAccess().getMessagesFromOwnerToReceiver(id_owner, id_rec));
                msgList.add(new Message(new Date(), "8*^&=*%^&*()90")); // canary value (temporary solution for now)
                msgList.addAll(singleton.chatDB.daoAccess().getMessagesToOwnerFromSender(id_owner, id_rec));
                return msgList;
            }

            @Override
            protected void onPostExecute(List<Message> ls)
            {
                ((WaitsOnMessageFetch)singleton.contextActivity).messageFetchFinished(ls);
            }
        }.execute();

    }

    public static void insertMessageFromRemote(Timestamp tm, String content) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //try {
                    singleton.chatDB.daoAccess().sendMessage(new Message(tm.toDate(), content));
                //}catch (SQLiteConstraintException e)
                //{ }
                return null;
            }
        }.execute();

    }

    public static void getChat(String current, String other)
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