package ch.epfl.sdp.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.social.conversation.RemoteToSQLiteAdapter;
import ch.epfl.sdp.database.room.social.Message;

public class MockServerToSQLiteAdapter  implements RemoteToSQLiteAdapter {
    private Context listener;
    private final static MockServerToSQLiteAdapter singleton = new MockServerToSQLiteAdapter();

    public static MockServerToSQLiteAdapter getInstance()
    {
        return singleton;
    }

    @Override
    public void setListener(Context listener) {
        singleton.listener = listener;
    }

    @Override
    public void sendRemoteServerDataToLocal(String owner, String sender, int chat_id) {
        // pretend that remote messages on FireStore are inside remoteMessages
        List<Message> remoteMessages = new ArrayList<>();
        ((WaitsOnWithServer<Message>) listener).contentFetchedWithServer(remoteMessages, true, true);

    }

    @Override
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {
        // does nothing since there is no remote server
    }
}
