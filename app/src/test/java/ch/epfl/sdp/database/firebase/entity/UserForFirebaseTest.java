package ch.epfl.sdp.database.firebase.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserForFirebaseTest {
    @Test
    public void userForFirebaseTest() {
        UserForFirebase userForFirebase1 = new UserForFirebase();
        UserForFirebase userForFirebase2 = new UserForFirebase("test@gmail.com", "test", 0.0);

        userForFirebase1.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", userForFirebase1.getEmail());

        userForFirebase1.setUsername("user1");
        assertEquals("user1", userForFirebase1.getUsername());

        userForFirebase1.setScore(9.9);
        assertEquals(9.9, userForFirebase1.getScore(), 0.01);
    }
}
