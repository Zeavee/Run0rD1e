package ch.epfl.sdp.database.room;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeaderboardEntityTest {
    @Test
    public void leaderboardEntityTest() {
        LeaderboardEntity leaderboardEntity = new LeaderboardEntity("test@gmail.com", "test", 0.0);

        leaderboardEntity.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", leaderboardEntity.getEmail());

        leaderboardEntity.setUsername("user1");
        assertEquals("user1", leaderboardEntity.getUsername());

        leaderboardEntity.setScore(9.9);
        assertEquals(9.9, leaderboardEntity.getScore(), 0.01);
    }
}
