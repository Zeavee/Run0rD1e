package ch.epfl.sdp.SocialUnitTests;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sdp.database.social.Message;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void testMessageWorks() {
        Message m = new Message(new Date(0), "hello", 500);
        m.setDate(new Date(1));
        m.setText("hallo");
        m.setChat_id(1000);
        assertEquals("hallo", m.getText());
        assertEquals(1, m.getDate().getTime());
        assertEquals(1000, m.getChat_id());
    }
}
