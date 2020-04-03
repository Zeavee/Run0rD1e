package ch.epfl.sdp.social;

import android.content.Context;

public interface RemoteToSQLiteAdapter {

     RemoteToSQLiteAdapter getInstance();
     void setListener(Context listener);
     void sendRemoteServerDataToLocal(String owner, String sender);
     void sendLocalDataToRemoteServer(String current_usr, String to, Message m);
}
