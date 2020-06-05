package ch.epfl.sdp.SocialUnitTests;

import org.junit.Test;

import ch.epfl.sdp.social.socialDatabase.IsFriendsWith;
import ch.epfl.sdp.social.socialDatabase.User;

import static org.junit.Assert.assertEquals;

public class IsFriendsWithTest {

    @Test
    public void isFriendsInstantiationWorks()
    {
        User jacob = new User("placeholder");
        jacob.setEmail("jacob");
        IsFriendsWith pair = new IsFriendsWith(jacob.getEmail(), "samuel");
        assertEquals("jacob", pair.getFriendID1());
        assertEquals("samuel", pair.getFriendID2());
    }

    @Test
    public void settersModifyCorrectly()
    {
        IsFriendsWith pair = new IsFriendsWith("steve", "claire");
        pair.setFriendID1("alice");
        pair.setFriendID2("bob");
        assertEquals("alice", pair.getFriendID1());
        assertEquals("bob", pair.getFriendID2());
    }
}
