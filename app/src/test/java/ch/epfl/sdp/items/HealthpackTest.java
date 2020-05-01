package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.entity.PlayerManagerTest;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertTrue;

public class HealthpackTest {
    private Player originalPlayer = PlayerManager.getCurrentUser();
    @Test
    public void cloneHasSameHealthPoints() {
        Healthpack original = new Healthpack(50.5);
        Item cloned = original.clone();
        assertTrue(((Healthpack)cloned).getHealthPackAmount() == original.getHealthPackAmount());
    }

    @Test
    public void healthPackIncreasesHealthToThreshold(){
        Player sick = new Player(20.0, 20.0, 100, "amroa", "amro.abdrabo@gmail.com");
        sick.setHealthPoints(20.0);
        PlayerManager.setCurrentUser(sick);
        Healthpack pack = new Healthpack(30.0);
        pack.use();
        assertTrue(sick.getHealthPoints() == 50.0);
        Healthpack pack2 = new Healthpack(70.0);
        pack2.use();
        assertTrue(sick.getHealthPoints() == 100.0);
        PlayerManager.setCurrentUser(originalPlayer);
    }

    @Test
    public void healthPackIsOfCorrectType(){
        assertTrue(new Healthpack(20.0).getEntityType() == EntityType.HEALTHPACK);
    }

}
