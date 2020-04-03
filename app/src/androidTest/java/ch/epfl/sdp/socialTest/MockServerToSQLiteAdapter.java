package ch.epfl.sdp.socialTest;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ch.epfl.sdp.social.Message;
import ch.epfl.sdp.social.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.WaitsOnMessageFetch;

public class MockServerToSQLiteAdapter  implements RemoteToSQLiteAdapter {
    private static Context listener;
    private static MockServerToSQLiteAdapter singleton = new MockServerToSQLiteAdapter();

    public MockServerToSQLiteAdapter getInstance()
    {
        return singleton;
    }

    @Override
    public void setListener(Context listener) {
        singleton.listener = listener;
    }

    @Override
    public void sendRemoteServerDataToLocal(String owner, String sender) {

        // pretend that remote messages on FireStore are the following
        List<Message> remoteMessages = new ArrayList<>();
        remoteMessages.add(new Message(new Date(), "Shaima is not stupid, from user with ID stupid2"));
        ((WaitsOnMessageFetch)listener).incomingMessageFetchFinished(remoteMessages);

    }

    @Override
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {
        // does nothing since there is no remote server
    }
}
