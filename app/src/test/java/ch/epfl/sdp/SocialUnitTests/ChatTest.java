package ch.epfl.sdp.SocialUnitTests;


import org.junit.Test;

import ch.epfl.sdp.database.social.Chat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests basic functionality of a Chat instance
 */
public class ChatTest {

    @Test
    public void basicChatInstantiationWorks()
    {
        String user1 = "saoud@gmail.com";
        String user2 = "sacha@gmail.com";
        Chat c= new Chat(user1, user2);
        assertEquals(c.getFrom(), user1);
        assertEquals(c.getTo(), user2);
    }
    @Test
    public void chatIdNotNegative()
    {
        assertTrue(new Chat("amro", "adam").getChat_id() >= 0);
    }

    @Test
    public void settersChangeFields()
    {
        Chat c= new Chat("dina", "james");
        c.setFrom("alice");
        c.setTo("bob");
        assertEquals("alice", c.getFrom());
        assertEquals("bob", c.getTo());
    }
}
