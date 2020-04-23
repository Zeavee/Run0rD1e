package ch.epfl.sdp.dependencies;

import ch.epfl.sdp.social.Conversation.FireStoreToSQLiteAdapter;
import ch.epfl.sdp.social.Conversation.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.RemoteUsers.FriendsRepositery;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

/**
 * The goal is to provide dependencies that activities need without making these dependencies static fields inside the activity classes
 * (static fields inside activities are a memory leak as Alexandre Chau mentioned)
 */
public class DependencyProvider {

    /**
     * Handles fetching data regarding user conversations from the remote server (FireStore) to the local SQLite database
     */
    public static RemoteToSQLiteAdapter remoteToSQLiteAdapter = FireStoreToSQLiteAdapter.getInstance();

    /**
     * The email of the current user (only for testing purposes since the actual email is obtained from the login activity)
     */
    public static String email = "stupid1@gmail.com";


    /**
     * The server that fetches users from cloud service
     */
    public static RemoteFriendFetcher remoteUserFetch = new FriendsRepositery();


}
