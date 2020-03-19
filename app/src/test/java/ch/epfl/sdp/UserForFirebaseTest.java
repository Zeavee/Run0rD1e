package ch.epfl.sdp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserForFirebaseTest {
    private static UserForFirebase userEmpty = new UserForFirebase();
    private static UserForFirebase user1 = new UserForFirebase("name", "email");
    private static UserForFirebase user2 = new UserForFirebase("name", "email");

    @Test
    public void testGetUsername() {
        assertEquals("name", user1.getUsername());
    }

    @Test
    public void testSetUsername() {
        userEmpty.setUsername("setname");
        assertEquals("setname", userEmpty.getUsername());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email", user1.getEmail());
    }

    @Test
    public void testSetEmail() {
        userEmpty.setEmail("setemail");
        assertEquals("setemail", userEmpty.getEmail());
    }

    @Test
    public void testGetHealthPoints() {
        assertEquals(100.0, user1.getHealthPoints(), 0.01);
    }

    @Test
    public void testSetHealthPoints() {
        userEmpty.setHealthPoints(90);
        assertEquals(90, userEmpty.getHealthPoints(), 0.01);
    }

    @Test
    public void testEquals() {
        user1 = new UserForFirebase("name", "email");
        assertEquals(true, user1.equals(user2));
    }

    @Test
    public void testHashCode() {
        user1 = new UserForFirebase("name", "email");
        assertEquals(true, user1.hashCode() == user2.hashCode());
    }
}
