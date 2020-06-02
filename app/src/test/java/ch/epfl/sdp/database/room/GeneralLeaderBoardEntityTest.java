package ch.epfl.sdp.database.room;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeneralLeaderBoardEntityTest {
    @Test
    public void leaderboardEntityTest() {
        GeneralLeaderBoardEntity leaderboardEntityGeneral = new GeneralLeaderBoardEntity("test@gmail.com", "test", 0);

        leaderboardEntityGeneral.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", leaderboardEntityGeneral.getEmail());

        leaderboardEntityGeneral.setUsername("user1");
        assertEquals("user1", leaderboardEntityGeneral.getUsername());

        leaderboardEntityGeneral.setGeneralScore(10);
        assertEquals(10, leaderboardEntityGeneral.getGeneralScore());
    }
}
