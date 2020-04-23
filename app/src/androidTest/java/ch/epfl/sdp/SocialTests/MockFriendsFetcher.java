package ch.epfl.sdp.SocialTests;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sdp.social.socialDatabase.User;
import ch.epfl.sdp.social.WaitsOn;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

public class MockFriendsFetcher implements RemoteFriendFetcher {
    @Override
    public void getFriendsFromServer(String constraint, WaitsOn<User> waiter) {
        List<User> all = new ArrayList<>();
        List<User> filtered = new ArrayList<>();
        all.addAll(Arrays.asList(
                new User("stupid0@gmail.com", "stupid0"),
                new User("stupid1@gmail.com", "stupid1"),
                new User("stupid2@gmail.com", "stupid2"),
                new User("stupid3@gmail.com", "stupid3"),
                new User("stupid4@gmail.com", "stupid4"),
                new User("stupid5@gmail.com", "stupid5"),
                new User("stupid6@gmail.com", "stupid6")));

        for (User el: all)
        {

            if (el.getEmail().contains(constraint))
            {
                filtered.add(el);
            }
        }
        waiter.contentFetched(filtered);
    }
}
