package ch.epfl.sdp.socialTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sdp.social.User;
import ch.epfl.sdp.social.friends_firestore.RemoteFriendFetcher;

public class MockFriendsFetcher extends RemoteFriendFetcher {
    @Override
    public void getFriendsFromServer(String constraint) {
        List<User> all = new ArrayList<>();
        List<User> filtered = new ArrayList<>();
        all.addAll(Arrays.asList(new User("stupid0@gmail.com"), new User("stupid1@gmail.com"),
                new User("stupid2@gmail.com"), new User("stupid3@gmail.com"), new User("stupid4@gmail.com")
        , new User("stupid5@gmail.com"), new User("stupid6@gmail.com")));

        for (User el: filtered)
        {
            if (el.getEmail().contains(constraint))
            {
                filtered.add(el);
            }
        }
    }
}
