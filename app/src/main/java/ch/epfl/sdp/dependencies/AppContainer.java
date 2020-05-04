package ch.epfl.sdp.dependencies;

import ch.epfl.sdp.social.Conversation.FireStoreToSQLiteAdapter;
import ch.epfl.sdp.social.Conversation.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.RemoteUsers.FriendsRepository;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

public class AppContainer {
    /**
     * Handles fetching data regarding user conversations from the remote server (FireStore) to the local SQLite database
     */
    public RemoteToSQLiteAdapter remoteToSQLiteAdapter = new FireStoreToSQLiteAdapter();

    /**
     * The server that fetches users from cloud service
     */
    public RemoteFriendFetcher remoteUserFetch = new FriendsRepository();
}
