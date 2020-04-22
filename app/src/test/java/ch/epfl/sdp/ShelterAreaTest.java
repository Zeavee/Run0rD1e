package ch.epfl.sdp;

import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.map.GeoPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ShelterAreaTest {
    private static Player player1 = new Player(6.151210, 46.212470, 50,
            "test1", "test1@email.com");
    private static Player player2 = new Player(4.149290, 46.212470, 50,
            "test2", "test2@email.com");
    private static Player player3 = new Player(5.149290, 46.212470, 50,
            "test3", "test3@email.com");
    private static Player player4 = new Player(3.149290, 46.212470, 50,
            "test4", "test4@email.com");
    private static GeoPoint shelterLocation = new GeoPoint(6.149291, 46.212470);

    private ShelterArea createShelterArea() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        double shelterAoeRadius = 100;
        PlayerManager.setPlayers(players);
        return new ShelterArea(shelterLocation, shelterAoeRadius);
    }

    /**
     * basicTests covers all the basic methods of the ShelterArea class (such as constructor, getter, ...)
     */
    @Test
    public void basicTests() {
        ShelterArea shelterArea = createShelterArea();
        assertNotNull(shelterArea);
        assertEquals(EntityType.SHELTER ,shelterArea.getEntityType());
        assertEquals(shelterLocation ,shelterArea.getLocation());
        assertEquals(100, shelterArea.getAoeRadius(), 0);
        //Currently, none of the player should be in the shelter area as the shelter method was not called
        assertFalse(shelterArea.isInShelterArea(player1));
        assertFalse(shelterArea.isInShelterArea(player2));
        assertFalse(shelterArea.isInShelterArea(player3));
        assertFalse(shelterArea.isInShelterArea(player4));
    }

    @Test
    public void ShelterTest() {
        ShelterArea shelterArea = createShelterArea();
        shelterArea.shelter();
        assertFalse(shelterArea.isInShelterArea(player2));
        assertFalse(shelterArea.isInShelterArea(player3));
        assertFalse(shelterArea.isInShelterArea(player4));
        assertTrue(shelterArea.isInShelterArea(player1));
        assertFalse(player2.isShielded());
        assertFalse(player3.isShielded());
        assertFalse(player4.isShielded());
        assertTrue(player1.isShielded());
    }


}
