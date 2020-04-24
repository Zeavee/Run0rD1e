package ch.epfl.sdp.SocialUnitTests;

import org.junit.Test;

import ch.epfl.sdp.social.socialDatabase.IsFriendsWith;
import ch.epfl.sdp.social.socialDatabase.User;

import static org.junit.Assert.assertTrue;

public class IsFriendsWithTest {

    @Test
    public void isFriendsInstantiationWorks()
    {
        User jacob = new User("placeholder");
        jacob.setEmail("jacob");
        IsFriendsWith pair = new IsFriendsWith(jacob.getEmail(), "samuel");
        assertTrue(pair.getFriendID1().equals("jacob"));
        assertTrue(pair.getFriendID2().equals("samuel"));
    }

    @Test
    public void settersModifyCorrectly()
    {
        IsFriendsWith pair = new IsFriendsWith("steve", "claire");
        pair.setFriendID1("alice");
        pair.setFriendID2("bob");
        assertTrue(pair.getFriendID1().equals("alice"));
        assertTrue(pair.getFriendID2().equals("bob"));
    }
}
