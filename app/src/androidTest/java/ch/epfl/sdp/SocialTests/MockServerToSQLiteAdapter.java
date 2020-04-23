package ch.epfl.sdp.SocialTests;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.epfl.sdp.social.socialDatabase.Message;
import ch.epfl.sdp.social.Conversation.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.WaitsOnWithServer;

public class MockServerToSQLiteAdapter  implements RemoteToSQLiteAdapter {
    private Context listener;
    private static MockServerToSQLiteAdapter singleton = new MockServerToSQLiteAdapter();
    public List<Message> remoteMessages = new ArrayList<>();

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
        remoteMessages = new ArrayList<>();
        ((WaitsOnWithServer<Message>) listener).contentFetchedWithServer(remoteMessages, true);

    }

    @Override
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {
        // does nothing since there is no remote server
    }
}
