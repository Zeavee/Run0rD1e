package ch.epfl.sdp.dependencies;

import ch.epfl.sdp.social.Conversation.RemoteToSQLiteAdapter;

/**
 * The goal is to provide dependencies that activities need without making these dependencies static fields inside the activity classes
 * (static fields inside activities are a memory leak as dialexo mentioned)
 */
public class DependencyProvider {

    /**
     * Handles fetching data regarding user conversations from the remote server (FireStore) to the local SQLite database
     */
    public static RemoteToSQLiteAdapter remoteToSQLiteAdapter;

    /**
     * @brief sets the adapter that interfaces between the SQLite db and the remote server
     * @param adapter the adapter that interfaces between the SQLite db and the remote server
     */
    public static void setRemoteToSQLiteAdapter(RemoteToSQLiteAdapter adapter)
    {
        remoteToSQLiteAdapter = adapter;
    }

    /**
     * @brief the email of the current user (only for testing purposes since the actual email is obtained from the login activity)
     */
    public static String email = "stupid1@gmail.com";

    /**
     * sets the email of the current user (for testing activities without logging in)
     * @param newEmail the email of the logged in user
     */
    public static void setEmail(String newEmail)
    {
        email = newEmail;
    }

}
