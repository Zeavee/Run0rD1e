package ch.epfl.sdp.social.friends_firestore;

import java.util.List;

import ch.epfl.sdp.social.User;

public interface WaitsOnUserFetch {

    void signalFriendsFetched(List<User> fetched_friends);

    void updateSearch(String friendQuery);
}
