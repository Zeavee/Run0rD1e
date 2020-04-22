package ch.epfl.sdp.social;

import android.content.Context;
import ch.epfl.sdp.social.socialDatabase.*;

public interface RemoteToSQLiteAdapter {

    void setListener(Context listener);

    void sendRemoteServerDataToLocal(String owner, String sender, int chat_id);

    void sendLocalDataToRemoteServer(String current_usr, String to, Message m);
}
