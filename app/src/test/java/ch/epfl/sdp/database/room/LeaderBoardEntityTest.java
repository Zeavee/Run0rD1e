package ch.epfl.sdp.database.room;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeaderBoardEntityTest {
    @Test
    public void leaderboardEntityTest() {
        LeaderBoardEntity leaderboardEntity = new LeaderBoardEntity("test@gmail.com", "test", 0);

        leaderboardEntity.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", leaderboardEntity.getEmail());

        leaderboardEntity.setUsername("user1");
        assertEquals("user1", leaderboardEntity.getUsername());

        leaderboardEntity.setGeneralScore(10);
        assertEquals(10, leaderboardEntity.getGeneralScore());
    }
}
