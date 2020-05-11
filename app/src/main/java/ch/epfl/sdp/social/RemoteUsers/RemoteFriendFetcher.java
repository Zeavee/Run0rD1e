package ch.epfl.sdp.social.RemoteUsers;


import ch.epfl.sdp.social.socialDatabase.*;
import ch.epfl.sdp.social.WaitsOn;

/**
 * This interface provides an abstraction for class that fetch all friends from the remote server
 */
public interface RemoteFriendFetcher {
    /**
     * This methods fetch the friends list of the user
     *
     * @param constraint a constraints on the name of the user we search for
     * @param waiter     an object that waits for the query to be complete and tell the user when it is done
     */
    void getFriendsFromServer(String constraint, WaitsOn<User> waiter);
}
