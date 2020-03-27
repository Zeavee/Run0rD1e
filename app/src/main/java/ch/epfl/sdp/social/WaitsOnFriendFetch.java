package ch.epfl.sdp.social;

import java.util.List;

interface WaitsOnFriendFetch {

    void friendsFetched(List<User> friends);
}
