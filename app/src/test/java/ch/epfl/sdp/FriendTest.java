package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RandomEnemyGenerator;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.social.Friend;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FriendTest {
    private HashMap<Friend, UserForFirebase> map;
    private List<String> emails = Arrays.asList("amro@ewa.co.eg", "jakehall@youtube.com", "tobias199@bell.ca", "zeavee@bs.ch");
    private List<String> username = Arrays.asList("are1998", "flutist", "jimmyO", "zea_Breeze");

    @Before
    public void setup()
    {
        map = new HashMap<Friend, UserForFirebase>();
        for (int i =0; i < emails.size(); ++i)
        {
            UserForFirebase user = new UserForFirebase(username.get(i), emails.get(i));
            map.put(new Friend(user), user);
        }
    }
    @Test
    public void addingFriendsWorks() {


    }
}
