package ch.epfl.sdp;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sdp.social.Message;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageTest {

    @Test
    public void testMessageWorks()
    {
        Message m = new Message(new Date(0),"hello" );
        m.setDate(new Date(1));
        m.setText("hallo");
        m.setChat_id(1000);
        assertTrue(m.getText().equals("hallo"));
        assertTrue(m.getDate().getTime() == 1);
        assertTrue(m.getChat_id()== 1000);

    }
}
