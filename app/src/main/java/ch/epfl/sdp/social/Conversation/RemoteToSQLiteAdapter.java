package ch.epfl.sdp.social.Conversation;

import android.content.Context;
import ch.epfl.sdp.social.socialDatabase.*;

public interface RemoteToSQLiteAdapter {

    void setListener(Context listener);

    /**
     * @brief Gets the incoming messages from remote server. "Owner" is the currently signed in user, and "sender" is the user who sent messages to "owner"
     * @param owner the currently signed in user
     * @param sender the user who sent the messages we are fetching
     * @param chat_id id of the conversation between owner and sender (used to index local database that stores all conversations by user "owner")
     * */
    void sendRemoteServerDataToLocal(String owner, String sender, int chat_id);

    /**
     * @brief Send a message to the server
     * @param current_usr The currently signed in user
     * @param to The user we want to send a message to
     * @param m The message we want to send
     */
    void sendLocalDataToRemoteServer(String current_usr, String to, Message m);
}
