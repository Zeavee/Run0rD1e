package ch.epfl.sdp.social.Conversation;

import android.content.Context;

import ch.epfl.sdp.social.socialDatabase.Message;

public interface RemoteToSQLiteAdapter {

    void setListener(Context listener);

    /**
     * @param owner   the currently signed in user
     * @param sender  the user who sent the messages we are fetching
     * @param chat_id id of the conversation between owner and sender (used to index local database that stores all conversations by user "owner")
     * @brief Gets the incoming messages from remote server. "Owner" is the currently signed in user, and "sender" is the user who sent messages to "owner"
     */
    void sendRemoteServerDataToLocal(String owner, String sender, int chat_id);

    /**
     * @param current_usr The currently signed in user
     * @param to          The user we want to send a message to
     * @param m           The message we want to send
     * @brief Send a message to the server
     */
    void sendLocalDataToRemoteServer(String current_usr, String to, Message m);
}
