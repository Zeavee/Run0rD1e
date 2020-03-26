package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sdp.database.UserForFirebase;
import ch.epfl.sdp.social.Friend;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class FriendTest {
    private List<Friend> friends;
    private List<String> emails = Arrays.asList("amro@ewa.co.eg", "jakehall@youtube.com", "tobias199@bell.ca", "zeavee@ge.ch");
    private List<String> username = Arrays.asList("are1998", "flutist", "jimmyO", "zea_Breeze");

    @Before
    public void setup()
    {
        friends = new ArrayList<>();
        for (int i =0; i < emails.size(); ++i)
        {
            UserForFirebase user = new UserForFirebase(username.get(i), emails.get(i));
            friends.add(new Friend(user));
        }
    }
    @Test
    public void addingFriendsWorks() {
        Friend f1 = friends.get(0);
        Friend f2 = friends.get(1);
        int f1count = f1.getFriendCount();
        int f2count = f1.getFriendCount();
        int netChange = 1;
        if (f1.isFriendsWith(f2))
        {
            netChange = 0;
        }
        f1.unFriend(f2);
        assertTrue(!f1.isFriendsWith(f2));
        f1.addFriend(f2);
        assertTrue(f1.isFriendsWith(f2));
        assertEquals(f1count+netChange, f1.getFriendCount());
        assertEquals(f2count+netChange, f2.getFriendCount());
    }

    @Test
    public void friendCountIsCorrect() {
        Friend f1 = friends.get(2);
        for (int i = 0; i < emails.size();++i)
        {
            f1.unFriend(friends.get(0));
        }

        int f1count = f1.getFriendCount();
        f1.addFriend(friends.get(0));
        f1.addFriend(friends.get(1));
        assertEquals(2, f1.getFriendCount());
    }
}
