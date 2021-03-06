package ch.epfl.sdp.utils;

import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.database.authentication.FirebaseAuthenticationAPI;
import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ClientFirestoreDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonFirestoreDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerFirestoreDatabaseAPI;
import ch.epfl.sdp.social.RemoteUsers.FriendsRepository;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;
import ch.epfl.sdp.social.conversation.FireStoreToSQLiteAdapter;
import ch.epfl.sdp.social.conversation.RemoteToSQLiteAdapter;

public class AppContainer {
    /**
     * Handles fetching data regarding user conversations from the remote server (FireStore) to the local SQLite database
     */
    public RemoteToSQLiteAdapter remoteToSQLiteAdapter = new FireStoreToSQLiteAdapter();

    /**
     * The server that fetches users from cloud service
     */
    public RemoteFriendFetcher remoteUserFetch = new FriendsRepository();

    public AuthenticationAPI authenticationAPI = new FirebaseAuthenticationAPI();

    public CommonDatabaseAPI commonDatabaseAPI = new CommonFirestoreDatabaseAPI();

    public ServerDatabaseAPI serverDatabaseAPI = new ServerFirestoreDatabaseAPI();

    public ClientDatabaseAPI clientDatabaseAPI = new ClientFirestoreDatabaseAPI();

}
