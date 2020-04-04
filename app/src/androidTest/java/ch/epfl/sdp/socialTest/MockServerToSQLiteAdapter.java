package ch.epfl.sdp.socialTest;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.epfl.sdp.social.Message;
import ch.epfl.sdp.social.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.WaitsOnMessageFetch;

public class MockServerToSQLiteAdapter  implements RemoteToSQLiteAdapter {
    private static Context listener;
    private static MockServerToSQLiteAdapter singleton = new MockServerToSQLiteAdapter();
    private boolean alreadySentMessage;

    public MockServerToSQLiteAdapter getInstance()
    {
        return singleton;
    }

    @Override
    public void setListener(Context listener) {
        singleton.listener = listener;
    }

    @Override
    public void sendRemoteServerDataToLocal(String owner, String sender, int chat_id) {

        // pretend that remote messages on FireStore are the following
        if (!alreadySentMessage) {
            List<Message> remoteMessages = new ArrayList<>();
            remoteMessages.add(new Message(new Date(), "Shaima is not stupid, from user with ID stupid2", chat_id));
            ((WaitsOnMessageFetch) listener).incomingMessageFetchFinished(remoteMessages, true);
            alreadySentMessage = true;
        }

    }

    @Override
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {
        // does nothing since there is no remote server
    }
}
