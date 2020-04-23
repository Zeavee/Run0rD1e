package ch.epfl.sdp.SocialUnitTests;


import org.junit.Test;

import ch.epfl.sdp.social.socialDatabase.Chat;

import static org.junit.Assert.assertTrue;

/**
 * @brief tests basic functionality of a Chat instance
 */
public class ChatTest {

    private String user1 = "saoud@gmail.com";
    private String user2 = "sacha@gmail.com";

    @Test
    public void basicChatInstantiationWorks()
    {
        Chat c= new Chat(user1, user2);
        assertTrue(c.getFrom().equals(user1));
        assertTrue(c.getTo().equals(user2));
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
        assertTrue(c.getFrom().equals("alice"));
        assertTrue(c.getTo().equals("bob"));
    }
}
